/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.model.interfaces;

import beltracker.be.Department;
import beltracker.gui.exception.ModelException;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface IDepartmentModel {
    
    Department loadDepartment();
    
    List<Department> getAllDepartments() throws ModelException;
    
    void saveDepartment(Department department);
    
}
