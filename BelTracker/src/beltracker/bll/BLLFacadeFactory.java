/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.exception.BelTrackerException;

/**
 *
 * @author Acer
 */
public class BLLFacadeFactory {
    
    public enum FacadeType {
        PRODUCTION
    }
    
    
    private static BLLFacadeFactory instance;
    
    private BLLFacadeFactory()
    {
        
    }
    
    public static synchronized BLLFacadeFactory getInstance()
    {
        if(instance == null)
        {
            return new BLLFacadeFactory();
        }
        return instance;
    }
    
    public IBLLFacade createFacade(FacadeType type) throws BelTrackerException
    {
        switch(type)
        {
            case PRODUCTION: 
                return new BLLManager();
            
            default:         
                return new BLLManager();
            
        }
    }

    
}
