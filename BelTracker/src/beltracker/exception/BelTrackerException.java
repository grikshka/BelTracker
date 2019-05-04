/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.exception;

/**
 *
 * @author Acer
 */
public class BelTrackerException extends Exception {
    
    public BelTrackerException(String message)
    {
        super(message);
    }
    
    public BelTrackerException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
}
