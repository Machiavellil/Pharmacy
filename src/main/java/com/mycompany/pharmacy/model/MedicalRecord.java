/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacy.model;

/**
 *
 * @author jojos
 */
public class MedicalRecord {
    private String patientEmail;
    private int recordNumber;
    private String condition;
    private String prescription;
    private String allergies;
    
    public MedicalRecord(String patientEmail, int recordNumber, String condition, String prescription, String allergies) {
        this.patientEmail = patientEmail;
        this.recordNumber = recordNumber;
        this.condition = condition;
        this.prescription = prescription;
        this.allergies = allergies;
    }
    
    public String fileFormat() {
        return String.format("=== Medical Record for %s ===\n" + "Record %d: \n" + "Conditions: %s\n" + "Prescriptions: %s\n" + "Allergies: %s\n" + "---", patientEmail, recordNumber, condition, prescription, allergies);
    }
     public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
    public String getPatientEmail() {
        return patientEmail;
    }
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public String getCondition() {
        return condition;
    }
    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
    public String getPrescription() {
        return prescription;
    }
    public void setAllergies(String Allergies) {
        this.allergies = allergies;
    }
    public String getAllergies() {
        return allergies;
    }
}
