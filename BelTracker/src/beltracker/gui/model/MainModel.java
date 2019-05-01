/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model;

import beltracker.be.Order;
import beltracker.exception.BeltrackerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author Acer
 */
public class MainModel implements IMainModel{
    
    private static final String DEPARTMENT_PROPERTIES_FILE = "properties/DepartmentProperties.settings";    
    private ObservableList<Order> orders;
    
    @Override
    public void loadOrders() throws BeltrackerException 
    {
        String departmentName = readDepartmentName();
        //TO DO
    }
    
    @Override
    public ObservableList<Order> getOrders()
    {
        return orders;
    }
    
    private String readDepartmentName() throws BeltrackerException
    {
        try
        {
            Properties departmentProperties = new Properties();
            departmentProperties.load(new FileInputStream(DEPARTMENT_PROPERTIES_FILE));
            String departmentName = departmentProperties.getProperty("DepartmentName", null);
            if(departmentName != null)
            {
                return departmentName;
            }
            else
            {
                throw new BeltrackerException("Cannot read department informations from properties file.");
            }
        }
        catch(IOException ex)
        {
            throw new BeltrackerException("Cannot find properties file with department informations.", ex);
        }
    }
    
}
