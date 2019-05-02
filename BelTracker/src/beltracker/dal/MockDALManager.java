/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import beltracker.be.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Acer
 */
public class MockDALManager implements IDALFacade{
    
    private final double ORDER_ON_TIME_PROBABILITY = 0.25;
    private final double TASK_ALMOST_FINISHED_PROGRESS = 0.9;
    private Random randGenerator = new Random();
    private List<String> customers = new ArrayList();
    
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
        List<Order> departmentOrders = new ArrayList();
        for(int i = 0; i < 100; i++)
        {
            String orderNumber = generateOrderNumber();
            String customerName = customers.get(randGenerator.nextInt(customers.size()));
            double realizedProgress = randGenerator.nextDouble();
            double estimatedProgress = generateEstimatedProgress(realizedProgress);
            Order order = new Order(orderNumber, customerName, departmentName, realizedProgress, estimatedProgress);
            departmentOrders.add(order);
        }
        return departmentOrders;
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
    
    private double generateEstimatedProgress(double realizedProgress)
    {
        if(randGenerator.nextDouble() > ORDER_ON_TIME_PROBABILITY)
        {
            return realizedProgress;
        }
        else if(realizedProgress > TASK_ALMOST_FINISHED_PROGRESS)
        {
            return realizedProgress;
        }
        else
        {
            double estimatedProgress;
            do
            {
                estimatedProgress = randGenerator.nextDouble();
            }
            while(estimatedProgress < realizedProgress);
            return estimatedProgress;
        }
    }
    
}
