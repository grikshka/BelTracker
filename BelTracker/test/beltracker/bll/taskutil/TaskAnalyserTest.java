/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll.taskutil;

import beltracker.be.Department;
import beltracker.be.Order;
import beltracker.be.Task;
import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Kiddo
 */
public class TaskAnalyserTest {
    
    public TaskAnalyserTest() {
    }
    
    private TaskAnalyser taskAnalyser;  
    
    @Before
    public void setUp() {
        taskAnalyser = new TaskAnalyser();
    }

    /**
     * Test of calculateEstimatedProgress method, of class TaskAnalyser.
     */
    @Test
    public void testCalculateEstimatedProgressBeforeStartDate() 
    {
        LocalDate currentDate = LocalDate.of(2018, 1, 1);
        LocalDate startDate = LocalDate.of(2019, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        Department department = new Department(1, "DepartmentOne");
        Task task = new Task(1, department, startDate, endDate);
        
        double expected = 0;
        double actual = taskAnalyser.calculateEstimatedProgress(task, currentDate);
        assertEquals(expected, actual, 0);
    }
    
    @Test
    public void testCalculateEstimatedProgressBeforeDeliveryDate() 
    {
        LocalDate currentDate = LocalDate.of(2019, 1, 1);
        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        Department department = new Department(1, "DepartmentOne");
        Task task = new Task(1, department, startDate, endDate);
        
        double expected = 0.5;
        double actual = taskAnalyser.calculateEstimatedProgress(task, currentDate);
        assertEquals(expected, actual, 0);
    }
    
    @Test
    public void testCalculateEstimatedProgressAfterDate() 
    {
        LocalDate currentDate = LocalDate.of(2020, 1, 1);
        LocalDate startDate = LocalDate.of(2019, 1, 1);
        LocalDate endDate = LocalDate.of(2018, 1, 1);
        Department department = new Department(1, "DepartmentOne");
        Task task = new Task(1, department, startDate, endDate);
        
        double expected = 1;
        double actual = taskAnalyser.calculateEstimatedProgress(task, currentDate);
        assertEquals(expected, actual, 0);
    }

    /**
     * Test of analyseStatus method, of class TaskAnalyser.
     */
    @Test
    public void testAnalyseStatusOverdue()
    {
        LocalDate currentDate = LocalDate.of(2018, 1, 1);
        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        LocalDate deliveryDate = LocalDate.of(2019, 1, 1);
        Department department = new Department(1, "DepartmentOne");
        Department currentTaskDepartment = new Department(2, "DepartmentTwo");
        Order order = new Order(1, "1111-11111", "Customer", deliveryDate, currentTaskDepartment);
        Task task = new Task(1, department, startDate, endDate);
        task.setOrder(order);
        
        Task.Status expected = Task.Status.OVERDUE;
        Task.Status actual = taskAnalyser.analyseStatus(task, department, currentDate);        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAnalyseStatusDelay()
    {
        LocalDate currentDate = LocalDate.of(2021, 1, 1);
        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        LocalDate deliveryDate = LocalDate.of(2019, 6, 6);
        Department department = new Department(1, "DepartmentOne");
        Department currentTaskDepartment = new Department(1, "DepartmentOne");
        Order order = new Order(1, "1111-11111", "Customer", deliveryDate, currentTaskDepartment);
        Task task = new Task(1, department, startDate, endDate);
        task.setOrder(order);
        
        Task.Status expected = Task.Status.DELAYED;
        Task.Status actual = taskAnalyser.analyseStatus(task, department, currentDate);       
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAnalyseStatusOnSchedule()
    {
        LocalDate currentDate = LocalDate.of(2018, 1, 1);
        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        LocalDate deliveryDate = LocalDate.of(2019, 1, 1);
        Department department = new Department(1, "DepartmentOne");
        Department currentTaskDepartment = new Department(1, "DepartmentOne");
        Order order = new Order(1, "1111-11111", "Customer", deliveryDate, currentTaskDepartment);
        Task task = new Task(1, department, startDate, endDate);
        task.setOrder(order);
        
        Task.Status expected = Task.Status.ON_SCHEDULE;
        Task.Status actual = taskAnalyser.analyseStatus(task, department,currentDate);       
        assertEquals(expected, actual);
    }
    
}
