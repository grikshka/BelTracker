/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.database.dao;

import beltracker.be.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class DepartmentDAO {
    
    public List<Department> getAllDepartments(Connection con) throws SQLException
    {
        String sqlStatement = "SELECT * FROM Department ";
        List<Department> allDepartments = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) 
            {
                Department department = new Department(rs.getInt("Id"), rs.getString("Name"));
                allDepartments.add(department);
            }
            return allDepartments;
        }

    }
    
}
