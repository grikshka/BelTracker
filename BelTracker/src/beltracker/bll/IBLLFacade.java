/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.be.Department;
import beltracker.be.Task;
import beltracker.bll.exception.BLLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface IBLLFacade {
    
    public enum SortingType {
        END_DATE_ASC("End date ▲"), END_DATE_DESC("End date ▼"),
        ESTIMATED_PROGRESS_ASC("Estimated Progress ▲"), ESTIMATED_PROGRESS_DESC("Estimated Progress ▼"),
        START_DATE_ASC("Start date ▲"), START_DATE_DESC("Start date ▼");
        
        private final String name;       
        
        private SortingType(String name)
        {
            this.name = name;
        }
        
        @Override
        public String toString()
        {
            return name;
        }
    }
    
    default List<SortingType> getTaskSortingTypes()
    {
        return new ArrayList<>(Arrays.asList(SortingType.values()));
    }
    
    List<Task> getTasks(Department department) throws BLLException;
    
    void submitTask(Task task) throws BLLException;
    
    List<Department> getAllDepartments() throws BLLException;
    
    List<Task> searchTasks(List<Task> tasks, String key);
    
    List<Task> sortTasks(List<Task> tasks, SortingType type);
    
    List<Task> detectModifiedTasks(List<Task> oldList, List<Task> newList);
    
    List<Task> detectNewTasks(List<Task> oldList, List<Task> newList);
    
    List<Task> detectRemovedTasks(List<Task> oldList, List<Task> newList);  
    
}
