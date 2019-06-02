/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import beltracker.dal.exception.DALException;

/**
 *
 * @author Acer
 */
public class DALFacadeFactory {
    
    public enum FacadeType {
        DATABASE, MOCK
    }
        
    private static DALFacadeFactory instance;
    
    private DALFacadeFactory()
    {
        
    }
    
    public static synchronized DALFacadeFactory getInstance()
    {
        if(instance == null)
        {
            instance = new DALFacadeFactory();
        }
        return instance;
    }
    
    public IDALFacade createFacade(FacadeType type)
    {
        switch(type)
        {
            case DATABASE: 
                return new DatabaseDALManager();
            
            case MOCK:       
                return new MockDALManager();
            
            default:         
                return new DatabaseDALManager();
        }
    }
    
}
