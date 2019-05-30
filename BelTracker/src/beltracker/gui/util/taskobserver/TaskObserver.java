/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.util.taskobserver;

import beltracker.be.Task;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface TaskObserver {
    
    void update(List<Task> newTasks, List<Task> modifiedTasks, List<Task> removedTasks);
        
}
