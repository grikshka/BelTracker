/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll.taskutil.comparator;

import beltracker.be.Task;
import java.util.Comparator;

/**
 *
 * @author Acer
 */
public class TaskEndDateComparator implements Comparator<Task>{
    
    public enum Type {
        ASC, DESC
    }
    
    private Type type;

    public TaskEndDateComparator(Type type)
    {
        this.type = type;
    }
    
    @Override
    public int compare(Task t1, Task t2) {
        if(type == Type.ASC)
        {
            return t1.getEndDate().compareTo(t2.getEndDate());
        }
        else
        {
            return t2.getEndDate().compareTo(t1.getEndDate());
        }
    }
    
}
