/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model.concrete;

import beltracker.be.Department;
import beltracker.bll.IBLLFacade;
import beltracker.bll.exception.BLLException;
import beltracker.gui.exception.ModelException;
import beltracker.gui.model.interfaces.IDepartmentModel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Acer
 */
public class DepartmentModel implements IDepartmentModel {
    
    private static final Logger LOGGER = Logger.getLogger(DepartmentModel.class);
    private static final String DEPARTMENT_PROPERTIES_FILE = "src/resources/properties/DepartmentProperties.properties";
    private static final String DEPARTMENT_ID_PROPERTY = "DepartmentID";
    private static final String DEPARTMENT_NAME_PROPERTY = "DepartmentName";
    private IBLLFacade bllFacade;
    
    public DepartmentModel(IBLLFacade facade)
    {
        this.bllFacade = facade;
    }

    @Override
    public List<Department> getAllDepartments() throws ModelException{
        try
        {
            return bllFacade.getAllDepartments();
        }
        catch(BLLException ex)
        {
            throw new ModelException("Cannot retrieve data from the server. Please check your internet connection.", ex);
        }
    }

    @Override
    public void saveDepartment(Department department) {
        try(OutputStream output = new FileOutputStream(DEPARTMENT_PROPERTIES_FILE))
        {
            Properties departmentProperties = new Properties();
            departmentProperties.setProperty(DEPARTMENT_ID_PROPERTY, Integer.toString(department.getId()));
            departmentProperties.setProperty(DEPARTMENT_NAME_PROPERTY, department.getName());
            departmentProperties.store(output, null);
        }
        catch(IOException ex)
        {
            LOGGER.warn("Cannot save department informations", ex);
        }
    }

    @Override
    public Department loadDepartment() {
        try(InputStream input = new FileInputStream(DEPARTMENT_PROPERTIES_FILE))
        {
            Properties departmentProperties = new Properties();
            departmentProperties.load(input);
            String departmentIdAsString = departmentProperties.getProperty(DEPARTMENT_ID_PROPERTY, null);
            String departmentName = departmentProperties.getProperty(DEPARTMENT_NAME_PROPERTY, null);
            if(departmentIdAsString != null && departmentName != null)
            {
                int departmentId = Integer.parseInt(departmentIdAsString);
                return new Department(departmentId, departmentName);
            }
            else
            {
                return null;
            }
        }
        catch(NumberFormatException ex)
        {
            LOGGER.warn("Cannot read department informations", ex);
            return null;
        }
        catch(IOException ex)
        {
            LOGGER.warn("Cannot read department informations", ex);
            return null;
        }
    }
    
}
