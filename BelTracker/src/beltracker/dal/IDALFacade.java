/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import beltracker.be.Department;
import beltracker.be.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface IDALFacade {
    
    List<Order> getOrders(Department department, LocalDate currentDate);
    
    List<Department> getAllDepartments();
    
}
