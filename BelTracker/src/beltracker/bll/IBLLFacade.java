/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.be.Department;
import beltracker.be.Task;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface IBLLFacade {
    
    List<Task> getTasks(Department department);
    
    List<Department> getAllDepartments();
    
    List<Task> searchTasks(List<Task> tasks, String key);
    
    List<Task> detectModifiedTasks(List<Task> oldList, List<Task> newList);
    
    List<Task> detectNewTasks(List<Task> oldList, List<Task> newList);
    
    List<Task> detectRemovedTasks(List<Task> oldList, List<Task> newList);  
    
}
