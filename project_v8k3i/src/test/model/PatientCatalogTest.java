package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// PatientCatalogTest class contains all the tests for the methods in the PatientCatalog class
public class PatientCatalogTest {

    private PatientCatalog testCatalog;
    @BeforeEach
    void rubBefore() {
        testCatalog = new PatientCatalog("Jenn's Patient Catalog");
    }

    @Test
    void testConstructor() {
        List<Patient> patients = testCatalog.getPatients();
        assertEquals(0, patients.size());
        assertEquals("Jenn's Patient Catalog", testCatalog.getName());
    }

    @Test
    void testAddPatient() {
        testCatalog.addPatient("Jenn Nguyen", 1234, 19, "F");
        List<Patient> patients = testCatalog.getPatients();
        Patient patient = testCatalog.searchPatient(1234);
        assertEquals(1, patients.size());
        assertTrue(patients.contains(patient));

        testCatalog.addPatient("Xavier Kuehn", 4321, 19, "M");
        patients = testCatalog.getPatients();
        patient = testCatalog.searchPatient(4321);
        assertEquals(2, patients.size());
        assertTrue(patients.contains(patient));
    }

    @Test
    void testRemovePatientWithHealthNum() {
        testCatalog.addPatient("Jenn Nguyen", 1234, 19, "F");
        testCatalog.addPatient("Xavier Kuehn", 4321, 19, "M");

        Patient patient = testCatalog.searchPatient(4321);
        testCatalog.removePatient(4321);
        List<Patient> patients = testCatalog.getPatients();
        assertEquals(1, patients.size());
        assertFalse(patients.contains(patient));

        patient = testCatalog.searchPatient(1234);
        testCatalog.removePatient(1234);
        patients = testCatalog.getPatients();
        assertEquals(0, patients.size());
        assertFalse(patients.contains(patient));
    }

    @Test
    void testContainsPatient() {
        testCatalog.addPatient("Jenn Nguyen", 1234, 19, "F");
        testCatalog.addPatient("Xavier Kuehn", 4321, 19, "M");
        testCatalog.addPatient("Annabell Nguyen", 1122, 12, "F");

        assertTrue(testCatalog.containsPatient(4321));
        assertFalse(testCatalog.containsPatient(5555));
    }

    @Test
    void testPatientsNameAndHealthNum() {
        testCatalog.addPatient("Jenn Nguyen", 1234, 19, "F");
        testCatalog.addPatient("Xavier Kuehn", 4321, 19, "M");

        List<String> namesAndHealthNums = testCatalog.listOfNameAndHealthNum();
        assertEquals(2, namesAndHealthNums.size());
        assertTrue(namesAndHealthNums.contains("Jenn Nguyen: 1234"));
        assertTrue(namesAndHealthNums.contains("Xavier Kuehn: 4321"));
    }

    @Test
    void testPatientsWithAllergy() {
        testCatalog.addPatient("Jenn Nguyen", 1234, 19, "F");
        testCatalog.addPatient("Xavier Kuehn", 4321, 19, "M");
        testCatalog.addPatient("Annabell Nguyen", 1122, 12, "F");
        Patient p0 = testCatalog.searchPatient(1234);
        p0.addAllergy("nuts");
        Patient p1 = testCatalog.searchPatient(4321);
        p1.addAllergy("pollen");
        p1.addAllergy("nuts");
        Patient p2 = testCatalog.searchPatient(1122);
        p2.addAllergy("grass");

        List<String> nutAllergies = testCatalog.patientsWithAllergy("nuts");
        assertEquals(2, nutAllergies.size());
        assertTrue(nutAllergies.contains("Jenn Nguyen: 1234"));
        assertTrue(nutAllergies.contains("Xavier Kuehn: 4321"));
    }

    @Test
    void testPatientsWithCondition() {
        testCatalog.addPatient("Jenn Nguyen", 1234, 19, "F");
        testCatalog.addPatient("Xavier Kuehn", 4321, 19, "M");
        testCatalog.addPatient("Annabell Nguyen", 1122, 12, "F");
        Patient p0 = testCatalog.searchPatient(1234);
        p0.addCondition("diabetes");
        Patient p1 = testCatalog.searchPatient(4321);
        p1.addCondition("cancer");
        p1.addCondition("diabetes");
        Patient p2 = testCatalog.searchPatient(1122);
        p2.addCondition("kidney disease");

        List<String> diabetesPatients = testCatalog.patientsWithCondition("diabetes");
        assertEquals(2, diabetesPatients.size());
        assertTrue(diabetesPatients.contains("Jenn Nguyen: 1234"));
        assertTrue(diabetesPatients.contains("Xavier Kuehn: 4321"));
    }
}
