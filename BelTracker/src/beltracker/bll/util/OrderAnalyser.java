/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll.util;

import beltracker.be.Department;
import beltracker.be.Order;
import beltracker.be.Order.OrderStatus;
import java.time.LocalDate;

/**
 *
 * @author Acer
 */
public class OrderAnalyser {
    
    public OrderStatus analyseOrderStatus(Order order, Department department)
    {
        if(!(order.getCurrentDepartment().equals(department)))
        {
            return OrderStatus.OVERDUE;
        }
        else if(LocalDate.now().isAfter(order.getDepartmentTask().getEndDate()))
        {
            return OrderStatus.DELAYED;
        }
        else
        {
            return OrderStatus.ON_SCHEDULE;
        }
    }
    
}
