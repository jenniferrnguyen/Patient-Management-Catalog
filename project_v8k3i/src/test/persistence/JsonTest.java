package persistence;

import model.Patient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// REFERENCE: this class uses code referenced from CPSC 210 material: JsonSerializationDemo
//            GitHub link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// allows for testability of patients in file stored as Json objects
public class JsonTest {

    // EFFECTS: checks that expects patient information matches the given patient from catalog file
    protected void checkPatient(String name, int healthNum, int age, String sex, boolean vaxxStatus, Patient patient) {
        assertEquals(name, patient.getName());
        assertEquals(healthNum, patient.getHealthNumber());
        assertEquals(age, patient.getAge());
        assertEquals(sex, patient.getSex());
        assertEquals(vaxxStatus, patient.getVaxxStatus());
    }
}
