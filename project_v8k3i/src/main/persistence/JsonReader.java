package persistence;

import model.Patient;
import model.PatientCatalog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a reader that reads a patient catalog from JSON data stored in file
// REFERENCE: this class uses code referenced from CPSC 210 material: JsonSerializationDemo
//            GitHub link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads patient catalog from files and returns it
    //          throws IOException if an error occurs reading data from file
    public PatientCatalog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePatientCatalog(jsonObject);
    }

    // EFFECTS: reads source file as string and tetuens it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();

    }

    // EFFECTS: parse patient catalog from JSON object and returns it
    private PatientCatalog parsePatientCatalog(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        PatientCatalog pc = new PatientCatalog(name);
        addPatients(pc, jsonObject);
        return pc;
    }

    // MODIFIES: pc
    // EFFECTS: parses patients from JSON object and adds them to patient catalog
    private void addPatients(PatientCatalog pc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("patients");
        for (Object json : jsonArray) {
            JSONObject nextPatient = (JSONObject) json;
            addPatient(pc, nextPatient);
        }
    }

    // MODIFIES: pc
    // EFFECTS: parses patient from JSON object and adds it to patient catalog
    private void addPatient(PatientCatalog pc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int healthNumber = jsonObject.getInt("health number");
        int age = jsonObject.getInt("age");
        String sex = jsonObject.getString("sex");
        boolean vaxxStatus = jsonObject.getBoolean("vaxx Status");
        JSONArray allergies = jsonObject.getJSONArray("allergies"); // !!!
        JSONArray conditions = jsonObject.getJSONArray("conditions"); // !!!
        Patient patient = pc.addPatient(name, healthNumber, age, sex);
        patient.vaxxStatus(vaxxStatus);
        addAllergies(patient, allergies);
        addConditions(patient, conditions);
    }

    // MODIFIES: patient
    // EFFECTS: parses allergies from JSON array and adds it to the patient's list of allergies
    private void addAllergies(Patient p, JSONArray allergies) {
        for (int i = 0; i < allergies.length(); i++) {
            String nextAllergy = allergies.getString(i);
            p.addAllergy(nextAllergy);
        }
    }

    // MODIFIES: patient
    // EFFECTS: parses conditions from JSON array and adds it to the patient's list of conditions
    private void addConditions(Patient p, JSONArray conditions) {
        for (int i = 0; i < conditions.length(); i++) {
            String nextCondition = conditions.getString(i);
            p.addCondition(nextCondition);
        }
    }

}