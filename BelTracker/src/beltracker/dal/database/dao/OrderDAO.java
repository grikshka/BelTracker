/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.database.dao;

import beltracker.be.Department;
import beltracker.be.Order;
import beltracker.be.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Acer
 */
public class OrderDAO {
    
    public Order getOrder(Connection con, Task task) throws SQLException
    {
        String sqlStatement = "SELECT TOP 1 dt.OrderId, o.Number OrderNumber, o.DeliveryDate, c.Name CustomerName, dt.DepartmentId, d.Name DepartmentName FROM DepartmentTask dt " +
                                "INNER JOIN Department d ON dt.DepartmentId = d.Id " +
                                "INNER JOIN [Order] o ON dt.OrderId = o.Id " +
                                "INNER JOIN OrderCustomer oc ON o.Id = oc.OrderId " +
                                "INNER JOIN Customer c ON oc.CustomerId = c.Id " +
                                "WHERE dt.OrderId IN " +
                                "(SELECT OrderId FROM DepartmentTask WHERE Id = ?) " +
                                "AND dt.IsFinished = 0 " +
                                "ORDER BY dt.StartDate";
        try(PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setInt(1, task.getId());
            ResultSet rs = statement.executeQuery();
            Order taskOrder = null;
            if(rs.next())
            {
                int orderId = rs.getInt("OrderId");
                String orderNumber = rs.getString("OrderNumber");
                LocalDate deliveryDate = rs.getDate("DeliveryDate").toLocalDate();
                String customerName = rs.getString("CustomerName");
                Department currentDepartment = new Department(rs.getInt("DepartmentId"), rs.getString("DepartmentName"));
                taskOrder = new Order(orderId, orderNumber, customerName, deliveryDate, currentDepartment);
            }
            return taskOrder;
        }
    }
    
}
