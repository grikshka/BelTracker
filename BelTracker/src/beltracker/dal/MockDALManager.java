/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import beltracker.be.Department;
import beltracker.be.Order;
import beltracker.be.Task;
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
    private final List<Department> departments = new ArrayList<>();
    private final Random randGenerator = new Random();
    private final List<String> customers = new ArrayList<>();
    
    public MockDALManager()
    {
        initializeCustomersList();
        initializeDepartmentsList();
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
    
    
    private void initializeDepartmentsList() 
    {
        departments.add(new Department(1, "Halvfab"));
        departments.add(new Department(2, "Bælg"));
        departments.add(new Department(3, "Montage 1"));
        departments.add(new Department(4, "Montage 2"));
        departments.add(new Department(5, "Bertel"));
        departments.add(new Department(6, "Maler"));
        departments.add(new Department(7, "Forsendelse"));       
    }

    @Override
    public List<Order> getOrders(Department department, LocalDate currentDate) 
    {
        List<Order> departmentOrders = new ArrayList<>();
        for(int i = 0; i < AMOUNT_OF_ORDERS; i++)
        {
            int orderId = i;
            Order order = generateOrder(orderId, department, currentDate);
            departmentOrders.add(order);
        }
        return departmentOrders;
    }
    
    @Override
    public List<Department> getAllDepartments() {
        return departments;
    }
    
    private Order generateOrder(int orderId, Department department, LocalDate currentDate)
    {
        double randomDouble = randGenerator.nextDouble();
        if(randomDouble < ORDER_DELAYED_PROBABILITY)
        {
            return generateDelayedOrder(orderId, department, currentDate);
        }
        else if(randomDouble < ORDER_DELAYED_PROBABILITY + ORDER_OVERDUE_PROBABILITY)
        {
            return generateOverdueOrder(orderId, department, currentDate);
        }
        else
        {
            return generateOnScheduleOrder(orderId, department, currentDate);
        }
    }
    
    private Order generateDelayedOrder(int orderId, Department department, LocalDate currentDate)
    {
        String orderNumber = generateOrderNumber();
        String customerName = customers.get(randGenerator.nextInt(customers.size()));
        LocalDate deliveryDate = currentDate.plusDays(randGenerator.nextInt(7) + 14);
        LocalDate taskStartDate = currentDate.minusDays(randGenerator.nextInt(7) + 4);
        LocalDate taskEndDate = currentDate.minusDays(randGenerator.nextInt(2) + 1);
        Task departmentTask = new Task(taskStartDate, taskEndDate);
        return new Order(orderId, orderNumber, customerName, deliveryDate, department, departmentTask);
    }
    
    private Order generateOverdueOrder(int orderId, Department department, LocalDate currentDate)
    {
        String orderNumber = generateOrderNumber();
        String customerName = customers.get(randGenerator.nextInt(customers.size()));
        LocalDate deliveryDate = currentDate.plusDays(randGenerator.nextInt(7) + 14);
        LocalDate taskStartDate = currentDate.minusDays(randGenerator.nextInt(2) + 1);
        LocalDate taskEndDate = currentDate.plusDays(randGenerator.nextInt(7) + 1);
        Department overdueDepartment = generateOverdueDepartment(department);
        Task departmentTask = new Task(taskStartDate, taskEndDate);
        return new Order(orderId, orderNumber, customerName, deliveryDate, overdueDepartment, departmentTask);
    }
    
    private Order generateOnScheduleOrder(int orderId, Department department, LocalDate currentDate)
    {
        String orderNumber = generateOrderNumber();
        String customerName = customers.get(randGenerator.nextInt(customers.size()));
        LocalDate deliveryDate = currentDate.plusDays(randGenerator.nextInt(7) + 14);
        LocalDate taskStartDate = currentDate.minusDays(randGenerator.nextInt(4) + 1);
        LocalDate taskEndDate = currentDate.plusDays(randGenerator.nextInt(4) + 1);
        Task departmentTask = new Task(taskStartDate, taskEndDate);
        return new Order(orderId, orderNumber, customerName, deliveryDate, department, departmentTask);
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
    
    private Department generateOverdueDepartment(Department department)
    {
        Department overdueDepartment;
        do
        {
            overdueDepartment = departments.get(randGenerator.nextInt(departments.size()));
        }
        while(overdueDepartment.getId() == department.getId());
        return overdueDepartment;
    }
    
}
