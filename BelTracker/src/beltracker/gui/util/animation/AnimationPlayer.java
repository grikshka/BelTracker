/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.gui.util.animation;

import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Kiddo
 */
public class AnimationPlayer {
    
    public void playSlideAndShow(Stage stageToShow)
    {
        int delay = 1;
        int period = 7;
        
        stageToShow.setY(stageToShow.getY()- 50);
        Timer moveVerticalTimer = new Timer();
        moveVerticalTimer.scheduleAtFixedRate(new TimerTask()
        {
            private int moveCount = 0;
            
            @Override
            public void run()
            {                   
                stageToShow.setY(stageToShow.getY()+1.3);
                moveCount++;
                int moveLimit = moveCount;
                if(moveLimit >= 40)
                {
                    moveVerticalTimer.cancel();
                }
            }
        }, delay, period);
        
        stageToShow.setOpacity(0);
        Timeline mainTimeline = new Timeline();
        KeyValue stageFadeValue = new KeyValue(stageToShow.opacityProperty(), 1);
        KeyFrame stageFadeIn = new KeyFrame(Duration.millis(280), stageFadeValue);
        mainTimeline.getKeyFrames().add(stageFadeIn);
        mainTimeline.play();
    }

    public void playSlideAndClose(Stage stageToClose)
    {       
        int delay = 1;
        int period = 8;
        
        Timer moveVerticalTimer = new Timer();
        moveVerticalTimer.scheduleAtFixedRate(new TimerTask()
        {
            private int moveCount = 0;
                     
            @Override
            public void run()
            {
                stageToClose.setY(stageToClose.getY()-1.3);
                moveCount++;
                int moveLimit = moveCount;
                if(moveLimit >= 100)
                {
                    moveVerticalTimer.cancel();
                }
            }
        }, delay, period);
        
        stageToClose.setOpacity(1);
        Timeline mainTimeline = new Timeline();
        KeyValue stageFadeValue = new KeyValue(stageToClose.opacityProperty(), 0);
        KeyFrame stageFadeOut = new KeyFrame(Duration.millis(300), stageFadeValue);
        mainTimeline.getKeyFrames().add(stageFadeOut);
        mainTimeline.play();
        
        mainTimeline.setOnFinished(e -> {
            stageToClose.close();
	});
    }
    
}
