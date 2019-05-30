/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.database.dao;

import beltracker.be.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class EventLogDAO {
    
    public enum EventType {
        
        SUBMIT_TASK("Task submission");
        
        private final String message;
        
        private EventType(String m)
        {
            message = m;
        }
        
    }
    
    public void logEvent(Connection con, Task task, long currentEpochTime, EventType type) throws SQLException
    {
        switch(type)
        {
            case SUBMIT_TASK:
                logEvent(con, task, currentEpochTime, type.message);
        }
    }
    
    private void logEvent(Connection connection, Task task, long currentEpochTime, String eventMessage) throws SQLException {
        String sqlStatement = "INSERT INTO [EventLog] ([TaskId], [DepartmentId], [Event], [EpochTime]) "
                + "VALUES (?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, task.getId());
            statement.setInt(2, task.getDepartment().getId());
            statement.setString(3, eventMessage);
            statement.setLong(4, currentEpochTime);           
            statement.execute();
        }
    }
    
}
