/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher;

import com.jfoenix.controls.JFXPasswordField;
import database.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kairos.components.MaterialButton;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Xurror
 */
public class ChangeTeacherPasswordSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXPasswordField jftfTeacherPassword1;
    @FXML
    private JFXPasswordField jftfTeacherPassword2;    
    
    @FXML
    private MaterialButton teacherCancelClick;
    @FXML
    private MaterialButton teacherSaveClick;
    
    static Stage stage;
    
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    private String matricule;
    
    void setInfo(String matricule) {
        this.matricule = matricule;
        System.out.println("Change Password for"+matricule);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO       
    }        
    
    @FXML
    private void setTeacherSaveClick(Event event){
        
        try{
            connection = database.getConnection();
            statement = connection.createStatement();
            
            String password1 = null;
            String password2 = null;
            
            System.out.println("Hmm Change Password for"+this.matricule);
            
            System.out.println(this.matricule.substring(1, this.matricule.length()-1));
            
            password1 = jftfTeacherPassword1.getText();
            password2 = jftfTeacherPassword2.getText();
            
            if(password1.equals(password2)){
                statement.executeUpdate(
                    "UPDATE public.teacher SET "
                    +"teacher_password = '"+jftfTeacherPassword1.getText()+"'"
                    +" WHERE teacher_matricule = '"+this.matricule.substring(1, this.matricule.length()-1)+"';"
                );                                           
            }            
            else{
                NotificationType notificationType = NotificationType.ERROR;
                TrayNotification tray = new TrayNotification();
                tray.setTitle("WARNING!!!");
                tray.setMessage("Passwords Do Not Match");
                tray.setNotificationType(notificationType);
                tray.showAndDismiss(Duration.millis(3000));
            }

            connection.close();
            statement.close();            
        }
        catch (SQLException e){
            e.printStackTrace();
            
        }
        jftfTeacherPassword1.clear();
        jftfTeacherPassword2.clear();  

    }
    
    @FXML
    private void setTeacherCancelClick(Event event){
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();
    }   
    
}
