/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model.concrete;

import beltracker.be.Department;
import beltracker.be.Task;
import beltracker.bll.IBLLFacade;
import beltracker.gui.model.interfaces.IMainModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import beltracker.gui.util.observer.TaskObserver;
import javafx.application.Platform;

/**
 *
 * @author Acer
 */
public class MainModel implements IMainModel {
     
    private IBLLFacade bllFacade;
    private Department selectedDepartment;
    private ObservableList<Task> departmentTasks;
    private ScheduledExecutorService tasksObserver;
    private List<TaskObserver> observers = new ArrayList<>();
    
    public MainModel(IBLLFacade facade)
    {
        this.bllFacade = facade;
    }
    
    @Override
    public void setDepartment(Department department) {
        selectedDepartment = department;
    }
    
    @Override
    public void loadTasks()
    {
        System.out.println(selectedDepartment.getName());
        List<Task> tasks = bllFacade.getTasks(selectedDepartment);
        departmentTasks = FXCollections.observableArrayList(tasks);
        runTasksObserving();
    }
    
    @Override
    public ObservableList<Task> getTasks()
    {
        return departmentTasks;
    }
    
    private void runTasksObserving()
    {
        tasksObserver = Executors.newSingleThreadScheduledExecutor();
        tasksObserver.scheduleAtFixedRate(() -> updateTasks(), 0, 3, TimeUnit.SECONDS);
    }
    
    private void stopTasksObserving()
    {
        tasksObserver.shutdown();
    }
    
    private void updateTasks()
    {
        List<Task> updatedTasks = bllFacade.getTasks(selectedDepartment);
        Platform.runLater(() -> updateModifiedTasks(updatedTasks));     
        updateNewAndRemovedTasks(updatedTasks);
    }
    
    private void updateModifiedTasks(List<Task> updatedTasks)
    {
        List<Task> modifiedTasks = bllFacade.detectModifiedTasks(departmentTasks, updatedTasks);
        for(Task modifiedTask : modifiedTasks)
        {
            for(Task departmentTask : departmentTasks)
            {
                if(modifiedTask.getId() == departmentTask.getId())
                {
                    departmentTask.update(modifiedTask);
                }
            }
        }
    }
    
    private void updateNewAndRemovedTasks(List<Task> updatedTasks)
    {
        List<Task> newTasks = bllFacade.detectNewTasks(departmentTasks, updatedTasks);
        List<Task> removedTasks = bllFacade.detectRemovedTasks(departmentTasks, updatedTasks);

        if(!newTasks.isEmpty() || !removedTasks.isEmpty())
        {
            departmentTasks.removeAll(removedTasks);
            departmentTasks.addAll(newTasks);
            notifyObservers(newTasks, removedTasks);
        }
    }

    @Override
    public void register(TaskObserver o) {
        observers.add(o);
    }

    @Override
    public void unregister(TaskObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(List<Task> newTasks, List<Task> removedTasks) {
        for(TaskObserver o : observers)
        {
            o.update(newTasks, removedTasks);
        }
    }
    
}
