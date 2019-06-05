/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

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
public class ChangePasswordSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXPasswordField jftfStudentPassword1;
    @FXML
    private JFXPasswordField jftfStudentPassword2;    
    
    @FXML
    private MaterialButton studentCancelClick;
    @FXML
    private MaterialButton studentSaveClick;       
    
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    static Stage stage;
    
    private String matricule;
    
    void setInfo(String matricule) {
        this.matricule = matricule;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO       
    }
    
    @FXML
    private void setStudentSaveClick(Event event){
        
        try{
            connection = database.getConnection();
            statement = connection.createStatement();                        
            
            if((jftfStudentPassword2.getText()).equals(jftfStudentPassword1.getText())){
                statement.executeUpdate(
                    "UPDATE public.student SET "
                    +"student_password = '"+jftfStudentPassword1.getText()+"'"
                    +" WHERE student_matricule = '"+this.matricule+"';"
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
        jftfStudentPassword1.clear();
        jftfStudentPassword2.clear();  

    }
    
    @FXML
    private void setStudentCancelClick(Event event){
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
        
}
