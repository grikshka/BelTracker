/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.dao;

import beltracker.be.Department;
import beltracker.be.Order;
import beltracker.be.Task;
import beltracker.dal.DbConnectionProvider;
import java.io.IOException;
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
                                "AND do.isFinished = 0 " + 
                                "ORDER BY do.OrderId, do.StartTime ASC";
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            List<Order> orders = new ArrayList();
            statement.setInt(1, department.getId());
            statement.setDate(2, Date.valueOf(currentDate));
            ResultSet rs = statement.executeQuery();
            
            int previousOrderId = -1;
            while(rs.next())
            {
                int orderId = rs.getInt("OrderId");
                if(orderId != previousOrderId)
                {                   
                    String orderNumber = rs.getString("OrderNumber");
                    String customerName = rs.getString("CustomerName");
                    LocalDate deliveryDate = rs.getDate("DeliveryDate").toLocalDate();
                    Department currentDepartment = new Department(rs.getInt("DepartmentId"), rs.getString("DepartmentName"));
                    LocalDate taskStartDate = rs.getDate("StartTime").toLocalDate();
                    LocalDate taskEndDate = rs.getDate("EndTime").toLocalDate();
                    Task departmentTask = new Task(taskStartDate, taskEndDate);
                    Order order = new Order(orderId, orderNumber, customerName, deliveryDate, currentDepartment, departmentTask);
                    orders.add(order);
                    previousOrderId = orderId;
                }
            }
            return orders;
        }
    }
    
}
