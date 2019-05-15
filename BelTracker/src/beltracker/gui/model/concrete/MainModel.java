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
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Acer
 */
public class MainModel implements IMainModel {
     
    private IBLLFacade bllFacade;
    private Department selectedDepartment;
    private ObservableList<Task> departmentTasks;
    
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
        List<Task> tasks = bllFacade.getTasks(selectedDepartment);
        departmentTasks = FXCollections.observableArrayList(tasks);
    }
    
    @Override
    public ObservableList<Task> getTasks()
    {
        return departmentTasks;
    }
    
    private void runOrderObserving()
    {
        //TO DO
    }
    
}
