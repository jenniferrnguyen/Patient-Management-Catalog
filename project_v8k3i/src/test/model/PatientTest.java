package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Patient.*;
import static org.junit.jupiter.api.Assertions.*;

// PatientTest class holds all the test for the methods in the Patient Class 
class PatientTest {

    private Patient testPatient;

    @BeforeEach
    void runBefore() {
        testPatient = new Patient("Jenn Nguyen", 1234, 19, "F");
    }

    @Test
    void testConstructor() {
        List<String> allergies =  testPatient.getAllergies();
        List<String> conditions = testPatient.getConditions();

        assertEquals("Jenn Nguyen", testPatient.getName());
        assertEquals(1234, testPatient.getHealthNumber());
        assertEquals("Jenn Nguyen: 1234", testPatient.getNameAndHealthNum());
        assertEquals(19, testPatient.getAge());
        assertEquals("F", testPatient.getSex());
        assertEquals(0, allergies.size());
        assertEquals(0, conditions.size());
    }

    @Test
    void testVaxxStatus() {
        testPatient.vaxxStatus(true);
        assertTrue(testPatient.getVaxxStatus());

        testPatient.vaxxStatus(false);
        assertFalse(testPatient.getVaxxStatus());
    }

    @Test
    void testAddAllergy() {
        List<String> allergies;

        testPatient.addAllergy("nuts");
        allergies = testPatient.getAllergies();
        assertEquals(1, allergies.size());

        testPatient.addAllergy("shell fish");
        allergies = testPatient.getAllergies();
        assertEquals(2, allergies.size());
    }

    @Test
    void testRemoveAllergy() {
        List<String> allergies;
        testPatient.addAllergy("nuts");
        testPatient.addAllergy("shell fish");

        testPatient.removeAllergy("shell fish");
        allergies = testPatient.getAllergies();
        assertEquals(1, allergies.size());
        assertFalse(allergies.contains("shell fish"));

        testPatient.removeAllergy("nuts");
        allergies = testPatient.getAllergies();
        assertEquals(0, allergies.size());
    }



    @Test
    void testAddCondition() {
        List<String> conditions;

        testPatient.addCondition("scoliosis");
        conditions = testPatient.getConditions();
        assertEquals(1, conditions.size());

        testPatient.addCondition("diabetes");
        conditions = testPatient.getConditions();
        assertEquals(2, conditions.size());
    }

    @Test
    void testRemoveCondition() {
        List<String> conditions;

        testPatient.addCondition("scoliosis");
        testPatient.addCondition("diabetes");

        testPatient.removeCondition("diabetes");
        conditions = testPatient.getConditions();
        assertEquals(1, conditions.size());
        assertFalse(conditions.contains("diabetes"));

        testPatient.removeCondition("scoliosis");
        conditions = testPatient.getConditions();
        assertEquals(0, conditions.size());
    }

    @Test
    void testCalculatePRI() {
        testPatient.addCondition("scoliosis");
        testPatient.addCondition("diabetes");
        testPatient.vaxxStatus(false);
        testPatient.addAllergy("nuts");

        int allergyPRI = PRI_ALLERGY_WEIGHT * testPatient.getAllergies().size();
        int conditionPRI = PRI_CONDITION_WEIGHT * testPatient.getConditions().size();
        int vaxxPRI = PRI_FALSE_VAXXSTATUS;
        assertEquals((allergyPRI + conditionPRI + vaxxPRI) / 3.0, testPatient.calculatePRI());

        testPatient.addAllergy("pollen");
        testPatient.addCondition("asthma");
        testPatient.vaxxStatus(true);
        allergyPRI = PRI_ALLERGY_WEIGHT * testPatient.getAllergies().size();
        conditionPRI = PRI_CONDITION_WEIGHT * testPatient.getConditions().size();
        vaxxPRI = PRI_TRUE_VAXXSTATUS;
        assertEquals((allergyPRI + conditionPRI + vaxxPRI) / 3.0, testPatient.calculatePRI());
    }

    @Test
    void testUpdateAge() {
        testPatient.updateAge(20);
        assertEquals(20, testPatient.getAge());
    }
}