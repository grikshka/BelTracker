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
public class TaskEstimatedProgressComparator implements Comparator<Task> {
    
    public enum Type {
        ASC, DESC
    }
    
    private Type type;
    
    public TaskEstimatedProgressComparator(Type type)
    {
        this.type = type;
    }

    @Override
    public int compare(Task t1, Task t2) {
        if(type == Type.ASC)
        {
            return Double.valueOf(t1.getEstimatedProgress()).compareTo(t2.getEstimatedProgress());
        }
        else
        {
            return Double.valueOf(t2.getEstimatedProgress()).compareTo(t1.getEstimatedProgress());
        }
    }
    
}
