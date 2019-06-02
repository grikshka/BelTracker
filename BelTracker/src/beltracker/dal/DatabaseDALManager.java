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
import beltracker.dal.exception.DALException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Acer
 */
public class DatabaseDALManager implements IDALFacade{
    
    private static final Logger LOGGER = Logger.getLogger(DatabaseDALManager.class);
    
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
            LOGGER.error("Cannot initialize the class properly", ex);
        }
    }

    @Override
    public List<Task> getTasks(Department department, LocalDate currentDate) throws DALException 
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
            throw new DALException("Cannot connect to the database", ex);
        }
        catch(SQLException ex)
        {
            throw new DALException("Cannot retrieve data", ex);
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
    public List<Department> getAllDepartments() throws DALException 
    {
        Connection con = null;
        try
        {
            con = connector.getConnection();
            return departmentDao.getAllDepartments(con);
        } 
        catch(SQLServerException ex) 
        {
            throw new DALException("Cannot connect to the database", ex);
        }
        catch(SQLException ex)
        {
            throw new DALException("Cannot retrieve data", ex);
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
    public void submitTask(Task task, long currentEpochTime) throws DALException {
        Connection con = null;
        try
        {
            con = connector.getConnection();
            taskDao.submitTask(con, task);
            logDao.logEvent(con, task, currentEpochTime, EventLogDAO.EventType.SUBMIT_TASK);
        } 
        catch(SQLServerException ex) 
        {
            throw new DALException("Cannot connect to the database", ex);
        }
        catch(SQLException ex)
        {
            throw new DALException("Failed to execute the statement for submitting task", ex);
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
    public void update(String pathToNewFile, String newFileName, FileType fileType) {
        Connection con = null;
        Path targetFilePath = null;
        try
        {
            con = connector.getConnection();
            DataTransfer newData = null;
            switch(fileType)
            {
                case JSON:
                    newData = jsonConverter.convertFileData(pathToNewFile);
                    dataTransferDao.transferData(con, newData);
                    targetFilePath = Paths.get(INSERTED_DATA_FILES_FOLDER).resolve(Paths.get(newFileName));
                    break;

                case CSV:
                    newData = csvConverter.convertFileData(pathToNewFile);
                    dataTransferDao.transferData(con, newData);
                    targetFilePath = Paths.get(INSERTED_DATA_FILES_FOLDER).resolve(Paths.get(newFileName));
                    break;

                case XLSX:
                    newData = xlsxConverter.convertFileData(pathToNewFile);  
                    dataTransferDao.transferData(con, newData);
                    targetFilePath = Paths.get(INSERTED_DATA_FILES_FOLDER).resolve(Paths.get(newFileName));
                    break;
                
                case INVALID_FILE_TYPE:
                    targetFilePath = Paths.get(INVALID_DATA_FILES_FOLDER).resolve(Paths.get(newFileName));
                    break;
            }
            
        }
        catch(ParseException ex)
        {
            LOGGER.error("Cannot parse the new data file", ex);
        }
        catch(IOException ex)
        {
            LOGGER.error("Cannot find the new data file", ex);
        }
        catch(SQLServerException ex) 
        {
            LOGGER.error("Cannot connect to the database", ex);
        }
        catch(SQLException ex)
        {
            LOGGER.error("Failed to execute the query for inserting new data", ex);
        }
        finally
        {
            try
            {
                Files.move(Paths.get(pathToNewFile), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                if(con != null)
                {
                    connector.releaseConnection(con);
                }
            }
            catch(IOException ex)
            {
                LOGGER.error("Cannot move the data file to the target folder", ex);
            }
        }
    }
    
}
