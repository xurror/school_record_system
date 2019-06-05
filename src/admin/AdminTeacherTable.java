/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Xurror
 */
public class AdminTeacherTable {
    
    private final SimpleIntegerProperty AdminTeacherTableDataNo;
    private final SimpleStringProperty AdminTeacherTableDataName;
    private final SimpleStringProperty AdminTeacherTableDataMatricule;
    private final SimpleStringProperty AdminTeacherTableDataEmail;
    private final SimpleStringProperty AdminTeacherTableDataFaculty;
    private final SimpleStringProperty AdminTeacherTableDataDepartment;
    private final SimpleStringProperty AdminTeacherTableDataRunningCourse;

    public AdminTeacherTable(int adminTeacherTableDataNo, String adminTeacherTableDataName, String adminTeacherTableDataMatricule, String adminTeacherTableDataEmail, String adminTeacherTableDataFaculty, String adminTeacherTableDataDepartment, String adminTeacherTableDataRunningCourse) {
        this.AdminTeacherTableDataNo = new SimpleIntegerProperty(adminTeacherTableDataNo);
        this.AdminTeacherTableDataName = new SimpleStringProperty(adminTeacherTableDataName);
        this.AdminTeacherTableDataMatricule = new SimpleStringProperty(adminTeacherTableDataMatricule);
        this.AdminTeacherTableDataEmail = new SimpleStringProperty(adminTeacherTableDataEmail);
        this.AdminTeacherTableDataFaculty = new SimpleStringProperty(adminTeacherTableDataFaculty);
        this.AdminTeacherTableDataDepartment = new SimpleStringProperty(adminTeacherTableDataDepartment);
        this.AdminTeacherTableDataRunningCourse = new SimpleStringProperty(adminTeacherTableDataRunningCourse);
    }

    public int getAdminTeacherTableDataNo() {
        return AdminTeacherTableDataNo.get();
    }

    public SimpleIntegerProperty adminTeacherTableDataNoProperty() {
        return AdminTeacherTableDataNo;
    }

    public void setAdminTeacherTableDataNo(int AdminTeacherTableDataNo) {
        this.AdminTeacherTableDataNo.set(AdminTeacherTableDataNo);
    }

    public String getAdminTeacherTableDataName() {
        return AdminTeacherTableDataName.get();
    }

    public SimpleStringProperty adminTeacherTableDataNameProperty() {
        return AdminTeacherTableDataName;
    }

    public void setAdminTeacherTableDataName(String adminTeacherTableDataName) {
        this.AdminTeacherTableDataName.set(adminTeacherTableDataName);
    }

    public String getAdminTeacherTableDataMatricule() {
        return AdminTeacherTableDataMatricule.get();
    }

    public SimpleStringProperty adminTeacherTableDataMatriculeProperty() {
        return AdminTeacherTableDataMatricule;
    }

    public void setAdminTeacherTableDataMatricule(String adminTeacherTableDataMatricule) {
        this.AdminTeacherTableDataMatricule.set(adminTeacherTableDataMatricule);
    }

    public String getAdminTeacherTableDataEmail() {
        return AdminTeacherTableDataEmail.get();
    }

    public SimpleStringProperty adminTeacherTableDataEmailProperty() {
        return AdminTeacherTableDataEmail;
    }

    public void setAdminTeacherTableDataEmail(String adminTeacherTableDataEmail) {
        this.AdminTeacherTableDataEmail.set(adminTeacherTableDataEmail);
    }
    
    public String getAdminTeacherTableDataFaculty() {
        return AdminTeacherTableDataFaculty.get();
    }

    public SimpleStringProperty adminTeacherTableDataFacultyProperty() {
        return AdminTeacherTableDataFaculty;
    }

    public void setAdminTeacherTableDataFaculty(String adminTeacherTableDataFaculty) {
        this.AdminTeacherTableDataFaculty.set(adminTeacherTableDataFaculty);
    }

    public String getAdminTeacherTableDataDepartment() {
        return AdminTeacherTableDataDepartment.get();
    }

    public SimpleStringProperty adminTeacherTableDataDepartmentProperty() {
        return AdminTeacherTableDataDepartment;
    }

    public void setAdminTeacherTableDataDepartment(String adminTeacherTableDataDepartment) {
        this.AdminTeacherTableDataDepartment.set(adminTeacherTableDataDepartment);
    }

    public String getAdminTeacherTableDataRunningCourse() {
        return AdminTeacherTableDataRunningCourse.get();
    }

    public SimpleStringProperty adminTeacherTableDataRunningCourseProperty() {
        return AdminTeacherTableDataRunningCourse;
    }

    public void setAdminTeacherTableDataRunningCourse(String adminTeacherTableDataRunningCourse) {
        this.AdminTeacherTableDataRunningCourse.set(adminTeacherTableDataRunningCourse);
    }
    
}
