package persistence;

import model.Patient;
import model.PatientCatalog;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// REFERENCE: this class uses code referenced from CPSC 210 material: JsonSerializationDemo
//            GitHub link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PatientCatalog pc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // correct! pass!
        }
    }

    @Test
    void testReaderEmptyPatientCatalog() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPatientCatalog.json");
        try {
            PatientCatalog pc = reader.read();
            assertEquals("My patient catalog", pc.getName());
            assertEquals(0, pc.getPatients().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCatalog() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCatalog.json");
        try {
            PatientCatalog pc = reader.read();
            assertEquals("My patient catalog", pc.getName());
            List<Patient> patients = pc.getPatients();
            assertEquals(2, patients.size());
            checkPatient("Jennifer Nguyen", 1234, 19, "F", true, patients.get(0));
            checkPatient("Xavier Kuehn", 4321, 19, "M", true, patients.get(1));
            List<String> conditions = new ArrayList<>();
            conditions.add("arthritis");
            conditions.add("eczema");
            assertEquals(conditions, patients.get(1).getConditions());
            List<String> allergies = new ArrayList<>();
            allergies.add("grass");
            allergies.add("pollen");
            assertEquals(allergies, patients.get(0).getAllergies());
            allergies = new ArrayList<>();
            allergies.add("nuts");
            assertEquals(allergies, patients.get(1).getAllergies());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
