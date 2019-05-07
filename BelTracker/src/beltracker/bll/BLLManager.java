/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll;

import beltracker.be.Order;
import beltracker.be.Order.OrderStatus;
import beltracker.dal.DALFacadeFactory;
import beltracker.dal.IDALFacade;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.List;

/**
 *
 * @author Acer
 */
public class BLLManager implements IBLLFacade{
    
    private IDALFacade facade;
    
    public BLLManager()
    {
        facade = DALFacadeFactory.getInstance().createFacade(DALFacadeFactory.FacadeType.MOCK);
    }

    @Override
    public List<Order> getOrders(String departmentName) {
        List<Order> orders = facade.getOrders(departmentName);
        for(Order order : orders)
        {
            OrderStatus status = checkOrderStatus(order, departmentName);
            double estimatedProgress = calculateEstimatedProgress(order);
            order.setOrderStatus(status);
            order.setEstimatedProgress(estimatedProgress);
        }
        return orders;
    }
    
    private OrderStatus checkOrderStatus(Order order, String departmentName)
    {
        if(LocalDate.now().isAfter(order.getDeliveryDate()))
        {
            return OrderStatus.DELAYED;
        }
        else if(!order.getDepartmentName().equals(departmentName))
        {
            return OrderStatus.OVERDUE;
        }
        else
        {
            return OrderStatus.ON_SCHEDULE;
        }
    }
    
    private double calculateEstimatedProgress(Order order)
    {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = order.getStartDate();
        LocalDate deliveryDate = order.getDeliveryDate();
        if(currentDate.isBefore(startDate))
        {
            return 0;
        }
        else if(currentDate.isBefore(deliveryDate))
        {
            double totalNumberOfDays = DAYS.between(startDate, deliveryDate);
            double currentNumberOfDays = DAYS.between(startDate, currentDate);
            return currentNumberOfDays/totalNumberOfDays;
        }
        else
        {
            return 1;
        }
    }
    
}
