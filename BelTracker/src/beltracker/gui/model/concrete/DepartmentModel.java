/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model.concrete;

import beltracker.be.Department;
import beltracker.bll.IBLLFacade;
import beltracker.gui.model.interfaces.IDepartmentModel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Acer
 */
public class DepartmentModel implements IDepartmentModel {
    
    private static final String DEPARTMENT_PROPERTIES_FILE = "src/resources/properties/DepartmentProperties.properties";
    private IBLLFacade bllFacade;
    
    public DepartmentModel(IBLLFacade facade)
    {
        this.bllFacade = facade;
    }

    @Override
    public List<Department> getAllDepartments() {
        return bllFacade.getAllDepartments();
    }

    @Override
    public void saveDepartment(Department department) {
        try(OutputStream output = new FileOutputStream(DEPARTMENT_PROPERTIES_FILE))
        {
            Properties departmentProperties = new Properties();
            departmentProperties.setProperty("DepartmentID", Integer.toString(department.getId()));
            departmentProperties.setProperty("DepartmentName", department.getName());
            departmentProperties.store(output, null);
        }
        catch(IOException ex)
        {
            //TO DO
        }
    }

    @Override
    public Department loadDepartment() {
        try(InputStream input = new FileInputStream(DEPARTMENT_PROPERTIES_FILE))
        {
            Properties departmentProperties = new Properties();
            departmentProperties.load(input);
            String departmentIdAsString = departmentProperties.getProperty("DepartmentID", null);
            String departmentName = departmentProperties.getProperty("DepartmentName", null);
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
            //TO DO
            return null;
        }
        catch(IOException ex)
        {
            //TO DO
            return null;
        }
    }
    
}
