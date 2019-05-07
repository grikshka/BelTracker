/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui;

import beltracker.gui.controller.LoadingViewController;
import beltracker.gui.model.MainModel;
import beltracker.gui.util.AlertManager;
import java.io.IOException;
import javafx.application.Application;
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
    private static final String LOADING_VIEW_PATH = "/beltracker/gui/view/LoadingView.fxml";
    private static final String STAGE_ICON_PATH = "/resources/images/BelmanIcon.png";
    private static final String STAGE_TITLE = "BelTracker";
    private static final String SCENE_BACKGROUND_HEXCOLOR = "#f4f4f4";
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LOADING_VIEW_PATH));
            Parent root = fxmlLoader.load();
            
            Scene scene = new Scene(root, 780, 560, Color.web(SCENE_BACKGROUND_HEXCOLOR));        
            stage.setScene(scene);
            stage.setMinWidth(1024);
            stage.setMinHeight(768);       
            Image icon = new Image(getClass().getResourceAsStream(STAGE_ICON_PATH));       
            stage.getIcons().add(icon);
            stage.setTitle(STAGE_TITLE); 
            
            LoadingViewController controller = fxmlLoader.getController();
            controller.injectModel(new MainModel());
            stage.setOnShown((e) -> controller.loadMainView());
            stage.show();

        }
        catch(IOException ex)
        {
            LOGGER.error(ex.getMessage(), ex);
            alertManager.displayUnexpectedError();
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
