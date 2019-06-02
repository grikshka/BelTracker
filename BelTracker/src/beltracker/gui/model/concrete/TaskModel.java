/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model.concrete;

import beltracker.be.Task;
import beltracker.bll.IBLLFacade;
import beltracker.bll.exception.BLLException;
import beltracker.gui.exception.ModelException;
import beltracker.gui.model.interfaces.ITaskModel;

/**
 *
 * @author Acer
 */
public class TaskModel implements ITaskModel{
    
    private IBLLFacade bllFacade;
    private Task task;
    
    public TaskModel(IBLLFacade facade)
    {
        this.bllFacade = facade;
    }
    
    @Override
    public void setTask(Task task)
    {
        this.task = task;
    }

    @Override
    public Task getTask() 
    {
        return task;
    }

    @Override
    public void submitTask() throws ModelException 
    {
        try
        {
            bllFacade.submitTask(task);
        }
        catch(BLLException ex)
        {
            throw new ModelException("Failed to submit the task. Please check your internet connection.", ex);
        }
    }
}
