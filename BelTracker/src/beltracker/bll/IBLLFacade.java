/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.be.Department;
import beltracker.be.Order;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface IBLLFacade {
    
    List<Order> getOrders(Department department);
    
    List<Department> getDepartments();
    
}
