/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *
 * @author Acer
 */
public class AlertManager {
    
    public void displayError(String message)
    {
        displayError(message, false);
    }
    
    public void displayError(String message, boolean shutdown)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        if(shutdown)
        {
            alert.showAndWait();
            Platform.exit();
        }
        else
        {
            alert.show();
        } 
    }
    
}
