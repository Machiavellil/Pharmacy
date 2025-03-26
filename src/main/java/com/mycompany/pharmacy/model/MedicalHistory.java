/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacy.model;

import com.mycompany.pharmacy.model.User;
import com.mycompany.pharmacy.model.Prescription;
import java.util.*;
import java.io.*;
import java.nio.file.*;
/**
 *
 * @author young
 */
public class MedicalHistory {
    private static final String medicalConditions = "Medical Conditions.txt";
    private static List<String> mConditions = new ArrayList<>();
    private String patientEmail;
    private ArrayList<Prescription> prescription = new ArrayList<>();
    private String allergies = " ";
    private List<String> patientConditions = new ArrayList<>();
    static {
        try {
            mConditions = Files.readAllLines(Paths.get(medicalConditions));
        }
        catch (IOException e) {
            System.err.println("Error loading conditions: " + e.getMessage());
        }
    }
    public MedicalHistory(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}
