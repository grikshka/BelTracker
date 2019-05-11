/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import beltracker.be.Department;
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
    
    public DALManager()
    {
        try
        {
            connector = new DbConnectionProvider(DB_PROPERTIES_FILE_PATH);
        }
        catch(IOException ex)
        {
            //TO DO
        }
    }

    @Override
    public List<Order> getOrders(Department department) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public List<Department> getDepartments() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
