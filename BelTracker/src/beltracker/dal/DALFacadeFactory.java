/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import java.util.HashMap;

/**
 *
 * @author Acer
 */
public class DALFacadeFactory {
    
    public enum FacadeType {
        PRODUCTION, MOCK
    }
    
    
    private static DALFacadeFactory instance;
    
    private DALFacadeFactory()
    {
        
    }
    
    public static synchronized DALFacadeFactory getInstance()
    {
        if(instance == null)
        {
            return new DALFacadeFactory();
        }
        return instance;
    }
    
    public IDALFacade createFacade(FacadeType type)
    {
        switch(type)
        {
            case PRODUCTION: 
                return new DALManager();
            
            case MOCK:       
                return new MockDALManager();
            
            default:         
                return new DALManager();
        }
    }
    
}
