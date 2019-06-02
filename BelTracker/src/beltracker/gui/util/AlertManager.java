/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Acer
 */
public class AlertManager {
    
    private static final String CONFIRMATION_IMAGE = "/resources/images/ConfirmationAlert.png";
    
    public void displayError(String message)
    {
        displayError(null, message, false);
    }
    
    public void displayError(String message, boolean shutdown)
    {
        displayError(null, message, shutdown);
    }
    
    public void displayError(String header, String message)
    {
        displayError(header, message, false);
    }
    
    public void displayError(String header, String message, boolean shutdown)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("BelTracker - Error");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(header);
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
    
    public boolean displayConfirmation(Stage currentStage, String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        setAlertCentering(currentStage, alertStage);
        alert.setTitle("BelTracker - Confirmation");
        alert.setGraphic(new ImageView(CONFIRMATION_IMAGE));
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/resources/css/Style.css").toExternalForm());  
        ButtonType clickedButton = alert.showAndWait().get();
        if(clickedButton == ButtonType.OK)
        {
            return true;
        }
        return false;
    }
    
    private void setAlertCentering(Stage currentStage, Stage alertStage)
    {
        double centerXPosition = currentStage.getX() + currentStage.getWidth()/2d;
        double centerYPosition = currentStage.getY() + currentStage.getHeight()/2d;
        alertStage.setOnShowing(e -> alertStage.hide());
        alertStage.setOnShown(e -> {
                alertStage.setX(centerXPosition - alertStage.getWidth()/2d);
                alertStage.setY(centerYPosition - alertStage.getHeight()/2d );
                alertStage.show();});
    }
    
}
