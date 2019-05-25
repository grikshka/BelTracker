/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll.util;

import beltracker.be.Department;
import beltracker.be.Task;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class TaskAnalyser {
    
    public double calculateEstimatedProgress(Task task)
    {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = task.getStartDate();
        LocalDate deliveryDate = task.getEndDate();
        if(currentDate.isBefore(startDate))
        {
            return 0;
        }
        else if(currentDate.isBefore(deliveryDate))
        {
            double totalNumberOfDays = DAYS.between(startDate, deliveryDate);
            double currentNumberOfDays = DAYS.between(startDate, currentDate);
            return currentNumberOfDays/totalNumberOfDays;
        }
        else
        {
            return 1;
        }
    }
    
    public Task.Status analyseStatus(Task task, Department department)
    {
        if(!(task.getOrder().getCurrentDepartment().equals(department)))
        {
            return Task.Status.OVERDUE;
        }
        else if(LocalDate.now().isAfter(task.getEndDate()))
        {
            return Task.Status.DELAYED;
        }
        else
        {
            return Task.Status.ON_SCHEDULE;
        }
    }
    
    public List<Task> searchTasksByOrderNumber(List<Task> tasks, String key)
    {        
        List<Task> results = new ArrayList<>();
        String generalizedKey = key.trim();
        for(Task task : tasks)
        {
            String orderNumber = task.getOrder().getNumber();
            if(generalizedKey.length() <= orderNumber.length())
            {
                if(orderNumber.substring(0, generalizedKey.length()).equals(generalizedKey))
                {
                    results.add(task);
                }
            }
        }
        return results;
    }
    
    public List<Task> searchTasksByOrderCustomer(List<Task> tasks, String key)
    {
        List<Task> results = new ArrayList<>();
        String generalizedKey = key.trim().toLowerCase();
        for(Task task : tasks)
        {
            String customerName = task.getOrder().getCustomerName().toLowerCase();
            if(generalizedKey.length() <= customerName.length())
            {
                if(customerName.substring(0, generalizedKey.length()).equals(generalizedKey))
                {
                    results.add(task);
                }
            }
        }
        return results;
    }
    
    public List<Task> detectNewTasks(List<Task> oldList, List<Task> newList)
    {
        List<Task> newTasks = new ArrayList<>();
        for(Task newListTask : newList)
        {
            if(!oldList.contains(newListTask))
            {
                boolean taskModified = false;
                for(Task oldListTask : oldList)
                {
                    if(newListTask.getId() == oldListTask.getId())
                    {
                        taskModified = true;
                    }
                }
                if(!taskModified)
                {
                    newTasks.add(newListTask);
                }
            }
        }
        return newTasks;
    }
    
    public List<Task> detectRemovedTasks(List<Task> oldList, List<Task> newList)
    {
        List<Task> removedTasks = new ArrayList<>();
        for(Task oldListTask : oldList)
        {
            boolean taskModified = false;
            for(Task newListTask: newList)
            {
                if(oldListTask.getId() == newListTask.getId())
                {
                    taskModified = true;
                    break;
                }
            }
            if(!taskModified)
            {
                removedTasks.add(oldListTask);
            }
        }
        return removedTasks;
    }
    
    public List<Task> detectModifiedTasks(List<Task> oldList, List<Task> newList)
    {
        List<Task> modifiedTasks = new ArrayList<>();
        for(Task newListTask : newList)
        {
            if(!oldList.contains(newListTask))
            {
                for(Task oldListTask : oldList)
                {
                    if(newListTask.getId() == oldListTask.getId())
                    {
                        modifiedTasks.add(newListTask);
                        break;
                    }
                }
            }
        }
        return modifiedTasks;
        
    }
}
