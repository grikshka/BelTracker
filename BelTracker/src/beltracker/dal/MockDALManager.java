/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import beltracker.be.Order;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Acer
 */
public class MockDALManager implements IDALFacade{
    private final int AMOUNT_OF_ORDERS = 100;
    private final double ORDER_OVERDUE_PROBABILITY = 0.10;
    private final double ORDER_DELAYED_PROBABILITY = 0.10;
    private final Random randGenerator = new Random();
    private final List<String> customers = new ArrayList<>();
    
    public MockDALManager()
    {
        initializeCustomersList();
    }
    
    private void initializeCustomersList()
    {
        customers.add("Microsoft");
        customers.add("Boeing");
        customers.add("Phillips");
        customers.add("Exor");
        customers.add("Chevron");
        customers.add("Ford");
        customers.add("Enel");
        customers.add("Hitachi");
        customers.add("ChemChina");
        customers.add("Norinco");
    }

    @Override
    public List<Order> getOrders(String departmentName) 
    {
        List<Order> departmentOrders = new ArrayList<>();
        for(int i = 0; i < AMOUNT_OF_ORDERS; i++)
        {
            Order order = generateOrder(departmentName);
            departmentOrders.add(order);
        }
        return departmentOrders;
    }
    
    private Order generateOrder(String departmentName)
    {
        double randomDouble = randGenerator.nextDouble();
        if(randomDouble < ORDER_DELAYED_PROBABILITY)
        {
            return generateDelayedOrder(departmentName);
        }
        else if(randomDouble < ORDER_DELAYED_PROBABILITY + ORDER_OVERDUE_PROBABILITY)
        {
            return generateOverdueOrder();
        }
        else
        {
            return generateOnScheduleOrder(departmentName);
        }
    }
    
    private Order generateDelayedOrder(String departmentName)
    {
        String orderNumber = generateOrderNumber();
        String customerName = customers.get(randGenerator.nextInt(customers.size()));
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(randGenerator.nextInt(7) + 4);
        LocalDate deliveryDate = currentDate.minusDays(randGenerator.nextInt(2) + 1);
        return new Order(orderNumber, customerName, departmentName, startDate, deliveryDate);
    }
    
    private Order generateOverdueOrder()
    {
        String orderNumber = generateOrderNumber();
        String customerName = customers.get(randGenerator.nextInt(customers.size()));
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(randGenerator.nextInt(2) + 1);
        LocalDate deliveryDate = currentDate.plusDays(randGenerator.nextInt(7) + 1);
        return new Order(orderNumber, customerName, "PreviousDepartment", startDate, deliveryDate);
    }
    
    private Order generateOnScheduleOrder(String departmentName)
    {
        String orderNumber = generateOrderNumber();
        String customerName = customers.get(randGenerator.nextInt(customers.size()));
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(randGenerator.nextInt(4) + 1);
        LocalDate deliveryDate = currentDate.plusDays(randGenerator.nextInt(4) + 1);
        return new Order(orderNumber, customerName, departmentName, startDate, deliveryDate);
    }
    
    private String generateOrderNumber()
    {
        String orderNumber = "";
        orderNumber += randGenerator.nextInt(900) + 100;
        orderNumber += "-";
        orderNumber += randGenerator.nextInt(900) + 100;
        orderNumber += "-";
        orderNumber += randGenerator.nextInt(90) + 10;
        return orderNumber;
    }
    
    private LocalDate generateOrderDeliveryDate()
    {
        LocalDate currentDate = LocalDate.now();
        int daysToAdd = randGenerator.nextInt(14) - 3;
        LocalDate deliveryDate = currentDate.plusDays(daysToAdd);
        return deliveryDate;
    }
    
    private LocalDate generateOrderStartDate(LocalDate deliveryDate)
    {
        int daysToSubstract = randGenerator.nextInt(7) + 3;
        LocalDate startDate = deliveryDate.minusDays(daysToSubstract);
        return startDate;
    }
    
}
