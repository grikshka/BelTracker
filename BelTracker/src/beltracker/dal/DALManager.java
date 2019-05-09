/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import beltracker.be.Order;
import beltracker.exception.BelTrackerException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Acer
 */
public class DALManager implements IDALFacade{
    
    private static final String DB_PROPERTIES_FILE_PATH = "src/resources/properties/DatabaseProperties.properties";
    private DbConnectionProvider connector;
    
    public DALManager() throws BelTrackerException
    {
        try
        {
            connector = new DbConnectionProvider(DB_PROPERTIES_FILE_PATH);
        }
        catch(IOException ex)
        {
            throw new BelTrackerException("Cannot find properties file with database informations.", ex);
        }
    }

    @Override
    public List<Order> getOrders(String departmentName) {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    
    
}
