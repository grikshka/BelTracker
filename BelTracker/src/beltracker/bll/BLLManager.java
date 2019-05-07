/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.bll.util.OrderAnalyser;
import beltracker.be.Order;
import beltracker.be.Order.OrderStatus;
import beltracker.dal.DALFacadeFactory;
import beltracker.dal.IDALFacade;
import java.util.List;

/**
 *
 * @author Acer
 */
public class BLLManager implements IBLLFacade{
    
    private IDALFacade facade;
    private OrderAnalyser orderAnalyser;
    
    public BLLManager()
    {
        facade = DALFacadeFactory.getInstance().createFacade(DALFacadeFactory.FacadeType.MOCK);
        orderAnalyser = new OrderAnalyser();
    }

    @Override
    public List<Order> getOrders(String departmentName) {
        List<Order> orders = facade.getOrders(departmentName);
        for(Order order : orders)
        {
            OrderStatus status = orderAnalyser.checkOrderStatus(order, departmentName);
            double estimatedProgress = orderAnalyser.calculateEstimatedProgress(order);
            order.setOrderStatus(status);
            order.setEstimatedProgress(estimatedProgress);
        }
        return orders;
    }
    
    
    
}
