/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import about.AboutSceneController;
import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.SignUpSceneController;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Xurror
 */
public class AdminSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    TableView<AdminTable> adminTableView;
    @FXML
    TableColumn<AdminTable,Integer> adminTableStudentID;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentName;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentMatricule;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentFaculty;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentDepartment;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentPhone;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentEmail;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentDOB;
    
    @FXML
    private Button AdminCancelClick;
    @FXML
    private Button AdminSaveClick;
    @FXML
    private Button AdminLoadClick;
    
    @FXML
    private JFXTextField jtfStdFName;
    @FXML
    private JFXTextField jtfStdLName;
    @FXML
    private JFXTextField jtfStdMatricule;
    
    @FXML
    private ComboBox chooseFaculty;
    @FXML
    private ComboBox chooseDepartment;
    
    @FXML
    private JFXTextField jtfStdEmail;
    @FXML
    private JFXTextField jtfStdPhone;
    @FXML
    private DatePicker dpStdDOB;
    @FXML
    private JFXTextField jtfAdminSearch;
    
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private boolean isSetAdminAddStudentClick;
    private boolean isSetAdminEditClick;
    private AboutSceneController aboutSceneController = new AboutSceneController();

    private String temp;
    
    /*private ObservableList<AdminTable> adminTableData = FXCollections.observableArrayList(

            //Make a Observable List from adminTable by creating an Object
            //This Observable List can take data with every Property
            //new AdminTable(1, name, ....)
    );*/

    final ObservableList comboOptions1 = FXCollections.observableArrayList();
    final ObservableList comboOptions2 = FXCollections.observableArrayList();
    
    private ObservableList getDataFromSqlAndAddToObservableList(String query){
        ObservableList<AdminTable>adminTableData = FXCollections.observableArrayList();
        
        try {

            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);//"SELECT * FROM students;"
            
            while(resultSet.next()){
                
                adminTableData.add(new AdminTable(
                    resultSet.getInt("student_id"),
                    resultSet.getString("student_fname")+" "+resultSet.getString("student_lname"),
                    resultSet.getString("student_matricule"),
                    resultSet.getString("student_faculty"),
                    resultSet.getString("student_department"),
                    resultSet.getString("student_email"),
                    resultSet.getString("student_phone"),                    
                    resultSet.getString("student_dob")
                ));
                
            }
            
            connection.close();
            statement.close();
            resultSet.close();
            
        } catch (SQLException e) {
            
            e.printStackTrace();
            
        }
        
        return adminTableData;

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //1st initially get all data from MySQL server
        //And set it to Observable arrayList
        //getDataFromSqlAndAddToObservableList();

        //Get data from adminTableData ObservableList and set thus data on JavaFX table column
        fillComboBox();                
        
        adminTableStudentID.setCellValueFactory(new PropertyValueFactory<AdminTable,Integer>("adminTableDataStudentID")); // Point JavaFX adminTableStudentNo column at AdminTable's adminTableDataNo variable Property
        adminTableStudentName.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentName"));
        adminTableStudentMatricule.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentMatricule"));
        adminTableStudentFaculty.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentFaculty"));
        adminTableStudentDepartment.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentDepartment"));        
        adminTableStudentEmail.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentEmail"));
        adminTableStudentPhone.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentPhone"));        
        adminTableStudentDOB.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentDOB"));

        adminTableView.setItems(getDataFromSqlAndAddToObservableList("SELECT * FROM public.student;"));//Point JavaFX table at adminTableData ObservableList
        
        chooseFaculty.setItems(comboOptions1);
    }
    
    private void adminSetAllEnable(){
        jtfStdFName.setDisable(false);
        jtfStdLName.setDisable(false);
        jtfStdMatricule.setDisable(false);
        chooseFaculty.setDisable(false);
        chooseDepartment.setDisable(false);
        jtfStdEmail.setDisable(false);
        jtfStdPhone.setDisable(false);
        dpStdDOB.setDisable(false);

        AdminSaveClick.setDisable(false);
        AdminLoadClick.setDisable(false);
        AdminCancelClick.setDisable(false);

    }

    private void adminSetAllDisable(){
        jtfStdFName.setDisable(true);
        jtfStdLName.setDisable(true);
        jtfStdMatricule.setDisable(true);
        chooseFaculty.setDisable(true);
        chooseDepartment.setDisable(true);
        jtfStdEmail.setDisable(true);
        jtfStdPhone.setDisable(true);
        dpStdDOB.setDisable(true);
        
        AdminSaveClick.setDisable(true);
        AdminLoadClick.setDisable(true);
        AdminCancelClick.setDisable(true);

    }
    
    private void adminSetAllClear(){
        
        jtfStdFName.clear();
        jtfStdLName.clear();
        jtfStdMatricule.clear();
        jtfStdEmail.clear();
        jtfStdPhone.clear();
        chooseDepartment.getItems().clear();
        //chooseFaculty.getItems().clear();
        
    }
    
    public void fillComboBox(){
        try {
            System.out.println("Alright What's Wrong?");
            
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT school_faculty" 
                    + " FROM public.school;");
            
            while(resultSet.next()){
                comboOptions1.add(resultSet.getString("school_faculty"));
                System.out.println(resultSet.getString("school_faculty"));
            }
            connection.close();
            statement.close();           
            
        } catch (SQLException ex) {
            Logger.getLogger(SignUpSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }                
        
    }
    
    @FXML
    private void setLoadClick(Event event){
        try{
            chooseDepartment.getItems().clear();
            try {
                System.out.println("Alright What's Wrong Here Too?");
                connection = database.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT *" 
                        + " FROM public.school"
                        + " WHERE school_faculty = '"+chooseFaculty.getValue().toString()+"';");

                while(resultSet.next()){
                    comboOptions2.add(resultSet.getString("school_department1"));
                    comboOptions2.add(resultSet.getString("school_department2"));
                    comboOptions2.add(resultSet.getString("school_department3"));
                    System.out.println(resultSet.getString("school_department1"));
                }
                connection.close();
                statement.close();           

            } catch (SQLException ex) {
                Logger.getLogger(SignUpSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
            chooseDepartment.setItems(comboOptions2);
            
        }catch(NullPointerException e){
            NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("You Must Select A Faculty");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
            
        }    
        
    }
        
    @FXML
    private void setAdminSaveClick(Event event){
        
        try{
            connection = database.getConnection();
            statement = connection.createStatement();

            if(isSetAdminAddStudentClick){
                int rowsAffected = statement.executeUpdate(
                        "INSERT INTO public.student "+
                        "(student_fname, student_lname, "
                        + "student_matricule, student_faculty, "+
                        "student_department, student_email, "
                        + "student_phone, student_dob)"+
                        "VALUES ('"+jtfStdFName.getText()
                        +"', '"+jtfStdLName.getText()
                        +"', '"+jtfStdMatricule.getText()
                        +"', '"+chooseFaculty.getValue().toString()
                        +"', '"+chooseDepartment.getValue().toString()
                        +"', '"+jtfStdEmail.getText()
                        +"', '"+jtfStdPhone.getText()
                        +"', '"+dpStdDOB.getValue()
                        +"');"
                );
                
                statement.executeUpdate(
                        "INSERT INTO public.studentgpa "
                        + "(studentgpa_matricule) "
                        + "VALUES ('"+jtfStdMatricule.getText()+"')"
                );
                /*
                statement.executeUpdate(
                        "INSERT INTO public.mycourses"
                        + "(mycourses_matricule)"
                        + " VALUES ('"+jtfStdMatricule.getText()+"')"
                );
                */
                
            }
            else if (isSetAdminEditClick){

                int rowsAffected = statement.executeUpdate(
                        "UPDATE public.student SET "
                        +"student_fname = '"+jtfStdFName.getText()+"', "
                        +"student_lname = '"+jtfStdLName.getText()+"', "
                        +"student_matricule = '"+jtfStdMatricule.getText()+"', "
                        +"student_faculty = '"+chooseFaculty.getValue().toString()+"', "
                        +"student_department = '"+chooseDepartment.getValue().toString()+"', "
                        +"student_email = '"+jtfStdEmail.getText()+"', "
                        +"student_phone = '"+jtfStdPhone.getText()+"', "
                        +"student_dob = '"+dpStdDOB.getValue()+"'"
                        + "WHERE student_fname = '"+jtfStdFName.getText()+"'"
                        + " AND  student_lname = '"+jtfStdLName.getText()+"' ;"
                );
                
                if(temp == null){
                    statement.executeUpdate(
                        "INSERT INTO public.studentgpa "
                        + "(studentgpa_matricule) "
                        + "VALUES ('"+jtfStdMatricule.getText()+"')"
                    );
                    /*
                    statement.executeUpdate(
                            "INSERT INTO public.mycourses"
                            + "(mycourses_matricule)"
                            + " VALUES ('"+jtfStdMatricule.getText()+"')"
                    );
                    */
                    
                }
                else{
                    statement.executeUpdate(
                        "UPDATE public.studentgpa "
                        + "SET studentgpa_matricule ='"+jtfStdMatricule.getText()+"'"
                        + "WHERE studentgpa_matricule = '"+temp+"';"                          
                    );
                    
                    statement.executeUpdate(
                        "UPDATE public.mycourses "
                        + "SET mycourses_matricule ='"+jtfStdMatricule.getText()+"'"
                        + "WHERE mycourses_matricule = '"+temp+"';"
                    );
                }                                                

            }

            connection.close();
            statement.close();
            resultSet.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            
        }
        adminSetAllClear();
        adminSetAllDisable();
        
        adminTableView.setItems(getDataFromSqlAndAddToObservableList("SELECT * FROM public.student;"));
        
        isSetAdminEditClick = false;
        isSetAdminAddStudentClick = false;

    }
    
    @FXML
    private void setAdminCancelClick(Event event){
        
        adminSetAllClear();
        adminSetAllDisable();
        
        isSetAdminEditClick=false;
        isSetAdminAddStudentClick = false;
        
    }
    
    @FXML
    private void setAdminRefreshClick(Event event){
        
        adminTableView.setItems(getDataFromSqlAndAddToObservableList("SELECT * FROM public.student;"));//sql Query
        jtfAdminSearch.clear();
        
    }


    @FXML
    private void setAdminSearchClick(Event event){
        
        String sqlQuery = "SELECT * FROM public.student "
                + "WHERE student_matricule = '"+jtfAdminSearch.getText()+"'"
                + " OR student_fname = '"+jtfAdminSearch.getText()+"';";
        
        adminTableView.setItems(getDataFromSqlAndAddToObservableList(sqlQuery));
        
    }

    @FXML
    private void setAdminEditClick(Event event){       
        try{
            AdminTable getSelectedRow = adminTableView.getSelectionModel().getSelectedItem();
            String name = getSelectedRow.getAdminTableDataStudentName();
            temp = getSelectedRow.getAdminTableDataStudentMatricule();      

            String sqlQuery = "SELECT * FROM public.student "
                    + "WHERE student_matricule = '"+getSelectedRow.getAdminTableDataStudentMatricule()+"'"
                    + " OR (student_fname = '"+name.substring(0, name.indexOf(" "))+"'"
                    + " AND  student_lname = '"+name.substring(name.indexOf(" ")+1)+"') ;";
            System.out.println(getSelectedRow.getAdminTableDataStudentMatricule());
            System.out.println(name.substring(0, name.indexOf(" ")));
            System.out.println(name.substring(name.indexOf(" ")+1));

            try {

                connection = database.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sqlQuery);

                adminSetAllEnable();

                while(resultSet.next()) {                
                    jtfStdFName.setText(resultSet.getString("student_fname"));
                    jtfStdLName.setText(resultSet.getString("student_lname"));
                    jtfStdMatricule.setText(resultSet.getString("student_matricule"));
                    chooseFaculty.setValue(resultSet.getString("student_faculty"));
                    chooseDepartment.setValue(resultSet.getString("student_department"));
                    jtfStdEmail.setText(resultSet.getString("student_email"));
                    jtfStdPhone.setText(resultSet.getString("student_phone"));

                    try {

                        if (!(resultSet.getString("student_dob").isEmpty())) {
                            dpStdDOB.setValue(LocalDate.parse(resultSet.getString("student_dob")));

                        }

                    }
                    catch (NullPointerException e){

                        dpStdDOB.setValue(null);

                    }

                }

                temp=jtfStdMatricule.getText();
                isSetAdminEditClick = true;

            }

            catch (SQLException e) {

                e.printStackTrace();

            }
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
    private void setAdminDeleteClick(Event event){
        try{
            AdminTable getSelectedRow = adminTableView.getSelectionModel().getSelectedItem();
            String sqlQuery = "DELETE FROM public.student "
                    + "WHERE student_matricule = '"+getSelectedRow.getAdminTableDataStudentMatricule()+"';";

            try {

                connection = database.getConnection();
                statement = connection.createStatement();

                statement.executeUpdate(sqlQuery);
                statement.executeUpdate(
                        "DELETE FROM public.studentgpa "
                        + "WHERE studentgpa_matricule ='"+getSelectedRow.getAdminTableDataStudentMatricule()+"';"
                );
                adminTableView.setItems(
                        getDataFromSqlAndAddToObservableList(
                                "SELECT * FROM public.student;"
                        )
                );

            }

            catch (SQLException e) {

                e.printStackTrace();

            }

            adminTableView.setItems(getDataFromSqlAndAddToObservableList(sqlQuery));
            
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
    private void setAdminAddStudentClick(Event event){        
        adminSetAllEnable();
        isSetAdminAddStudentClick = true;
        
    }

    @FXML
    private void setAdminCloseClick(Event event){
        
       aboutSceneController.close();
        
    }

    @FXML
    private void setAdminAboutClick(Event event) throws IOException {
        
        aboutSceneController.about();
        
    }

    @FXML
    private void setAdminCoursePanelClick(Event event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/admin/AdminCourseScene.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setTitle("Course Panel");
        stage.show();
        
    }

    @FXML
    private void setAdminTeacherPanelClick(Event event)throws IOException{
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/admin/AdminTeacherScene.fxml"));
        loader.load();
        Parent pa = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(pa));
        stage.setTitle("Teacher Panel");
        stage.show();
        
    }    
    
}
