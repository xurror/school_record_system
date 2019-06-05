/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Xurror
 */
public class AdminTable {
    private final SimpleIntegerProperty adminTableDataStudentID;
    private final SimpleStringProperty adminTableDataStudentName;
    private final SimpleStringProperty adminTableDataStudentMatricule;
    private final SimpleStringProperty adminTableDataStudentFaculty;
    private final SimpleStringProperty adminTableDataStudentDepartment;
    private final SimpleStringProperty adminTableDataStudentPhone;
    private final SimpleStringProperty adminTableDataStudentEmail;
    private final SimpleStringProperty adminTableDataStudentDOB;

    public AdminTable(int adminTableDataStudentID, String adminTableDataStudentName, String adminTableDataStudentMatricule, String adminTableDataStudentFaculty, String adminTableDataStudentDepartment, String adminTableDataStudentPhone, String adminTableDataStudentEmail, String adminTableDataStudentDOB) {
        this.adminTableDataStudentID = new SimpleIntegerProperty(adminTableDataStudentID);
        this.adminTableDataStudentName = new SimpleStringProperty(adminTableDataStudentName);
        this.adminTableDataStudentMatricule = new SimpleStringProperty(adminTableDataStudentMatricule);
        this.adminTableDataStudentFaculty = new SimpleStringProperty(adminTableDataStudentFaculty);
        this.adminTableDataStudentDepartment = new SimpleStringProperty(adminTableDataStudentDepartment);
        this.adminTableDataStudentPhone = new SimpleStringProperty(adminTableDataStudentEmail);
        this.adminTableDataStudentEmail = new SimpleStringProperty(adminTableDataStudentPhone);
        this.adminTableDataStudentDOB = new SimpleStringProperty(adminTableDataStudentDOB);
    }

    public Integer getAdminTableDataStudentID() {
        return adminTableDataStudentID.get();
    }

    public SimpleIntegerProperty adminTableDataStudentIDProperty() {
        return adminTableDataStudentID;
    }

    public void setAdminTableDataStudentID(Integer adminTableDataStudentID) {
        this.adminTableDataStudentID.set(adminTableDataStudentID);
    }

    public String getAdminTableDataStudentName() {
        return adminTableDataStudentName.get();
    }

    public SimpleStringProperty adminTableDataStudentNameProperty() {
        return adminTableDataStudentName;
    }

    public void setAdminTableDataStudentName(String adminTableDataStudentName) {
        this.adminTableDataStudentName.set(adminTableDataStudentName);
    }

    public String getAdminTableDataStudentMatricule() {
        return adminTableDataStudentMatricule.get();
    }

    public SimpleStringProperty adminTableDataStudentMatriculeProperty() {
        return adminTableDataStudentMatricule;
    }

    public void setAdminTableDataStudentMatricule(String adminTableDataStudentMatricule) {
        this.adminTableDataStudentMatricule.set(adminTableDataStudentMatricule);
    }    

    public String getAdminTableDataStudentFaculty() {
        return adminTableDataStudentFaculty.get();
    }

    public SimpleStringProperty adminTableDataStudentFacultyProperty() {
        return adminTableDataStudentFaculty;
    }

    public void setAdminTableDataStudentFaculty(String adminTableDataStudentFaculty) {
        this.adminTableDataStudentFaculty.set(adminTableDataStudentFaculty);
    }

    public String getAdminTableDataStudentDepartment() {
        return adminTableDataStudentDepartment.get();
    }

    public SimpleStringProperty adminTableDataStudentDepartmentProperty() {
        return adminTableDataStudentDepartment;
    }

    public void setAdminTableDataStudentDepartment(String adminTableDataStudentDepartment) {
        this.adminTableDataStudentDepartment.set(adminTableDataStudentDepartment);
    }

    public String getAdminTableDataStudentPhone() {
        return adminTableDataStudentPhone.get();
    }

    public SimpleStringProperty adminTableDataStudentPhoneProperty() {
        return adminTableDataStudentPhone;
    }

    public void setAdminTableDataStudentPhone(String adminTableDataStudentPhone) {
        this.adminTableDataStudentPhone.set(adminTableDataStudentPhone);
    }
    
    public String getAdminTableDataStudentEmail() {
        return adminTableDataStudentEmail.get();
    }

    public SimpleStringProperty adminTableDataStudentEmailProperty() {
        return adminTableDataStudentEmail;
    }

    public void setAdminTableDataStudentEmail(String adminTableDataStudentEmail) {
        this.adminTableDataStudentEmail.set(adminTableDataStudentEmail);
    }

    public String getAdminTableDataStudentDOB() {
        return adminTableDataStudentDOB.get();
    }

    public SimpleStringProperty adminTableDataStudentDOBProperty() {
        return adminTableDataStudentDOB;
    }

    public void setAdminTableDataStudentDOB(String adminTableDataStudentDOB) {
        this.adminTableDataStudentDOB.set(adminTableDataStudentDOB);
    }

}
