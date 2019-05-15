/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.dao;

import beltracker.be.Department;
import beltracker.be.Task;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class TaskDAO {
    
    public List<Task> getTasks(Connection con, Department department, LocalDate currentDate) throws SQLException
    {
        String sqlStatement = "SELECT * FROM DepartmentTask WHERE DepartmentId = ? AND IsFinished = 0 AND ? >= StartDate";
        List<Task> departmentTasks = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setInt(1, department.getId());
            statement.setDate(2, Date.valueOf(currentDate));
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("Id");
                LocalDate startDate = rs.getDate("StartDate").toLocalDate();
                LocalDate endDate = rs.getDate("EndDate").toLocalDate();
                departmentTasks.add(new Task(id, department, startDate, endDate));
            }
        }
        return departmentTasks;
    }
    
}
