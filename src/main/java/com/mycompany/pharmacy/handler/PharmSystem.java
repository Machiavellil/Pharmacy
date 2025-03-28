package com.mycompany.pharmacy.handler;
import com.mycompany.pharmacy.model.Customer;
import java.util.*;

/**
 *
 * @author jojos
 */
public class PharmSystem {
    private final ArrayList<Customer> patient; //List of patients.
    String EMAIL, PASSWORD; //Variables for the object.
    Customer customer = new Customer(EMAIL, PASSWORD); //Object in Customer
    public PharmSystem() {
        patient = new ArrayList<>(); 
        /*A constructor that will create a new object in 'System' class.
        'patient' will be initialised as an empty ArrayList.*/
    }

    public Customer findPatient(String email) {
        for (Customer patient : patient) {
            if (patient.getEmail().equalsIgnoreCase(email)) {
                return patient;
            }
        }
        /*Loop that iterates through each object in the 'patient' ArrayList.
        During each iteration, it gets the next patient, and assigns it to the
        'patient' variable. It gets the patient's email while being case-insensitive.
        If a match is found, it returns the matching patient immediately, otherise, 
        it registers the patient and returns null.*/
        return null;
    }

    public void displayPatients() {
        System.out.println("Current Patients: ");
        for (Customer p : patient) {
            System.out.println(p);
        }
        /*This function displays all patients currently available in the system.*/
    }
}
