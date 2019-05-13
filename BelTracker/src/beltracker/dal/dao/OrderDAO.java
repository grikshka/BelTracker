/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.dao;

import beltracker.be.Department;
import beltracker.be.Order;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Acer
 */
public class OrderDAO {
    
    public List<Order> getOrders(Connection connection, Department department, LocalDate currentDate) throws SQLException
    {
        String sqlStatement =   "SELECT d.Name DepartmentName, do.*, o.DeliveryDate, o.Number OrderNumber, c.Name CustomerName FROM DepartmentOrder do " +
                                "INNER JOIN Department d ON do.DepartmentId = d.Id " +
                                "INNER JOIN [Order] o ON do.OrderId = o.Id " +
                                "INNER JOIN OrderCustomer oc ON o.Id = oc.OrderId " +
                                "INNER JOIN Customer c ON oc.CustomerId = c.Id " +
                                "WHERE do.OrderId IN " +
                                "(SELECT OrderId FROM DepartmentOrder WHERE DepartmentId = ? AND isFinished = 0 AND ? >= startTime) " +
                                "ORDER BY do.OrderId, do.StartTime ASC";
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, department.getId());
            statement.setDate(2, Date.valueOf(currentDate));
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                while(rs.getBoolean("IsFinished"))
                {
                    rs.next();
                }
                int orderId = rs.getInt("OrderId");
                //TO DO
                //TO DO
            }
        }
        
        return null;
    }
    
}