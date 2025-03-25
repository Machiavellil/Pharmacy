
package com.mycompany.pharmacy.model;
import java.util.*;
public class Doctor extends User {
    String specialisation; //Specialisation of the doctor
    ArrayList<Prescription> prescription = new ArrayList<>(); //List of prescriptions the doctor has written
    public void writePrescription(Customer patient, Prescription prescription) {
        /*Doctor is supposed to search for a patient in the system after the appointment
        when the  patient is found, he will fill in a form with the prescription 
        that will contain the medication name, its dosage, and when to take
        If the patient is not found, the doctor adds him to the patient list
        */
        System.out.println("Enter the name of the patient: ");
        /*Enters patient name
        If patient is in system, proceed
        If not, add patient into system
        */
        
    };
    public void viewPatientHistory(Customer patient) {
        /* A patient's prescription contains his past prescriptions, allergies,
        medical conditions, lab and test results, and notes from past appointments with doctors
        The doctor is supposed to search for a patient in the system
        when the patient is found, he will view the patient's profile, finding the history
        If the patient is not found, the doctor adds him to the patient list
        */
    };
    public void addMedicalRecord(Customer patient, String record) {
        /* Medical records are like patient diaries
        During the appointment, a doctor will take note of what the patient complains of,
        what the doctor found, what the doctor did to help the patient (as in prescribe medicine),
        and what to watch out for (like vomiting or nausea, etc.)
        This is now stored in the system as a permanent record and could be viewed
        by the doctor in the patient's history
        The doctor is supposed to search for a patient in the system
        when the patient is found, he will add a record 
        If the patient is not found, the doctor adds him to the patient list
        */
    };
    public void consultPharmacy(Pharmacist pharmacist, String query) {
      /* A doctor consult the pharmacy about certain things which are
        whether a medicine is in stock or not, medicine substitute (in case of unavailabilty 
        or the patient cannot afford original prescription), safety checks about medications
        (in case patient is on other medications), and its dosage (things like if pills could
        be crushed for children, etc.)
        The doctor is supposed to send a note/message to the pharmacy, the pharmacist available
        will receive a notification then responds to the doctor's inquiries and the
        response will be sent back to the doctor
        */  
    };
    public void respondToConsultation(Customer patient, String response) {
        /* A patient will send a consultation to the doctor, they will search fpr
        the doctor they want to send a consultation to
        The consultation could be about taking medication with other pills,
        side effects, etc.
        After the patient sends the consult, the doctor receives a notification and views
        patient history then proceeds to send a response to the patient.
        */
    };
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
