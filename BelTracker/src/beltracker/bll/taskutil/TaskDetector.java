/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll.taskutil;

import beltracker.be.Task;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class TaskDetector {
    
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
