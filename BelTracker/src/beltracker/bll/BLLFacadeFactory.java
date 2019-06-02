/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.bll.exception.BLLException;
import beltracker.dal.DALFacadeFactory;
import beltracker.dal.IDALFacade;
import beltracker.dal.exception.DALException;

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
        dalFacade = DALFacadeFactory.getInstance().createFacade(DALFacadeFactory.FacadeType.DATABASE);
    }
    
    public static synchronized BLLFacadeFactory getInstance()
    {
        if(instance == null)
        {
            instance = new BLLFacadeFactory();
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
