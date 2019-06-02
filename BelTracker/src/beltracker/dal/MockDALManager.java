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
    
    private int amountOfTasks = 100;
    private final double TASK_OVERDUE_PROBABILITY = 0.10;
    private final double TASK_DELAYED_PROBABILITY = 0.10;
    private final List<Department> departments = new ArrayList<>();
    private final List<String> customers = new ArrayList<>();
    private final List<Integer> submittedTasksId = new ArrayList<>();
    private final Random randGenerator = new Random();
    
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
    public List<Task> getTasks(Department department, LocalDate currentDate) 
    {
        List<Task> departmentTasks = new ArrayList<>();
        for(int i = 0; i < amountOfTasks; i++)
        {
            if(!submittedTasksId.contains(i))
            {
                int id = i;
                Task task = generateTask(id, department, currentDate);
                departmentTasks.add(task);
            }
        }
        return departmentTasks;
    }
    
    @Override
    public List<Department> getAllDepartments() {
        return departments;
    }
    
    private Task generateTask(int id, Department department, LocalDate currentDate)
    {
        double randomDouble = randGenerator.nextDouble();
        if(randomDouble < TASK_DELAYED_PROBABILITY)
        {
            return generateDelayedTask(id, department, currentDate);
        }
        else if(randomDouble < TASK_DELAYED_PROBABILITY + TASK_OVERDUE_PROBABILITY)
        {
            return generateOverdueTask(id, department, currentDate);
        }
        else
        {
            return generateOnScheduleTask(id, department, currentDate);
        }
    }
    
    private Task generateDelayedTask(int id, Department department, LocalDate currentDate)
    {
        Order taskOrder = generateOrder(id, department, currentDate);      
        LocalDate startDate = currentDate.minusDays(randGenerator.nextInt(7) + 4);
        LocalDate endDate = currentDate.minusDays(randGenerator.nextInt(2) + 1);
        Task departmentTask = new Task(id, department, startDate, endDate);
        departmentTask.setOrder(taskOrder);
        return departmentTask;
    }
    
    private Task generateOverdueTask(int id, Department department, LocalDate currentDate)
    {
        LocalDate startDate = currentDate.minusDays(randGenerator.nextInt(2) + 1);
        LocalDate endDate = currentDate.plusDays(randGenerator.nextInt(7) + 1);
        Department overdueDepartment = generateOverdueDepartment(department);
        Order taskOrder = generateOrder(id, overdueDepartment, currentDate);
        Task departmentTask = new Task(id, department, startDate, endDate);
        departmentTask.setOrder(taskOrder);
        return departmentTask;
    }
    
    private Task generateOnScheduleTask(int id, Department department, LocalDate currentDate)
    {
        Order taskOrder = generateOrder(id, department, currentDate);
        LocalDate startDate = currentDate.minusDays(randGenerator.nextInt(4) + 1);
        LocalDate endDate = currentDate.plusDays(randGenerator.nextInt(4) + 1);
        Task departmentTask = new Task(id, department, startDate, endDate);
        departmentTask.setOrder(taskOrder);
        return departmentTask;
    }
    
    private Order generateOrder(int id, Department department, LocalDate currentDate)
    {
        String orderNumber = generateOrderNumber();
        String orderCustomerName = customers.get(randGenerator.nextInt(customers.size()));
        LocalDate orderDeliveryDate = currentDate.plusDays(randGenerator.nextInt(7) + 14);
        return new Order(id, orderNumber, orderCustomerName, orderDeliveryDate, department);
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
        while(overdueDepartment.equals(department));
        return overdueDepartment;
    }

    @Override
    public void submitTask(Task task, long currentEpochTime) {
        submittedTasksId.add(task.getId());
    }

    @Override
    public void update(String pathToNewFile, String newFileName, FileType fileType) {
        amountOfTasks += 10;
    }
    
}
