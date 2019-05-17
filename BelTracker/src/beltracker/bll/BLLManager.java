/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.be.Department;
import beltracker.be.Task;
import beltracker.bll.util.TaskAnalyser;
import beltracker.dal.IDALFacade;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Acer
 */
public class BLLManager implements IBLLFacade{
    
    private IDALFacade dalFacade;
    private TaskAnalyser taskAnalyser;
    
    public BLLManager(IDALFacade facade)
    {
        this.dalFacade = facade;
        taskAnalyser = new TaskAnalyser();
    }

    @Override
    public List<Task> getTasks(Department department) {
        LocalDate currentDate = LocalDate.now();
        List<Task> tasks = dalFacade.getTasks(department, currentDate);
        for(Task departmentTask : tasks)
        {
            Task.Status status = taskAnalyser.analyseStatus(departmentTask, department);
            double estimatedProgress = taskAnalyser.calculateEstimatedProgress(departmentTask);
            departmentTask.setStatus(status);
            departmentTask.setEstimatedProgress(estimatedProgress);
        }
        return tasks;
    }

    @Override
    public List<Department> getAllDepartments() {
        return dalFacade.getAllDepartments();
    }

    @Override
    public List<Task> detectModifiedTasks(List<Task> oldList, List<Task> newList) {
        return taskAnalyser.detectModifiedTasks(oldList, newList);
    }

    @Override
    public List<Task> detectNewTasks(List<Task> oldList, List<Task> newList) {
        return taskAnalyser.detectNewTasks(oldList, newList);
    }

    @Override
    public List<Task> detectRemovedTasks(List<Task> oldList, List<Task> newList) {
        return taskAnalyser.detectRemovedTasks(oldList, newList);
    }
    
    
    
}
