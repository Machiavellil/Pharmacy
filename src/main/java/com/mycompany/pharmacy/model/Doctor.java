package com.mycompany.pharmacy.model;
import com.mycompany.pharmacy.handler.PharmSystem;
import com.mycompany.pharmacy.model.Medicine;
import com.mycompany.pharmacy.model.Prescription;
import com.mycompany.pharmacy.handler.PrescriptionManager;
import com.mycompany.pharmacy.handler.PrescriptionPersistence;
import java.util.*; //For lists, scanner, etc.
import java.io.*; //Exception Handling
import java.nio.file.*; //Buffer, paths, files, etc.

public class Doctor extends User {
    String specialisation; //Specialisation of the doctor.
    String prescriptionFile = "src/main/java/com/mycompany/pharmacy/database/prescriptions.txt"; //File variable for prescriptions.
    String medicines = "src/main/java/com/mycompany/pharmacy/database/Drugs.txt"; //File variable for drugs.
    ArrayList<String> consultations = new ArrayList<>(); //List of consultations.
    Scanner input = new Scanner(System.in); //Object in Scanner for inputting.
    public Doctor(String email, String password) {
        super(email, password);
    }
    public void writePrescription(User patient, String DocName, String prescriptionFile, String medicinesInput, int prescriptionID) {
       try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(prescriptionFile), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
           /*This part allows us to write into the file multiple times, and it
           creates the file if it doesn't exist, and appends into the file if it exists.*/
              bw.write("# Prescription for " + patient.getEmail() + " (customer)\n"); //Prescription header.
              bw.write(String.format("%d, %s, %s\n", prescriptionID, DocName, patient.getEmail()));
              /*Writes in the file with a fixed format.*/
              if (medicinesInput.contains(",")) {
                  String[] medicines = medicinesInput.split("\\s*, \\s*");
                  for (String med : medicines) {
                      bw.write("MED: " + med.trim() + "\n");
                  }
              }
              else {
                  bw.write("MED: " + medicinesInput.trim() + "\n");
              }
              /*This parts handles the data entry of either one medicine, or multiple
              medicines.*/
              bw.write("END");
       }
       catch (IOException e) {
           System.err.println("Failed to write prescription: " + e.getMessage());
       }
       //Catches file access errors and prints user-friendly message.
    };
    public void viewPatientHistory(User patient) {
        
        System.out.println("\n=== Patient History ===");
        
        /* A patient's history contains his past prescriptions, allergies,
        medical conditions, lab and test results, and notes from past appointments with doctors
        The doctor is supposed to search for a patient in the system
        when the patient is found, he will view the patient's profile, finding the history
        If the patient is not found, the doctor adds him to the patient list
        */
    };
    public void addMedicalRecord(User patient, String record) {
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
        System.out.println("What do you want to know about?");
        query = input.nextLine();
        System.out.println("Thank you for reaching out!\nA pharmacist will respond to you shortly.");
        /*A doctor consults the pharmacy about certain things which include whether a medicine is
        in stock or not, medicine substitute (in case of unavailabilty
        or the patient cannot afford original prescription), safety checks about medications
        (in case patient is on other medications), and its dosage (things like if pills could
        be crushed for children, etc.)
        The doctor is supposed to send a note/message to the pharmacy, the pharmacist available
        will receive a notification then responds to the doctor's inquiries and the
        response will be sent back to the doctor.*/  
    };
    public void viewConsultations() {
        if (consultations.isEmpty()) {
            System.out.println("No consultations.");
        }
        else {
            System.out.println("Total number of consultations are: " + consultations.size());
            for (String consults : consultations) {
                
            }
        }
        /*Patient would have filled in a consultation form, which would be sent to the doctor
        and would be viewed by the doctor to respond to them.
        They would be stored in an ArrayList.*/  
    };
    public void respondToConsultation(Customer patient, String response) {
        /* A patient will send a consultation to the doctor, they will search for
        the doctor they want to send a consultation to
        The consultation could be about taking medication with other pills,
        side effects, etc.
        After the patient sends the consult, the doctor receives a notification and views
        patient history then proceeds to send a response to the patient.
        */
    };
}

