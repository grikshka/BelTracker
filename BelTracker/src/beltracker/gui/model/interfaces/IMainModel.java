/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model.interfaces;

import beltracker.be.Department;
import beltracker.be.Task;
import beltracker.bll.IBLLFacade.SortingType;
import beltracker.gui.exception.ModelException;
import java.util.List;
import javafx.collections.ObservableList;
import beltracker.gui.util.taskobserver.TaskSubject;

/**
 *
 * @author Acer
 */
public interface IMainModel extends TaskSubject{
    
    void setDepartment(Department department);
    
    void loadTasks() throws ModelException;
    
    ObservableList<Task> getTasks();
    
    List<SortingType> getTaskSortingTypes();
    
    List<Task> searchTasks(List<Task> tasks, String key);
    
    List<Task> sortTasks(List<Task> tasks, SortingType type);
    
    
}
