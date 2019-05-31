/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.database.dao;

import beltracker.dal.datatransfer.DataTransfer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Acer
 */
public class DataTransferDAO {
    
    public void transferData(Connection con, DataTransfer data) throws SQLException
    {
        insertEmployees(con, data.getEmployees());
        insertOrders(con, data.getOrders());
    }
    
    private void insertEmployees(Connection con, List<DataTransfer.Employee> newEmployees) throws SQLException 
    {
        String sqlStatement = "INSERT INTO Employee VALUES(?,?,?)";
        try(PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            for(DataTransfer.Employee employee : newEmployees) 
            {
                statement.setString(1, employee.getInitials());
                statement.setString(2, employee.getName());
                statement.setLong(3, employee.getSalaryNumber());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }
    
    private void insertOrders(Connection con, List<DataTransfer.Order> newOrders) throws SQLException
    {
        String sqlStatement = "INSERT INTO [Order] VALUES(?, ?, ?)";
        try(PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS))
        {
            for(DataTransfer.Order order : newOrders) 
            {
                statement.setString(1, order.getNumber());
                statement.setDate(2, Date.valueOf(order.getDeliveryDate()));
                statement.setBoolean(3, false);
                statement.execute();
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                int orderId = rs.getInt(1);
                insertOrderCustomer(con, order, orderId);
                insertDepartmentTasks(con, order, orderId);
            }
        }
    }
    
    private void insertOrderCustomer(Connection con, DataTransfer.Order order, int orderId) throws SQLException {
        String sqlStatement = "INSERT INTO OrderCustomer VALUES(?, ?)";
        try(PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            int customerID = checkCustomer(con, order);
            statement.setInt(1, orderId);
            statement.setInt(2, customerID);
            statement.execute();
        }
    }
    
    private int checkCustomer(Connection con, DataTransfer.Order order) throws SQLException
    {
        String sqlStatement = "merge [Customer] with(HOLDLOCK) as target "
                + "using (values ('newvalue')) "
                + "as source (Name) "
                + "on target.Name = ? "
                + "WHEN MATCHED THEN "
                + "UPDATE SET target.Name = target.Name "
                + "WHEN NOT MATCHED THEN "
                + "INSERT ([Name]) "
                + "VALUES (?) "
                + "OUTPUT inserted.Id ; ";
        try(PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setString(1, order.getCustomerName());
            statement.setString(2, order.getCustomerName());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("Id");
        }
    }
    
    private void insertDepartmentTasks(Connection con, DataTransfer.Order order, int orderId) throws SQLException
    {
        String sqlStatement = "INSERT INTO DepartmentTask VALUES(?,?,?,?,?)";
        try(PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            for(DataTransfer.DepartmentTask departmentTask : order.getDepartmentTasks())
            {
                int departmentId = checkDepartment(con, departmentTask);
                statement.setInt(1, departmentId);
                statement.setInt(2, orderId);
                statement.setDate(3, Date.valueOf(departmentTask.getStartDate()));
                statement.setDate(4, Date.valueOf(departmentTask.getEndDate()));
                statement.setBoolean(5, departmentTask.getIsFinished());
                statement.execute();
            }
            
        }
    }
    
    private int checkDepartment(Connection con, DataTransfer.DepartmentTask task) throws SQLException
    {
        String sqlStatement = "merge [Department] with(HOLDLOCK) as target "
                + "using (values ('newvalue')) "
                + "as source (Name) "
                + "on target.Name = ? "
                + "WHEN MATCHED THEN "
                + "UPDATE SET target.Name = target.Name "
                + "WHEN NOT MATCHED THEN "
                + "INSERT ([Name]) "
                + "VALUES (?) "
                + "OUTPUT inserted.Id ; ";
        try(PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setString(1, task.getDepartmentName());
            statement.setString(2, task.getDepartmentName());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("Id");
        }
    }
}
