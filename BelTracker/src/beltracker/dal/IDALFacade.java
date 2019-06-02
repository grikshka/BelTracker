/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import beltracker.be.Department;
import beltracker.be.Task;
import beltracker.dal.exception.DALException;
import java.time.LocalDate;
import java.util.List;
import beltracker.dal.util.dataobserver.DataObserver;

/**
 *
 * @author Acer
 */
public interface IDALFacade extends DataObserver{
    
    List<Task> getTasks(Department department, LocalDate currentDate) throws DALException;
    
    void submitTask(Task task, long currentEpochTime) throws DALException;
    
    List<Department> getAllDepartments() throws DALException;
    
}
