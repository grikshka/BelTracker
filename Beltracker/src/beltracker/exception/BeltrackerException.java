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
public class BeltrackerException extends Exception {
    
    public BeltrackerException(String message)
    {
        super(message);
    }
    
    public BeltrackerException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
}
