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
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Kiddo
 */
public class TaskSearcherTest {
    
    public TaskSearcherTest() {
    }

    private TaskSearcher taskSearcher;
    
    @Before
    public void setUp() {
        taskSearcher = new TaskSearcher();
    }
    /**
     * Test of searchTasksByOrderNumber method, of class TaskSearcher.
     */
    @Test
    public void testSearchTasksByOrderNumber() 
    {
        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        LocalDate deliveryDate = LocalDate.of(2019, 1, 1);
        Department department = new Department(1, "DepartmentOne");
        Order order1 = new Order(1, "1111-11111", "Customer", deliveryDate, department);
        Order order2 = new Order(2, "2222-22222", "Customer", deliveryDate, department);
        Order order3 = new Order(3, "3333-33333", "Customer", deliveryDate, department);
        Task task1 = new Task(1, department, startDate, endDate);
        Task task2 = new Task(1, department, startDate, endDate);
        Task task3 = new Task(1, department, startDate, endDate);
        task1.setOrder(order1);
        task2.setOrder(order2);
        task3.setOrder(order3);
        List<Task> tasks1 = new ArrayList();
        List<Task> tasks2 = new ArrayList();
        tasks1.add(task1);
        tasks1.add(task2);
        tasks1.add(task3);
        tasks2.add(task1);
        String key = "1111-11111";
        
        List<Task> expected = tasks2;
        List<Task> actual = taskSearcher.searchTasksByOrderNumber(tasks1, key);        
        assertEquals(expected, actual);
    }

    /**
     * Test of searchTasksByOrderCustomer method, of class TaskSearcher.
     */
    @Test
    public void testSearchTasksByOrderCustomer() 
    {
        LocalDate startDate = LocalDate.of(2019, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        LocalDate deliveryDate = LocalDate.of(2019, 6, 6);
        Department department = new Department(1, "DepartmentOne");
        Order order1 = new Order(1, "1111-11111", "CustomerOne", deliveryDate, department);
        Order order2 = new Order(2, "1111-11111", "CustomerTwo", deliveryDate, department);
        Order order3 = new Order(3, "1111-11111", "CustomerThree", deliveryDate, department);
        Task task1 = new Task(1, department, startDate, endDate);
        Task task2 = new Task(1, department, startDate, endDate);
        Task task3 = new Task(1, department, startDate, endDate);
        task1.setOrder(order1);
        task2.setOrder(order2);
        task3.setOrder(order3);
        List<Task> tasks1 = new ArrayList();
        List<Task> tasks2 = new ArrayList();
        tasks1.add(task1);
        tasks1.add(task2);
        tasks1.add(task3);
        tasks2.add(task1);
        String key = "CustomerOne";
        
        List<Task> expected = tasks2;
        List<Task> actual = taskSearcher.searchTasksByOrderCustomer(tasks1, key);      
        assertEquals(expected, actual);
    }
    
}
