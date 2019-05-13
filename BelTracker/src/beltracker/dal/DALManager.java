/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import beltracker.be.Department;
import beltracker.be.Order;
import beltracker.dal.dao.DepartmentDAO;
import beltracker.dal.dao.OrderDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Acer
 */
public class DALManager implements IDALFacade{
    
    private static final String DB_PROPERTIES_FILE_PATH = "src/resources/properties/DatabaseProperties.properties";
    private DbConnectionProvider connector;
    private OrderDAO orderDao;
    private DepartmentDAO departmentDao;
    
    public DALManager()
    {
        try
        {
            connector = new DbConnectionProvider(DB_PROPERTIES_FILE_PATH);
            orderDao = new OrderDAO();
            departmentDao = new DepartmentDAO();
        }
        catch(IOException ex)
        {
            //TO DO
        }
    }

    @Override
    public List<Order> getOrders(Department department, LocalDate currentDate) 
    {
        try(Connection con = connector.getConnection())
        {
            return orderDao.getOrders(con, department, currentDate);
        } 
        catch(SQLServerException ex) 
        {
            //TO DO
            return null;
        }
        catch(SQLException ex)
        {
            //TO DO
            return null;
        }
    }

    @Override
    public List<Department> getAllDepartments() 
    {
        try(Connection con = connector.getConnection())
        {
            return departmentDao.getAllDepartments(con);
        } 
        catch(SQLServerException ex) 
        {
            //TO DO
            return null;
        }
        catch(SQLException ex)
        {
            //TO DO
            return null;
        }
    }
    
}
