/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll.taskutil;

import beltracker.be.Task;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class TaskSearcher {
    
    public List<Task> searchTasksByOrderNumber(List<Task> tasks, String key)
    {        
        List<Task> results = new ArrayList<>();
        String generalizedKey = key.trim();
        for(Task task : tasks)
        {
            String orderNumber = task.getOrder().getNumber();
            if(generalizedKey.length() <= orderNumber.length())
            {
                if(orderNumber.substring(0, generalizedKey.length()).equals(generalizedKey))
                {
                    results.add(task);
                }
            }
        }
        return results;
    }
    
    public List<Task> searchTasksByOrderCustomer(List<Task> tasks, String key)
    {
        List<Task> results = new ArrayList<>();
        String generalizedKey = key.trim().toLowerCase();
        for(Task task : tasks)
        {
            String customerName = task.getOrder().getCustomerName().toLowerCase();
            if(generalizedKey.length() <= customerName.length())
            {
                if(customerName.substring(0, generalizedKey.length()).equals(generalizedKey))
                {
                    results.add(task);
                }
            }
        }
        return results;
    }
    
}
