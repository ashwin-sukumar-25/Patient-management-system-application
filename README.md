# 🏥 Patient Management System Application

### A Java-based micro-project for efficient patient management with Text-to-Speech functionality

## 📜 Project Overview

This **Patient Management System** is a Java-based application designed to simplify and streamline patient management in healthcare facilities. The application allows for the efficient handling of patient records, appointments, and other crucial data, with a focus on simplicity and performance.

### Key Features:
- **Java-based Application**: Developed entirely in Java for cross-platform compatibility.
- **MySQL Server Integration**: The application connects to a MySQL server over a network for database management.
- **Text-to-Speech Conversion**: Implemented with **FreeTTS.jar** to enable text-to-speech functionality for patient details.
- **Executable File**: The project has been compiled into `.exe` for easy execution on Windows platforms.

## 📁 Project Structure

The project comprises the following main files:

1. **PatientManagementSystem.exe**: The executable version of the application for Windows users.
2. **PatientManagementSystem.jar**: The Java archive file, which can be executed on any system with Java installed.
3. **PatientManagementSystem.xml**: Configuration or data file required for application setup.

## 🛠️ Technologies Used

- **Java**: Core language used for building the application.
- **MySQL**: For database management of patient data (via a remote server).
- **FreeTTS.jar**: For text-to-speech conversion, providing audio feedback.
  
## 🚀 How to Run the Project

### Prerequisites:
- **Java Runtime Environment (JRE)**: Ensure you have JRE installed on your system.
- **Network Access to MySQL Server**: The application requires access to a MySQL server over a network.

### Steps to Run:

1. **For Windows Users**:
   - Simply double-click the `PatientManagementSystem.exe` to launch the application.

2. **For Java Users**:
   - Open a terminal or command prompt.
   - Navigate to the project directory.
   - Run the following command to start the application:
     ```bash
     java -jar PatientManagementSystem.jar
     ```

3. **Network Configuration**:
   - Ensure that your PC is connected to the network.
   - The application connects to a remote MySQL server. Verify that the server is running and accessible over the network.
   - The application will handle the connection to the database server automatically, so no additional configuration is needed.

## 🗣️ Text-to-Speech Functionality

The application uses **FreeTTS.jar** for converting patient information into speech, providing an accessible interface for users who prefer or require auditory feedback.

## 📚 College Project

This project was developed as part of our college curriculum under the **Java Micro Project** category. It demonstrates the effective use of Java, MySQL (over a network), and external libraries for real-world application development.

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
