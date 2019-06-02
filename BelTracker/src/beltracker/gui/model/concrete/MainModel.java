/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model.concrete;

import beltracker.be.Department;
import beltracker.be.Task;
import beltracker.bll.IBLLFacade;
import beltracker.bll.exception.BLLException;
import beltracker.gui.exception.ModelException;
import beltracker.gui.model.interfaces.IMainModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import beltracker.gui.util.taskobserver.TaskObserver;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import org.apache.log4j.Logger;

/**
 *
 * @author Acer
 */
public class MainModel implements IMainModel {
     
    private static final Logger LOGGER = Logger.getLogger(MainModel.class);
    
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
    public void loadTasks() throws ModelException
    {
        try
        {
            List<Task> tasks = bllFacade.getTasks(selectedDepartment);
            departmentTasks = FXCollections.observableArrayList(tasks);
            runTasksObserving();
        }
        catch(BLLException ex)
        {
            throw new ModelException("Cannot retrieve data from the server. Please check your internet connection.", ex);
        }
    }
    
    @Override
    public ObservableList<Task> getTasks()
    {
        return departmentTasks;
    }
    
    public void runTasksObserving()
    {
        tasksObserver = Executors.newSingleThreadScheduledExecutor();
        tasksObserver.scheduleAtFixedRate(() -> updateTasks(), 3, 3, TimeUnit.SECONDS);
    }
    
    private void stopTasksObserving()
    {
        tasksObserver.shutdown();
    }
    
    private void updateTasks()
    {
        try
        {
            List<Task> updatedTasks = bllFacade.getTasks(selectedDepartment);

            List<Task> modifiedTasks = bllFacade.detectModifiedTasks(departmentTasks, updatedTasks);
            List<Task> newTasks = bllFacade.detectNewTasks(departmentTasks, updatedTasks);
            List<Task> removedTasks = bllFacade.detectRemovedTasks(departmentTasks, updatedTasks);

            departmentTasks.addAll(newTasks);
            updateModifiedTasks(updatedTasks);  
            departmentTasks.removeAll(removedTasks);

            notifyObservers(newTasks, modifiedTasks, removedTasks);
        }
        catch(BLLException ex)
        {
            LOGGER.error("Cannot update tasks - failed to retrieve data from the server", ex);
        }
    }
    
    private void updateModifiedTasks(List<Task> modifiedTasks)
    {      
        for(Task modifiedTask : modifiedTasks)
        {
            for(Task departmentTask : departmentTasks)
            {
                if(modifiedTask.getId() == departmentTask.getId())
                {
                    Platform.runLater(() -> departmentTask.update(modifiedTask));
                }
            }
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
    public void notifyObservers(List<Task> newTasks, List<Task> modifiedTasks, List<Task> removedTasks) {
        for(TaskObserver o : observers)
        {
            o.update(newTasks, modifiedTasks, removedTasks);
        }
    }

    @Override
    public List<Task> searchTasks(List<Task> tasks, String key) {
        return bllFacade.searchTasks(tasks, key);
    }

    @Override
    public List<IBLLFacade.SortingType> getTaskSortingTypes() {
        return bllFacade.getTaskSortingTypes();
    }

    @Override
    public List<Task> sortTasks(List<Task> tasks, IBLLFacade.SortingType type) {
        return bllFacade.sortTasks(tasks, type);
    }
    
}
