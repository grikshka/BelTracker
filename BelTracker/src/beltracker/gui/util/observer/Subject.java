/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.util.observer;

import beltracker.be.Task;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface Subject {
    
    void register(TaskObserver o);
    
    void unregister(TaskObserver o);
    
    void notifyObservers(List<Task> newTasks, List<Task> removedTasks);
    
}
