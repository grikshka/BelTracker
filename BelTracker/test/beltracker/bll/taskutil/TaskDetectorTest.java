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
public class TaskDetectorTest {
    
    public TaskDetectorTest() {
    }
    
    private TaskDetector taskDetector;
    
    @Before
    public void setUp() {
        taskDetector = new TaskDetector();
    }


    /**
     * Test of detectNewTasks method, of class TaskDetector.
     */
    @Test
    public void testDetectNewTasks() 
    {
        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        LocalDate deliveryDate = LocalDate.of(2019, 1, 1);
        Department department = new Department(1, "DepartmentOne");    
        Order order1 = new Order(1, "1111-11111", "CustomerOne", deliveryDate, department);
        Order order2 = new Order(2, "2222-22222", "CustomerTwo", deliveryDate, department);
        Order order3 = new Order(3, "3333-33333", "CustomerThree", deliveryDate, department);
        Task task1 = new Task(1, department, startDate, endDate);
        Task task2 = new Task(2, department, startDate, endDate);
        Task task3 = new Task(3, department, startDate, endDate);
        task1.setOrder(order1);
        task2.setOrder(order2);
        task3.setOrder(order3);
        List<Task> oldList = new ArrayList();
        List<Task> newList = new ArrayList();
        oldList.add(task1);
        oldList.add(task2);
        newList.add(task1);
        newList.add(task2);
        newList.add(task3);
        
        
        List<Task> expected = new ArrayList();
        expected.add(task3);
        List<Task> actual = taskDetector.detectNewTasks(oldList, newList);      
        assertEquals(expected, actual);
    }

    /**
     * Test of detectRemovedTasks method, of class TaskDetector.
     */
    @Test
    public void testDetectRemovedTasks() 
    {
        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        LocalDate deliveryDate = LocalDate.of(2019, 1, 1);
        Department department = new Department(1, "DepartmentOne");    
        Order order1 = new Order(1, "1111-11111", "CustomerOne", deliveryDate, department);
        Order order2 = new Order(2, "2222-22222", "CustomerTwo", deliveryDate, department);
        Order order3 = new Order(3, "3333-33333", "CustomerThree", deliveryDate, department);
        Task task1 = new Task(1, department, startDate, endDate);
        Task task2 = new Task(2, department, startDate, endDate);
        Task task3 = new Task(3, department, startDate, endDate);
        task1.setOrder(order1);
        task2.setOrder(order2);
        task3.setOrder(order3);
        List<Task> oldList = new ArrayList();
        List<Task> newList = new ArrayList();
        oldList.add(task1);
        oldList.add(task2);
        newList.add(task1);
        newList.add(task3);
        
        List<Task> expected = new ArrayList();
        expected.add(task2);
        List<Task> actual = taskDetector.detectRemovedTasks(oldList, newList);
        assertEquals(expected, actual);
    }

    /**
     * Test of detectModifiedTasks method, of class TaskDetector.
     */
    @Test
    public void testDetectModifiedTasks()
    {
        LocalDate startDate = LocalDate.of(2019, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 1, 1);
        LocalDate deliveryDate = LocalDate.of(2020, 1, 1);
        Department department = new Department(1, "DepartmentOne");    
        Order order1 = new Order(1, "1111-11111", "CustomerOne", deliveryDate, department);
        Order order2 = new Order(2, "2222-22222", "CustomerTwo", deliveryDate, department);
        Order order3 = new Order(3, "3333-33333", "CustomerThree", deliveryDate, department);
        Task task1 = new Task(1, department, startDate, endDate);
        Task task2 = new Task(1, department, startDate, endDate);
        Task task3 = new Task(3, department, startDate, endDate);
        task1.setOrder(order1);
        task2.setOrder(order2);
        task3.setOrder(order3);
        List<Task> oldList = new ArrayList();
        List<Task> newList = new ArrayList();
        oldList.add(task1);
        oldList.add(task3);
        newList.add(task2);
        newList.add(task3);
        
        List<Task> expected = new ArrayList();
        expected.add(task2);
        List<Task> actual = taskDetector.detectModifiedTasks(oldList, newList);
        assertEquals(expected, actual);
    }
    
}
