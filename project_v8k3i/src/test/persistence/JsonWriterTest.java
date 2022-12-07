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
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            PatientCatalog pc = new PatientCatalog("My patient catalop");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyPatientCatalog() {
        try {
            PatientCatalog pc = new PatientCatalog("My patient catalog");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPatientCatalog.json");
            writer.open();
            writer.write(pc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPatientCatalog.json");
            pc = reader.read();
            assertEquals("My patient catalog", pc.getName());
            assertEquals(0, pc.getPatients().size());
        } catch (IOException e) {
            fail("not expecting exceptions to be thrown");
        }
    }

    @Test
    void testWriterGeneralWorkRoom() {
        try {
            PatientCatalog pc = new PatientCatalog("My patient catalog");
            pc.addPatient("Jennifer Nguyen", 1234, 19, "F");
            pc.addPatient("Annabell Nguyen", 1122, 12, "F");
            List<Patient> patients = pc.getPatients();
            patients.get(0).addCondition("eczema");
            patients.get(0).addCondition("scoliosis");
            patients.get(1).addAllergy("nuts");
            patients.get(1).addAllergy("pollen");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPatientCatalog.json");
            writer.open();
            writer.write(pc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPatientCatalog.json");
            pc = reader.read();
            assertEquals("My patient catalog", pc.getName());
            List<String> conditions = new ArrayList<>();
            conditions.add("eczema");
            conditions.add("scoliosis");
            List<String> allergies = new ArrayList<>();
            allergies.add("nuts");
            allergies.add("pollen");
            assertEquals(2, patients.size());
            checkPatient("Jennifer Nguyen", 1234, 19, "F", false, patients.get(0));
            checkPatient("Annabell Nguyen", 1122, 12, "F", false, patients.get(1));
            assertEquals(conditions, patients.get(0).getConditions());
            assertEquals(allergies, patients.get(1).getAllergies());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
