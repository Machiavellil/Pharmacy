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
    private ArrayList<Prescription> prescription = new ArrayList<>();
    private String allergies = " ";
    private static final String medicalConditions = "Medical Conditions.txt";
    private List<String> mConditions = new ArrayList<>();
    private String patientEmail;
    private void loadConditions() {
        try {
            if (Files.exists(Paths.get(medicalConditions))) {
                mConditions = Files.readAllLines(Paths.get(medicalConditions)).stream().filter(line -> line.startsWith(patientEmail + "|")).map(line -> line.split("\\|")[1]).toList();
            }
        }
        catch (IOException e) {
            System.err.println("Error loading conditions: " + e.getMessage());
        }
    };
}
