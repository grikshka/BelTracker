/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Acer
 */
public class DbConnectionProvider {
    
    private static final Logger LOGGER = Logger.getLogger(DbConnectionProvider.class);
    private SQLServerDataSource ds;
    private long connectionExpirationTimeMillis = 10_000;
    private HashMap<Connection, Long> locked = new HashMap<>();
    private HashMap<Connection, Long> unlocked = new HashMap<>();
    
    
    public DbConnectionProvider(String propertiesFilePath) throws IOException
    {
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(propertiesFilePath));
        ds = new SQLServerDataSource();
        ds.setServerName(databaseProperties.getProperty("Server"));
        ds.setDatabaseName(databaseProperties.getProperty("Database"));
        ds.setUser(databaseProperties.getProperty("User"));
        ds.setPassword(databaseProperties.getProperty("Password"));
    }
    
    public synchronized Connection getConnection() throws SQLServerException
    {     
        long currentTimeMillis = System.currentTimeMillis();
        Connection connection;
        if(!unlocked.isEmpty())
        {
            Iterator<Connection> unlockedConnections = unlocked.keySet().iterator();
            while(unlockedConnections.hasNext())
            {
                connection = unlockedConnections.next();
                if(currentTimeMillis - unlocked.get(connection) > connectionExpirationTimeMillis)
                {
                    unlocked.remove(connection);
                    expireConnection(connection);
                    connection = null;
                }
                else if(validateConnection(connection))
                {
                    unlocked.remove(connection);
                    locked.put(connection, currentTimeMillis);
                    return connection;
                }
                else
                {
                    unlocked.remove(connection);
                    expireConnection(connection);
                }
            }
            
        }
        connection = createConnection();
        locked.put(connection, currentTimeMillis);
        return connection;
    }
        
    public void releaseConnection(Connection con)
    {
        locked.remove(con);
        unlocked.put(con, System.currentTimeMillis());
    }
    
    private Connection createConnection() throws SQLServerException
    {
        return ds.getConnection();
    }
    
    private void expireConnection(Connection con)
    {
        try
        {
            con.close();
        }
        catch(SQLException ex)
        {
            LOGGER.error("Unable to close the connection", ex);
        }
    }
    
    private boolean validateConnection(Connection con)
    {
        try
        {
            return !(con.isClosed());
        }
        catch(SQLException ex)
        {
            LOGGER.error("Unable to check if connection is closed", ex);
            return false;
        }
    }
   
    
}
