/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model;

import beltracker.bll.BLLFacadeFactory;
import beltracker.bll.IBLLFacade;
import beltracker.gui.model.interfaces.IMainModel;
import beltracker.gui.model.interfaces.IDepartmentModel;
import beltracker.gui.model.concrete.DepartmentModel;
import beltracker.gui.model.concrete.MainModel;
import beltracker.gui.model.concrete.TaskModel;
import beltracker.gui.model.interfaces.ITaskModel;

/**
 *
 * @author Acer
 */
public class ModelCreator {
    
    private static ModelCreator instance;
    private IBLLFacade bllFacade;
    
    private ModelCreator() 
    {
        bllFacade = BLLFacadeFactory.getInstance().createFacade(BLLFacadeFactory.FacadeType.PRODUCTION);
    }
    
    public static synchronized ModelCreator getInstance()
    {
        if(instance == null)
        {
            instance = new ModelCreator();
        }
        return instance;
    }
    
    public IDepartmentModel createDepartmentModel()
    {
        return new DepartmentModel(bllFacade);
    }
    
    public IMainModel createMainModel()
    {
        return new MainModel(bllFacade);
    }
    
    public ITaskModel createTaskModel()
    {
        return new TaskModel(bllFacade);
    }
    
}
