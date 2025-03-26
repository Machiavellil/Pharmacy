package com.mycompany.pharmacy.model;
import com.mycompany.pharmacy.handler.PharmSystem;
import com.mycompany.pharmacy.model.Medicine;
import com.mycompany.pharmacy.model.Prescription;
import com.mycompany.pharmacy.handler.PrescriptionManager;
import com.mycompany.pharmacy.handler.PrescriptionPersistence;
import com.mycompany.pharmacy.model.MedicalRecord;
import java.util.*; //For lists, scanner, etc.
import java.io.*; //Exception Handling
import java.nio.file.*; //Buffer, paths, files, etc.

public class Doctor extends User {
    private static final String records = "Medical Records.txt"; //String variable for the records file.
    private String specialisation; //Specialisation of the doctor.
    private int number;
    private String condition;
    private String prescriptionFile = "src/main/java/com/mycompany/pharmacy/database/prescriptions.txt"; //File variable for prescriptions.
    private String medicines = "src/main/java/com/mycompany/pharmacy/database/Drugs.txt"; //File variable for drugs.
    private ArrayList<String> consultations = new ArrayList<>(); //List of consultations.
    
    Scanner input = new Scanner(System.in); //Object in Scanner for inputting.
    
    public Doctor(String email, String password) {
        super(email, password);
        //Constructor
    };
    
    public List<MedicalRecord> viewPatientHistory(String patientEmail) throws IOException {
        //'throws' is used incase the file reading fails.
        List<String> allLines = Files.readAllLines(Paths.get(records)); //Reads the entire file.
        List<MedicalRecord> history = new ArrayList<>();
        MedicalRecord currentRecord = null;
        //Stores final list of parsed records.
        for (String line : allLines) {
            if (line.startsWith("=== Medical Record for " + patientEmail)) {
                currentRecord = new MedicalRecord(patientEmail, -1, "", "", "");
            }
            /*This part matches for patient specific header. It creates an object in Medical
            Record with -1 for unset records and "" for empty fields.*/
            else if (line.startsWith("Record ")) {
                currentRecord.setRecordNumber(number);
            }
            /*This part checks if the line starts with 'Record' and if so, it calls the
            setRecordNumber().*/
            else if (line.startsWith("Conditions: ")) {
                currentRecord.setCondition(line.substring("Conditions: ".length()));
            }
            /*This part extract the text after 'Condition' and calls the setCondition().*/
            else if (line.startsWith("Prescriptions: ")) {
                currentRecord.setPrescription(line.substring("Prescriptions: ".length()));
            }
            /*This part works similar to the Conditions part. It extracts text after
            'Prescription' and calls the setPrescription().*/
            else if (line.startsWith("Allergies: ")) {
                currentRecord.setAllergies(line.substring("Allergies: ".length()));
            }
            /*This part also works similarly to the above two parts. It extracts the text after
            'Allergies' then updates the MedicalRecord object.*/
            else if (line.equals("---")) {
                addMedicalRecord(history, currentRecord);
                currentRecord = null;
            }
            /*This part includes a text (---) that signals the end of a record.
            After this text is recognised, addMedicalRecord() adding to the history list.*/
        }
        return history;
        /* A patient's history contains his past prescriptions, allergies,
        and medical conditions.*/
    };
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
    
    public void addMedicalRecord(List<MedicalRecord> history, MedicalRecord record) {
        if (record != null && record.getRecordNumber() > 0) {
            history.add(record);
        }
        else {
            System.err.println("Skipping invalid record for patient: " + record.getPatientEmail());
        }
        /*This function is used to add a medical record into the patient's
        medical history.*/
    };
    
    public void consultPharmacy(Pharmacist pharmacist, String query) {
        System.out.println("What do you want to know about?");
        query = input.nextLine();
        System.out.println("Thank you for reaching out!\nA pharmacist will respond to you shortly.");
        /*A doctor consults the pharmacy about certain things which include whether a medicine is
        in stock or not, medicine substitute (in case of unavailabilty or the patient cannot afford
        original prescription), safety checks about medications (in case patient is on other medications),
        and its dosage (things like if pills could be crushed for children, etc.).
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

