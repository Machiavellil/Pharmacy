# Pharmacy Management System

## Overview
The **Pharmacy Management System** is a comprehensive software application designed to streamline pharmacy operations, including inventory management, prescription handling, order processing, and user authentication. The system incorporates various user roles such as **Admin, Customer, Delivery Agent, Doctor, and Pharmacist**, each with specific functionalities to ensure efficient workflow and customer satisfaction.

## Features
### 1. **User Management**
- Admin can add, update, delete, and view users.
- Secure authentication and authorization for different user roles.
- Password reset and profile update functionalities.

### 2. **Medicine & Inventory Management**
- Pharmacists can add, update, remove, and search for medicines.
- Stock quantity tracking and automatic updates.
- Prescription-based medicine validation.

### 3. **Order & Cart Management**
- Customers can browse medicines, add items to a cart, and place orders.
- Prescription verification for restricted medicines.
- Order history tracking and cancellation options.
- Order status updates and delivery tracking by agents.

### 4. **Prescription Management**
- Doctors can create and update prescriptions for patients.
- Customers can view and manage their prescriptions.
- Prescription validation against orders.

### 5. **Delivery Management**
- Delivery agents can track assigned deliveries.
- Order status updates and estimated delivery times.
- Integration with customer order management.

### 6. **Authentication & Security**
- Secure login with password hashing.
- User session management and logout features.
- Role-based access control to restrict unauthorized actions.

### 7. **Additional Functionalities**
- CLI-based user interaction with structured menus.
- Sound management for user-friendly notifications.

## Technology Stack
- **Programming Language**: Java
- **User Interface**: CLI (Command Line Interface)
- **Data Persistence**: File-based storage (with potential for database integration)
- **Security**: Password hashing and role-based authentication
- **Tools**: NetBeans, Java Swing (for GUI components if required)

## System Architecture
The system follows an **Object-Oriented Programming (OOP)** approach, ensuring modularity and maintainability. The key classes include:
- **User (Base Class)**: Parent class for Admin, Customer, Delivery Agent, Doctor, and Pharmacist.
- **Medicine & Inventory Management**: Medicine, MedicineHandler.
- **Order Processing**: Cart, Order, OrderItem.
- **Prescription Management**: Prescription, PrescriptionManager, MedicalRecord.
- **Authentication**: AuthService, PasswordReset.
- **System Utilities**: MenuManager, SoundManager.

## Setup & Installation
### Prerequisites
- Java Development Kit (JDK) 8 or higher
- NetBeans IDE (recommended) or any Java-supported IDE

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/Machiavellil/Pharmacy.git
   cd pharmacy-management-system
   ```
2. Open the project in NetBeans or your preferred Java IDE.
3. Compile and run the main class:
   ```sh
   javac Pharmacy.java
   java Pharmacy
   ```
4. Follow the on-screen CLI menu to interact with the system.

## Usage Guide
- **Admin**: Manage users and system settings.
- **Customer**: Browse medicines, manage prescriptions, and place orders.
- **Pharmacist**: Handle inventory and respond to doctor queries.
- **Doctor**: Create prescriptions and view patient history.
- **Delivery Agent**: Track and manage deliveries.



