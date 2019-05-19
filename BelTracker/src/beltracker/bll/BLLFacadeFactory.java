/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.dal.DALFacadeFactory;
import beltracker.dal.IDALFacade;

/**
 *
 * @author Acer
 */
public class BLLFacadeFactory {
    
    public enum FacadeType {
        PRODUCTION
    }
    
    
    private static BLLFacadeFactory instance;
    private IDALFacade dalFacade;
    
    private BLLFacadeFactory()
    {
        dalFacade = DALFacadeFactory.getInstance().createFacade(DALFacadeFactory.FacadeType.PRODUCTION);
    }
    
    public static synchronized BLLFacadeFactory getInstance()
    {
        if(instance == null)
        {
            return new BLLFacadeFactory();
        }
        return instance;
    }
    
    public IBLLFacade createFacade(FacadeType type)
    {
        switch(type)
        {
            case PRODUCTION: 
                return new BLLManager(dalFacade);
            
            default:         
                return new BLLManager(dalFacade);
            
        }
    }

    
}
