/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher;

import about.AboutSceneController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
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
public class TeacherSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXComboBox chooseRunningCourse;

    @FXML
    TableView<TeacherTable> teacherTableView;
    @FXML
    TableColumn<TeacherTable, String> teacherColumnMatricule;    
    @FXML
    TableColumn<TeacherTable, Double> teacherColumnCA;
    @FXML
    TableColumn<TeacherTable, Double> teacherColumnExams;
    @FXML
    TableColumn<TeacherTable, Double> teacherColumnTotal;

    @FXML
    JFXTextField jftfTeacherAttendance;
    @FXML
    JFXTextField jftfTeacherAssignment;
    @FXML
    JFXTextField jftfTeacherClassTest;
    @FXML
    JFXTextField jftfTeacherFinal;

    @FXML
    MaterialButton teacherCancelButton;
    @FXML
    MaterialButton teacherAddGPAButton;
    @FXML
    MaterialButton teacherLoadButton;

    @FXML
    Text tName;
    @FXML
    Text tMatricule;
    @FXML
    Text sName;
    @FXML
    Text sMatricule;        
    
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private AboutSceneController aboutSceneController = new AboutSceneController();       
    
    private String matricule;    
    
    public void setCurrentInfo(String matricule) {  
        this.matricule = matricule;
        System.out.println("passed in matricule = "+matricule);
        //return matricule;
            
        try {
            
            connection = database.getConnection();
            statement = connection.createStatement();
            
            resultSet = statement.executeQuery("SELECT * FROM public.teacher"
                    + " WHERE teacher_matricule = '"+this.matricule+"';");
            
            while(resultSet.next()){
                tName.setText(resultSet.getString("teacher_fname")+" "+resultSet.getString("teacher_lname"));
                tMatricule.setLayoutX(tName.getLayoutX()+10+tName.getBoundsInParent().getWidth());
                tMatricule.setText("("+resultSet.getString("teacher_matricule")+")");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fillComboBox();
        
    }
    
    void fillComboBox(){
        try {
            System.out.println("Hayaa!!! matricule again = "+this.matricule);
            connection = database.getConnection();
            statement = connection.createStatement();
            
            resultSet = statement.executeQuery("SELECT * FROM public.tcourses"
                    + " WHERE tcourses_matricule = '"+this.matricule+"' ;");
            
            while(resultSet.next()){
                comboOptions.add(resultSet.getString("tcourses_code"));
                System.out.println("Teacher course = "+resultSet.getString("tcourses_code"));
            }                        
            
            connection.close();
            statement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        } 
        
    }
    
    final ObservableList comboOptions = FXCollections.observableArrayList();
    
    private ObservableList getDataFromTeacherTableDataAndAddToObservableList(String query){
        ObservableList<TeacherTable> teacherTableData = FXCollections.observableArrayList();
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);//"SELECT * FROM teacher;"
            while(resultSet.next()){
                teacherTableData.add(new TeacherTable(
                        resultSet.getString("mycourses_matricule"),
                        resultSet.getDouble("mycourses_ca"),                        
                        resultSet.getDouble("mycourses_exams"),
                        resultSet.getDouble("mycourses_total")
                ));
                
            }
            
            connection.close();
            statement.close();
            resultSet.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }        
        
        return teacherTableData;
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //fillComboBox();
                        
        teacherColumnMatricule.setCellValueFactory(new PropertyValueFactory<TeacherTable, String>("teacherTableDataMatricule"));
        teacherColumnCA.setCellValueFactory(new PropertyValueFactory<TeacherTable, Double>("teacherTableDataCA"));  
        teacherColumnExams.setCellValueFactory(new PropertyValueFactory<TeacherTable, Double>("teacherTableDataExams"));
        teacherColumnTotal.setCellValueFactory(new PropertyValueFactory<TeacherTable, Double>("teacherTableDataTotal"));        
        
        chooseRunningCourse.setItems(comboOptions);
        //chooseCourse.setItems(comboChoose);
    }
    /*
    public void fillComboBox(){
           
    }        
*/
    private void setAllDisable(){        
        jftfTeacherAttendance.setDisable(true);
        jftfTeacherAssignment.setDisable(true);
        jftfTeacherClassTest.setDisable(true);
        jftfTeacherFinal.setDisable(true);

        teacherCancelButton.setDisable(true);
        teacherAddGPAButton.setDisable(true);        
    }

    private void setAllEnable(){        
        jftfTeacherAttendance.setDisable(false);
        jftfTeacherAssignment.setDisable(false);        
        jftfTeacherClassTest.setDisable(false);
        jftfTeacherFinal.setDisable(false);

        teacherCancelButton.setDisable(false);
        teacherAddGPAButton.setDisable(false);        
    }

    private void setAllClear(){        
        jftfTeacherAttendance.clear();
        jftfTeacherAssignment.clear();        
        jftfTeacherClassTest.clear();
        jftfTeacherFinal.clear();       
    }
    
    @FXML
    private void setTeacherLoadClick(Event event) throws SQLException {
        try{
            //chooseRunningCourse.getItems().clear();
            String course = chooseRunningCourse.getValue().toString();
            teacherTableView.setItems(getDataFromTeacherTableDataAndAddToObservableList(
                    "SELECT * FROM public.mycourses"
                            + " WHERE mycourses_code = '"+course+"';"
            ));
        
        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING !!!");
            tray.setMessage("You Must Select A Course");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }
        
    }

    @FXML
    private void setTeacherSelectClick(Event event){
        try{
            setAllEnable();
            try {
                String stdMatricule = teacherTableView.getSelectionModel().getSelectedItem().getTeacherTableDataMatricule();
                connection = database.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM public.student"
                        + " WHERE student_matricule = '"+stdMatricule+"';");

                while(resultSet.next()){
                    sName.setText(resultSet.getString("student_fname")+" "+resultSet.getString("student_lname"));
                    sMatricule.setLayoutX(sName.getLayoutX()+10+sName.getBoundsInParent().getWidth());
                    sMatricule.setText("("+stdMatricule+")");

                }

            } catch (SQLException ex) {
                Logger.getLogger(TeacherSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("You Must Select A Student");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }    
            
    }

    @FXML
    private void setTeacherCancelClick(Event event){
        setAllDisable();
        setAllClear();
        sName.setText("Name");
        sMatricule.setLayoutX(sName.getLayoutX()+10+sName.getBoundsInParent().getWidth());
        sMatricule.setText("(Matricule)");
    }


    @FXML
    private void setTeacherAddMarksClick(Event event){
        try{
            sName.setText("Name");
            sMatricule.setLayoutX(sName.getLayoutX()+10+sName.getBoundsInParent().getWidth());
            sMatricule.setText("(Matricule)");

            String stdMatricule = teacherTableView.getSelectionModel().getSelectedItem().getTeacherTableDataMatricule();

            Double attendance, assignment;
            Double classTest, CA;
            Double finalm, total;

            if(jftfTeacherAttendance.getText().isEmpty())
                attendance = 0.00;

            else if(jftfTeacherAssignment.getText().isEmpty())
                assignment = 0.00;

            else if(jftfTeacherClassTest.getText().isEmpty())
                classTest = 0.00;

            else if(jftfTeacherFinal.getText().isEmpty())
                finalm = 0.00;

            else{
                attendance = Double.parseDouble(jftfTeacherAttendance.getText().trim());
                assignment = Double.parseDouble(jftfTeacherAssignment.getText().trim());        
                classTest = Double.parseDouble(jftfTeacherClassTest.getText().trim());
                finalm = Double.parseDouble(jftfTeacherFinal.getText().trim());

                if(attendance + assignment + classTest <=30.0 && finalm <=70.0){

                    total = attendance+assignment+classTest+finalm;
                    CA = attendance + assignment + classTest;

                    try {                    
                        connection = database.getConnection();
                        statement = connection.createStatement();

                        statement.executeUpdate(
                                "UPDATE public.mycourses"
                                + " SET mycourses_ca = '"+CA+"',"
                                        + " mycourses_exams = '"+finalm+"',"
                                        + " mycourses_total = '"+total+"'"
                                + " WHERE mycourses_matricule = '"+stdMatricule+"'"
                                        + " AND mycourses_code = '"+chooseRunningCourse.getValue().toString()+"';"
                        );

                    } 
                    catch (SQLException e) {
                        e.printStackTrace();

                    }

                    NotificationType notificationType = NotificationType.SUCCESS;
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle("SUCCESS !!!");
                    tray.setMessage("Student Marks Record Added Sucessfully.");
                    tray.setNotificationType(notificationType);
                    tray.showAndDismiss(Duration.millis(3000));

                }

                else{            
                    NotificationType notificationType = NotificationType.ERROR;
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle("WARNING !!!");
                    tray.setMessage("Something is Wrong Please Check it again.");
                    tray.setNotificationType(notificationType);
                    tray.showAndDismiss(Duration.millis(3000)); 

                }

            }

            String course = chooseRunningCourse.getValue().toString();
            teacherTableView.setItems(getDataFromTeacherTableDataAndAddToObservableList(
                    "SELECT * FROM public.mycourses"
                            + " WHERE mycourses_code = '"+course+"';"
            ));

            setAllDisable();
            setAllClear();
        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("You Must Select A Course");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }
    }

    @FXML
    private void setCourseAboutButtonClick(Event event) throws IOException {
        aboutSceneController.about();
    }

    @FXML
    private void setCourseCloseButtonClick(Event event){
        aboutSceneController.close();
    }
    
    @FXML
    private void setTeacherChangePasswordClick(Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/teacher/ChangeTeacherPasswordScene.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        
        ChangeTeacherPasswordSceneController editProfile = loader.getController();
        editProfile.setInfo(tMatricule.getText());
                            
        stage.setTitle("");
        stage.show();
    }

}
