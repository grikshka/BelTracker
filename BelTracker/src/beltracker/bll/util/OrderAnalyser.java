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
import static java.time.temporal.ChronoUnit.DAYS;

/**
 *
 * @author Acer
 */
public class OrderAnalyser {
    
    public OrderStatus analyseOrderStatus(Order order, Department department)
    {
        if(LocalDate.now().isAfter(order.getDepartmentTask().getEndDate()))
        {
            return OrderStatus.DELAYED;
        }
        else if(!(order.getCurrentDepartment().getId() == department.getId()))
        {
            return OrderStatus.OVERDUE;
        }
        else
        {
            return OrderStatus.ON_SCHEDULE;
        }
    }
    
}
