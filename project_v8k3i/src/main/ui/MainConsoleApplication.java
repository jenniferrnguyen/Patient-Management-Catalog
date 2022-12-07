package ui;

import java.io.FileNotFoundException;

// REFERENCE: this class uses code referenced from CPSC 210 material: JsonSerializationDemo
//            GitHub link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// class runs the console based PatientManagementConsoleApp
public class MainConsoleApplication {
    public static void main(String[] args) {
        try {
            new PatientManagementConsoleApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

    }
}