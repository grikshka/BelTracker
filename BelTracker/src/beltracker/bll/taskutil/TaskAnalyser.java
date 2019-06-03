/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.bll.taskutil;

import beltracker.be.Department;
import beltracker.be.Task;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 *
 * @author Acer
 */
public class TaskAnalyser {
     
    public double calculateEstimatedProgress(Task task, LocalDate currentDate)
    {
        LocalDate startDate = task.getStartDate();
        LocalDate deliveryDate = task.getEndDate();
        if(currentDate.isBefore(startDate))
        {
            return 0;
        }
        else if(currentDate.isBefore(deliveryDate))
        {
            double totalNumberOfDays = DAYS.between(startDate, deliveryDate);
            double currentNumberOfDays = DAYS.between(startDate, currentDate);
            return currentNumberOfDays/totalNumberOfDays;
        }
        else
        {
            return 1;
        }
    }
    
    public Task.Status analyseStatus(Task task, Department department, LocalDate currentDate)
    {
        if(!(task.getOrder().getCurrentDepartment().equals(department)))
        {
            return Task.Status.OVERDUE;
        }
        else if(currentDate.isAfter(task.getEndDate()))
        {
            return Task.Status.DELAYED;
        }
        else
        {
            return Task.Status.ON_SCHEDULE;
        }
    }
}
