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

    //Constructor for medical record. creates and object of type medical record.
    //Initializes the object with a patient email, record number, condition, prescription and allergies.
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

    //Sets the patient email.
     public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    //Returns the patient email.
    public String getPatientEmail() {
        return patientEmail;
    }

    //Sets the recordd number.
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    //Returns the record number.
    public int getRecordNumber() {
        return recordNumber;
    }

    //Sets the condition.
    public void setCondition(String condition) {
        this.condition = condition;
    }

    //Returns the condition.
    public String getCondition() {
        return condition;
    }

    //Sets the prescription.
    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    //Returns the prescription.
    public String getPrescription() {
        return prescription;
    }

    //Sets the allergies.
    public void setAllergies(String Allergies) {
        this.allergies = Allergies;
    }

    //Returns the allergies.
    public String getAllergies() {
        return allergies;
    }
}
