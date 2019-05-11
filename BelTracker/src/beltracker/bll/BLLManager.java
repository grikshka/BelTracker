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
import beltracker.dal.DALFacadeFactory;
import beltracker.dal.IDALFacade;
import beltracker.exception.BelTrackerException;
import java.util.List;

/**
 *
 * @author Acer
 */
public class BLLManager implements IBLLFacade{
    
    private IDALFacade dalFacade;
    private OrderAnalyser orderAnalyser;
    
    public BLLManager(IDALFacade facade)
    {
        this.dalFacade = facade;
        orderAnalyser = new OrderAnalyser();
    }

    @Override
    public List<Order> getOrders(Department department) {
        List<Order> orders = dalFacade.getOrders(department);
        for(Order order : orders)
        {
            OrderStatus status = orderAnalyser.analyseOrderStatus(order, department);
            double estimatedProgress = orderAnalyser.calculateEstimatedProgress(order);
            order.setOrderStatus(status);
            order.setEstimatedProgress(estimatedProgress);
        }
        return orders;
    }

    @Override
    public List<Department> getDepartments() {
        return dalFacade.getDepartments();
    }
    
    
    
}
