/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import database.DBConnection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.postgresql.util.PSQLException;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Xurror
 */
public class ProfileSceneController {

    @FXML
    private ImageView profilePic;

    @FXML
    Text tMatricule;
    @FXML
    Text tName;
    @FXML
    Text tDepartment;
    //@FXML
    //Text tCGPA;
    @FXML
    Text tDOB;
    @FXML
    Text tEmail;
    @FXML
    Text tVEmail;
    @FXML
    Text tPhone;

    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet, resultSet1, resultSet2;    
    
    private String matricule;

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private LineChart<String,Number> gpaVisualization;

    public void setCurrentInfo(String matricule) {        
        this.matricule = matricule;
        System.out.println("passed in matricule = "+matricule);
        
        try{
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM public.student"
                            + " WHERE student_matricule = '"+matricule+"' ;"
            );

            while(resultSet.next()) {
                tMatricule.setText(resultSet.getString("student_matricule"));
                tName.setText(resultSet.getString("student_fname")+" "+resultSet.getString("student_lname"));
                tDepartment.setLayoutX(tName.getLayoutX()+10+tName.getBoundsInParent().getWidth());//Adjust Layout with name
                tDepartment.setText(resultSet.getString("student_department"));
                //tCGPA.setText(resultSet.getString("student_cgpa"));
                tVEmail.setText(resultSet.getString("student_lname")+matricule+"@"+(tDepartment.getText()).toLowerCase()+".ubuea.cm");
                tDOB.setText(resultSet.getString("student_dob"));
                tEmail.setText(resultSet.getString("student_email"));
                tPhone.setText(resultSet.getString("student_phone"));
                
                try {
                    Image image = new Image(resultSet.getString("student_picture"));
                    profilePic.setImage(image);
                }
                catch (Exception e){
                    profilePic.setImage(new Image("/image/default-user-icon.png"));
                } 

            }
            connection.close();
            statement.close();
        
        } catch (SQLException e) {
            e.printStackTrace();
        }                       

    }
    
    private void calculateGpa(){
        
        //String Grade=null;
        double gpa = 0;
        int sumCredits = 0, semesters = 1;
        
        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            
            resultSet1 = statement.executeQuery("SELECT mycourses_semester"
                    + " FROM public.mycourses"
                    + " WHERE mycourses_matricule = '"+tMatricule.getText()+"';");
            
            while(resultSet1.next()){
                semesters = resultSet1.getInt("mycourses_semester");
            }
            
            for (int i = 1; i <= semesters; i++){
            
                resultSet = statement.executeQuery("SELECT *"
                        + " FROM public.mycourses"
                        + " WHERE mycourses_matricule = '"+tMatricule.getText()+"'"
                                + " AND mycourses_semester = '"+i+"' ;");
                
                while(resultSet.next()){
                    gpa = gpa + (resultSet.getInt("mycourses_credit") * resultSet.getDouble("mycourses_total"));
                    sumCredits = sumCredits + resultSet.getInt("mycourses_credit");
                    System.out.println("GPA = "+gpa);
                    System.out.println("sumCredits = "+sumCredits);
                    
                }
                
                gpa = gpa / (sumCredits*25);
                System.out.println("GPA = "+gpa);
                System.out.println("sumCredits = "+sumCredits);
                
                try{
                    statement.executeUpdate(
                            "UPDATE public.studentgpa"
                                    + " SET studentgpa_"+i+"thsem = "+gpa+""
                                            + " WHERE studentgpa_matricule = '"+tMatricule.getText()+"' ;");
                }catch(PSQLException e){
                    NotificationType notificationType = NotificationType.INFORMATION;
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle("HEY!!!");
                    tray.setMessage("You Have Not Registered Any Course Yet");
                    tray.setNotificationType(notificationType);
                    tray.showAndDismiss(Duration.millis(3000));
                    
                }
                        
            }
        } catch (SQLException ex) {
                Logger.getLogger(ProfileSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }

    @FXML
    private void setLoadGpaButtonClcik(Event event){        
        gpaVisualization.getData().clear();
        XYChart.Series<String,Number> gpaLineChart = new XYChart.Series<String,Number>();
        calculateGpa();
               
        try {            
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM public.studentgpa"
                    + " WHERE studentgpa_matricule = '"+matricule+"' ;");

            while (resultSet.next()){

                for (int i = 1; i <= 12 ; i++) {                    
                    if(resultSet.getDouble("studentgpa_"+i+"thsem")!=0.00){
                        gpaLineChart.getData().add(new XYChart.Data<String,Number>(i+"th", resultSet.getDouble("studentgpa_"+i+"thsem")));
                    }
                }

                gpaLineChart.setName("All GPA");
                gpaVisualization.getData().add(gpaLineChart);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            
        }

    }
    
    @FXML
    public void setChangePasswordClick(Event event) throws IOException{                 
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/student/ChangePasswordScene.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        
        ChangePasswordSceneController editProfile = loader.getController();
        editProfile.setInfo(tMatricule.getText());
                            
        stage.setTitle("");
        stage.show();
        
    }
    
    @FXML
    public void setRegisterCoursesClick(Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/student/RegistrationScene.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        
        RegistrationSceneController registerCourses = loader.getController();
        registerCourses.setInfo(tMatricule.getText());
                            
        stage.setTitle("");
        stage.show();
    }
    
    @FXML
    public void setChangePictureClick(Event event) throws IOException{        
        final FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        //InputStream inStream = null;
    	//OutputStream outStream = null;
        
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null){            
            try {
                //openFile(file);
                String imagepath = file.toURI().toURL().toString();
                
                System.out.println("file:"+imagepath);
                System.out.println("Working Directory = " +
                        System.getProperty("user.dir"));
                
                Image image = new Image(imagepath);
                profilePic.setImage(image);
                
                connection = database.getConnection();
                statement = connection.createStatement();
                statement.executeUpdate("UPDATE public.student"
                        + " SET student_picture = '"+imagepath+"';");
                
                /*
                Path copied = Paths.get(imagepath.substring(8));
                Path originalPath = Paths.get(
                System.getProperty(
                "user.dir")+"/src/image/"+tMatricule.getText()+".jpg");
                
                Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
                */
                //saveToFile(image);
            } catch (SQLException ex) {
                Logger.getLogger(ProfileSceneController.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        }
        else{
            NotificationType notificationType = NotificationType.SUCCESS;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WELCOME");
            tray.setMessage("You Are Succesfully Logged In");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
                            
        }
            
    }
    
    public static void saveToFile(Image image){
        File outputFile = new File(System.getProperty(
                        "user.dir")+"/src/image/");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
          ImageIO.write(bImage, "jpg", outputFile);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
    }
    
    private static void configureFileChooser(final FileChooser fileChooser){                           
        fileChooser.setTitle("Select Pictures");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif")); // limit chooser options to image files
    }
        
    @FXML
    public void setSeeResultsClick(Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/student/ResultsScene.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        
        ResultsSceneController seeResults = loader.getController();
        seeResults.setCurrentInfo(tMatricule.getText());
                            
        stage.setTitle("");
        stage.show();
    }
    
}
