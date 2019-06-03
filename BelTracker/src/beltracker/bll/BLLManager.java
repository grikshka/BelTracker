/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.be.Department;
import beltracker.be.Task;
import beltracker.bll.exception.BLLException;
import beltracker.bll.taskutil.TaskAnalyser;
import beltracker.bll.taskutil.TaskDetector;
import beltracker.bll.taskutil.TaskSearcher;
import beltracker.bll.taskutil.comparator.TaskEndDateComparator;
import beltracker.bll.taskutil.comparator.TaskEstimatedProgressComparator;
import beltracker.bll.taskutil.comparator.TaskStartDateComparator;
import beltracker.dal.IDALFacade;
import beltracker.dal.exception.DALException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class BLLManager implements IBLLFacade{
    
    private IDALFacade dalFacade;
    private TaskAnalyser taskAnalyser;
    private TaskDetector taskDetector;
    private TaskSearcher taskSearcher;
    
    public BLLManager(IDALFacade facade)
    {
        this.dalFacade = facade;
        taskAnalyser = new TaskAnalyser();
        taskDetector = new TaskDetector();
        taskSearcher = new TaskSearcher();
    }

    @Override
    public List<Task> getTasks(Department department) throws BLLException {
        try
        {
            LocalDate currentDate = LocalDate.now();
            List<Task> tasks = dalFacade.getTasks(department, currentDate);
            for(Task departmentTask : tasks)
            {
                Task.Status status = taskAnalyser.analyseStatus(departmentTask, department, currentDate);
                double estimatedProgress = taskAnalyser.calculateEstimatedProgress(departmentTask, currentDate);
                departmentTask.setStatus(status);
                departmentTask.setEstimatedProgress(estimatedProgress);
            }
            return tasks;
        }
        catch(DALException ex)
        {
            throw new BLLException("Cannot retrieve tasks", ex);
        }
    }

    @Override
    public List<Department> getAllDepartments() throws BLLException {
        try
        {
            return dalFacade.getAllDepartments();
        }
        catch(DALException ex)
        {
            throw new BLLException("Cannot retrieve departments", ex);
        }        
    }

    @Override
    public List<Task> detectModifiedTasks(List<Task> oldList, List<Task> newList) {
        return taskDetector.detectModifiedTasks(oldList, newList);
    }

    @Override
    public List<Task> detectNewTasks(List<Task> oldList, List<Task> newList) {
        return taskDetector.detectNewTasks(oldList, newList);
    }

    @Override
    public List<Task> detectRemovedTasks(List<Task> oldList, List<Task> newList) {
        return taskDetector.detectRemovedTasks(oldList, newList);
    }

    @Override
    public List<Task> searchTasks(List<Task> tasks, String key) {
        List<Task> tasksToSearch = new ArrayList<>(tasks);
        List<Task> results = new ArrayList<>();
        
        List<Task> orderNumberResults = taskSearcher.searchTasksByOrderNumber(tasksToSearch, key);
        results.addAll(orderNumberResults);
        tasksToSearch.removeAll(orderNumberResults);
        List<Task> orderCustomerResults = taskSearcher.searchTasksByOrderCustomer(tasksToSearch, key);
        results.addAll(orderCustomerResults);
        
        return results;
    }

    @Override
    public void submitTask(Task task) throws BLLException {
        try
        {
            long currentEpochTime = System.currentTimeMillis();
            dalFacade.submitTask(task, currentEpochTime);
        }
        catch(DALException ex)
        {
            throw new BLLException("Failed to submit the task", ex);
        }       
    }

    @Override
    public List<Task> sortTasks(List<Task> tasks, SortingType type) {
        switch(type)
        {
            case END_DATE_ASC:
                tasks.sort(new TaskEndDateComparator(TaskEndDateComparator.Type.ASC));
                break;
                
            case END_DATE_DESC:
                tasks.sort(new TaskEndDateComparator(TaskEndDateComparator.Type.DESC));
                break;
                
            case ESTIMATED_PROGRESS_ASC:
                tasks.sort(new TaskEstimatedProgressComparator(TaskEstimatedProgressComparator.Type.ASC));
                break;
                
            case ESTIMATED_PROGRESS_DESC:
                tasks.sort(new TaskEstimatedProgressComparator(TaskEstimatedProgressComparator.Type.DESC));
                break;
                
            case START_DATE_ASC:
                tasks.sort(new TaskStartDateComparator(TaskStartDateComparator.Type.ASC));
                break;
                
            case START_DATE_DESC:
                tasks.sort(new TaskStartDateComparator(TaskStartDateComparator.Type.DESC));
                break;
                
        }
        return tasks;
    }
    
    
    
}
