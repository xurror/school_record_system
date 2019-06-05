/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Xurror
 */
public class Main extends Application {
    
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.stage=primaryStage;

        MainWindow();

    }

    private void MainWindow() {

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("SplashScene.fxml"));
            AnchorPane pane=loader.load();
            SplashSceneController controller=loader.getController();
            controller.main(stage,this);
            Scene scene=new Scene(pane);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void closeStage(){
        stage.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
