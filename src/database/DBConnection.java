/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Xurror
 */
public class DBConnection {
    
    private Connection connection;
    
    private String url = "jdbc:postgresql://localhost:5432/SchoolRecordSystem";
    private String user = "postgres";
    private String pass = "123456";
    
    public Connection getConnection() throws SQLException {
        try{
            connection = DriverManager.getConnection(url,user,pass);
            System.out.println("Connected");
        }
        catch (SQLException ex){
            System.out.println("DB login problem");
            NotificationType notificationType = NotificationType.INFORMATION;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("WARNING!!!");
            tray.setMessage("PLEASE MAKE SURE YOUR DATABASE IS PROPERLY SETUP");            
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(3000));
        }        
        return connection;
    }
    
}
