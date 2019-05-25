/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.controller;

import beltracker.exception.BelTrackerException;
import beltracker.gui.model.interfaces.IMainModel;
import beltracker.gui.util.AlertManager;
import beltracker.gui.util.AnimationCreator;
import com.jfoenix.controls.JFXSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class LoadingViewController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(LoadingViewController.class);
    private static final String MAIN_VIEW_PATH = "/beltracker/gui/view/MainView.fxml";
    private final AlertManager alertManager;
    
    @FXML
    private JFXSpinner spnLoader;
    @FXML
    private ImageView imgLogo;
    
    public LoadingViewController()
    {
        alertManager = new AlertManager();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             
    }    
    
    public void showAnimation()
    {
        Node imageNode = imgLogo;
        SequentialTransition transition = AnimationCreator.createPopupAnimation(imageNode);
        transition.play();
        transition.setOnFinished((e) -> spnLoader.setVisible(true));

    }
    
    public void loadMainView(IMainModel model)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try
            {
                Parent mainView = initializeMainView(model);
                Platform.runLater(() -> showMainView(mainView));
            }
            catch(BelTrackerException ex)
            {
                LOGGER.error(ex.getMessage(), ex);
                Platform.runLater(() -> alertManager.displayError(ex.getMessage(), true));
            }
            catch(IOException ex)
            {
                LOGGER.error(ex.getMessage(), ex);
                Platform.runLater(() -> alertManager.displayError("The application was unable to start correctly", true));
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
        });
        executor.shutdown();
    }
    
    private Parent initializeMainView(IMainModel model) throws IOException, BelTrackerException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(MAIN_VIEW_PATH));
        Parent root = fxmlLoader.load();

        MainViewController controller = fxmlLoader.getController();
        controller.injectModel(model);
        controller.initializeView();
        
        return root;
    }
    
    private void showMainView(Parent mainView)
    {
        Scene currentScene = spnLoader.getScene();
        Parent currentView = currentScene.getRoot();

        SequentialTransition transition = AnimationCreator.createSwitchViewAnimation(currentScene, currentView, mainView);
        transition.play();      
    }
    
}
