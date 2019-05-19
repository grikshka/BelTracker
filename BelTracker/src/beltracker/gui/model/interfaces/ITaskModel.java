/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model.interfaces;

import beltracker.be.Task;
import beltracker.bll.IBLLFacade;

/**
 *
 * @author Acer
 */
public interface ITaskModel {
    
    void setTask(Task task);
    
    Task getTask();
    
}
