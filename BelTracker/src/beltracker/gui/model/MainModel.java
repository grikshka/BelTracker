/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model;

import beltracker.be.Order;
import beltracker.bll.BLLFacadeFactory;
import beltracker.bll.IBLLFacade;
import beltracker.exception.BelTrackerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Acer
 */
public class MainModel implements IMainModel{
    
    private static final String DEPARTMENT_PROPERTIES_FILE = "/src/resources/properties/DepartmentProperties.properties";    
    private IBLLFacade facade;
    private ObservableList<Order> orders;
    
    public MainModel()
    {
        facade = BLLFacadeFactory.getInstance().createFacade(BLLFacadeFactory.FacadeType.PRODUCTION);
    }
    
    @Override
    public void loadOrders() throws BelTrackerException 
    {
        String departmentName = readDepartmentName();
        List<Order> departmentOrders = facade.getOrders(departmentName);
        orders = FXCollections.observableArrayList(departmentOrders);
    }
    
    @Override
    public ObservableList<Order> getOrders()
    {
        return orders;
    }
    
    private String readDepartmentName() throws BelTrackerException
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
                throw new BelTrackerException("Cannot read department informations from properties file.");
            }
        }
        catch(IOException ex)
        {
            throw new BelTrackerException("Cannot find properties file with department informations.", ex);
        }
    }
    
}
