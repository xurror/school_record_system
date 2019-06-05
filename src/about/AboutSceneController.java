/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package about;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Xurror
 */
public class AboutSceneController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private AnchorPane pane3;

    @FXML
    private Label countLabel;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        translateAnimation(0.5,pane2,600);
        translateAnimation(0.5,pane3,600);
    } 
    
    public void translateAnimation(double duration, Node node, double byX){

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(duration) , node);
        translateTransition.setByX(byX);
        translateTransition.play();

    }
    
    int showSlide=0;

    @FXML
    void nextAction(ActionEvent event) {

        if (showSlide==0) {
            translateAnimation(0.5, pane2, -600);
            showSlide++; //showSlide=1
            countLabel.setText("2/3");
        }else if (showSlide==1){

            translateAnimation(0.5, pane3, -600);
            showSlide++; //showSlide=2
            countLabel.setText("3/3");

        }else {
            System.out.println("No more slides");
        }

    }

    @FXML
    void backAction(ActionEvent event) {

        if (showSlide==0){
            System.out.println("No more slide");
        }else if(showSlide==1){
            translateAnimation(0.5, pane2, 600);
            showSlide--; //showSlide=0
            countLabel.setText("1/3");
        }else if(showSlide==2){
            translateAnimation(0.5, pane3, 600);
            showSlide--; //showSlide=1
            countLabel.setText("2/3");
        }

    }

    public void about() throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/about/AboutScene.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(p));
        stage.show();
        
    }

    public void close() {
        
        Platform.exit();
        System.exit(0);
        
    }

       
    
}
