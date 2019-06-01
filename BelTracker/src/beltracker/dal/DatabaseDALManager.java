/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal;

import beltracker.dal.datatransfer.DataTransfer;
import beltracker.dal.database.DbConnectionProvider;
import beltracker.be.Department;
import beltracker.be.Order;
import beltracker.be.Task;
import beltracker.dal.database.dao.DataTransferDAO;
import beltracker.dal.database.dao.DepartmentDAO;
import beltracker.dal.database.dao.EventLogDAO;
import beltracker.dal.database.dao.OrderDAO;
import beltracker.dal.database.dao.TaskDAO;
import beltracker.dal.datafile.FolderWatcher;
import beltracker.dal.datafile.dataconverter.JSONConverter;
import beltracker.dal.datafile.dataconverter.XLSXConverter;
import beltracker.dal.datafile.dataconverter.csvconverter.CSVConverter;
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
public class DatabaseDALManager implements IDALFacade{
    
    private static final String DB_PROPERTIES_FILE_PATH = "src/resources/properties/DatabaseProperties.properties";
    private static final String NEW_DATA_FILES_FOLDER = "data/NewData";
    private static final String INSERTED_DATA_FILES_FOLDER = "data/InsertedData";
    private static final String INVALID_DATA_FILES_FOLDER = "data/InvalidData";
    
    private DbConnectionProvider connector;
    private FolderWatcher fileWatcher;
    private JSONConverter jsonConverter;
    private CSVConverter csvConverter;
    private XLSXConverter xlsxConverter;
    
    private DataTransferDAO dataTransferDao;
    private TaskDAO taskDao;
    private OrderDAO orderDao;
    private DepartmentDAO departmentDao;
    private EventLogDAO logDao;
    
    public DatabaseDALManager()
    {
        try
        {
            connector = new DbConnectionProvider(DB_PROPERTIES_FILE_PATH);
            fileWatcher = new FolderWatcher(NEW_DATA_FILES_FOLDER);
            fileWatcher.register(this);
            jsonConverter = new JSONConverter();
            csvConverter = new CSVConverter();
            xlsxConverter = new XLSXConverter();
            
            dataTransferDao = new DataTransferDAO();
            taskDao = new TaskDAO();
            orderDao = new OrderDAO();
            departmentDao = new DepartmentDAO();
            logDao = new EventLogDAO();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            //TO DO
        }
    }

    @Override
    public List<Task> getTasks(Department department, LocalDate currentDate) 
    {
        Connection con = null;
        try
        {
            con = connector.getConnection();
            List<Task> tasks = taskDao.getTasks(con, department, currentDate);
            for(Task departmentTask : tasks)
            {
                Order taskOrder = orderDao.getOrder(con, departmentTask);
                departmentTask.setOrder(taskOrder);
            }
            return tasks;
        } 
        catch(SQLServerException ex) 
        {
            ex.printStackTrace();
            return null;
            //TO DO
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return null;
            //TO DO
        }
        finally
        {
            if(con != null)
            {
                connector.releaseConnection(con);
            }
        }
    }

    @Override
    public List<Department> getAllDepartments() 
    {
        Connection con = null;
        try
        {
            con = connector.getConnection();
            return departmentDao.getAllDepartments(con);
        } 
        catch(SQLServerException ex) 
        {
            ex.printStackTrace();
            return null;
            //TO DO
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            return null;
            //TO DO
        }
        finally
        {
            if(con != null)
            {
                connector.releaseConnection(con);
            }
        }
    }

    @Override
    public void submitTask(Task task, long currentEpochTime) {
        Connection con = null;
        try
        {
            con = connector.getConnection();
            taskDao.submitTask(con, task);
            logDao.logEvent(con, task, currentEpochTime, EventLogDAO.EventType.SUBMIT_TASK);
        } 
        catch(SQLServerException ex) 
        {
            ex.printStackTrace();
            //TO DO
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            //TO DO
        }
        finally
        {
            if(con != null)
            {
                connector.releaseConnection(con);
            }
        }
    }

    @Override
    public void update(String pathToNewFile, FileType fileType) {
        Connection con = null;
        try
        {
            con = connector.getConnection();
            DataTransfer newData = null;
            switch(fileType)
            {
                case JSON:
                    newData = jsonConverter.convertFileData(pathToNewFile);
                    break;

                case CSV:
                    newData = csvConverter.convertFileData(pathToNewFile);
                    break;

                case XLSX:
                    newData = xlsxConverter.convertFileData(pathToNewFile);
                    break;
                
                case INVALID_FILE_TYPE:
                    //TO DO
                    break;
            }
            dataTransferDao.transferData(con, newData);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            //TO DO
        }
        finally
        {
            if(con != null)
            {
                connector.releaseConnection(con);
            }
        }
    }
    
}
