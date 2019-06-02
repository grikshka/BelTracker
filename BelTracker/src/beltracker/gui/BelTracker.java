/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui;

import beltracker.gui.controller.DepartmentConfigurationViewController;
import beltracker.gui.util.AlertManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 *
 * @author Acer
 */
public class BelTracker extends Application {
    
    private static final Logger LOGGER = Logger.getLogger(BelTracker.class);
    
    private static final int STAGE_MIN_WIDTH = 1110;
    private static final int STAGE_MIN_HEIGHT = 770;
    private static final String STAGE_ICON_PATH = "/resources/images/BelmanIcon.png";
    private static final String STAGE_TITLE = "BelTracker";
    
    private static final int SCENE_WIDTH = 1100;
    private static final int SCENE_HEIGHT = 700;
    private static final String SCENE_BACKGROUND_HEXCOLOR = "#f4f4f4";
    private static final String DEPARTMENT_CONFIGURATION_VIEW_PATH = "/beltracker/gui/view/DepartmentConfigurationView.fxml";
    
    private final AlertManager alertManager;
    
    public BelTracker()
    {
        alertManager = new AlertManager();
    }
    
    @Override
    public void start(Stage stage)
    {
        try
        {
            stage.setMinWidth(STAGE_MIN_WIDTH);
            stage.setMinHeight(STAGE_MIN_HEIGHT);       
            Image icon = new Image(getClass().getResourceAsStream(STAGE_ICON_PATH));       
            stage.getIcons().add(icon);
            stage.setTitle(STAGE_TITLE); 
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DEPARTMENT_CONFIGURATION_VIEW_PATH));  
            Parent root = fxmlLoader.load();
            
            Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.web(SCENE_BACKGROUND_HEXCOLOR));        
            stage.setScene(scene);
            
            DepartmentConfigurationViewController controller = fxmlLoader.getController();            
            stage.setOnShown((e) -> controller.loadDepartmentInformations());
            
            stage.setOnCloseRequest((e) -> {
                Platform.exit();
                System.exit(0);
            });            
            stage.show();
        }
        catch(IOException ex)
        {
            LOGGER.error(ex);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
