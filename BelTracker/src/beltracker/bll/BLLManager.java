/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.be.Department;
import beltracker.bll.util.OrderAnalyser;
import beltracker.be.Order;
import beltracker.be.Order.OrderStatus;
import beltracker.be.Task;
import beltracker.bll.util.TaskAnalyser;
import beltracker.dal.IDALFacade;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Acer
 */
public class BLLManager implements IBLLFacade{
    
    private IDALFacade dalFacade;
    private OrderAnalyser orderAnalyser;
    private TaskAnalyser taskAnalyser;
    
    public BLLManager(IDALFacade facade)
    {
        this.dalFacade = facade;
        orderAnalyser = new OrderAnalyser();
        taskAnalyser = new TaskAnalyser();
    }

    @Override
    public List<Order> getOrders(Department department) {
        LocalDate currentDate = LocalDate.now();
        List<Order> orders = dalFacade.getOrders(department, currentDate);
        for(Order order : orders)
        {
            OrderStatus status = orderAnalyser.analyseOrderStatus(order, department);
            order.setStatus(status);
            Task task = order.getDepartmentTask();
            double estimatedProgress = taskAnalyser.calculateEstimatedProgress(task);
            task.setEstimatedProgress(estimatedProgress);
        }
        return orders;
    }

    @Override
    public List<Department> getAllDepartments() {
        return dalFacade.getAllDepartments();
    }
    
    
    
}
