/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui;

import beltracker.gui.controller.LoadingViewController;
import beltracker.gui.model.MainModel;
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
    
    @Override
    public void start(Stage stage)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/beltracker/gui/view/LoadingView.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 780, 560, Color.web("#f4f4f4"));
            
            stage.setScene(scene);
            stage.setMinWidth(1024);
            stage.setMinHeight(768);       
            Image icon = new Image(getClass().getResourceAsStream("/beltracker/gui/images/BelmanIcon.png"));       
            stage.getIcons().add(icon);
            stage.setTitle("BelTracker"); 
            
            LoadingViewController controller = fxmlLoader.getController();
            controller.injectModel(new MainModel());      
            stage.setOnShown((e) -> controller.loadMainView());
            stage.show();

        }
        catch(IOException ex)
        {
            //TO DO
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
