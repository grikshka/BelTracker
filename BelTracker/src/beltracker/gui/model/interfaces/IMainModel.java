/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model.interfaces;

import beltracker.be.Department;
import beltracker.be.Task;
import beltracker.gui.util.observer.Subject;
import javafx.collections.ObservableList;

/**
 *
 * @author Acer
 */
public interface IMainModel extends Subject{
    
    void setDepartment(Department department);
    
    void loadTasks();
    
    ObservableList<Task> getTasks();
    
    
}
