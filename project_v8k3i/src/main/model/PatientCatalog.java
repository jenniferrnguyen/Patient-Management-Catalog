package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a PatientCatalog with a list of Patients
public class PatientCatalog implements Writable {

    private List<Patient> patientCatalog;
    private String name;

    // EFFECT: Constructs an empty Patient Catalog
    public PatientCatalog(String name) {
        this.name = name;
        patientCatalog = new ArrayList<>();
    }

    // REQUIRES: name is non-zero length, sex is "M" or "F" (not gender identity)
    // MODIFIES: this, Patient
    // EFFECTS: adds a patient to the patient catalog
    public Patient addPatient(String name, int healthNumber, int age, String sex) {
        Patient patient = new Patient(name, healthNumber, age, sex);
        patientCatalog.add(patient);
        EventLog.getInstance().logEvent(new Event(patient.getNameAndHealthNum()
                + " ---> ADDED to Patient Catalog"));
        return patient;
    }

    // REQUIRES: healthNum corresponds to a Patient who is in PatientCatalog
    // MODIFIES: this
    // EFFECTS: removes Patient from PatientCatalog
    public void removePatient(int healthNum) {
        for (int i = 0; i < patientCatalog.size(); i++) {
            Patient p = patientCatalog.get(i);
            if (p.getHealthNumber() == healthNum) {
                i = patientCatalog.size();
                patientCatalog.remove(p);
                EventLog.getInstance().logEvent(new Event(p.getNameAndHealthNum()
                        + " ---> REMOVED from Patient Catalog"));
            }
        }
    }

    // EFFECT: Check if the catalog contains a Patient with the given health number
    //         - returns true is the catalog contains given healthNum, false if not
    public boolean containsPatient(int healthNum) {
        for (Patient p: patientCatalog) {
            if (p.getHealthNumber() == healthNum) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: produces a list of all the patient's name and age from PatientCatalog
    public List<String> listOfNameAndHealthNum() {
        List<String> namesAndHealthNums = new ArrayList<>();
        for (Patient p: patientCatalog) {
            namesAndHealthNums.add(p.getNameAndHealthNum());
        }
        return namesAndHealthNums;
    }

    // REQUIRES: healthNum is the Health Number of a Patient who is in PatientCatalog
    // EFFECTS: gets the given patients Health information
    public Patient searchPatient(int healthNum) {
        Patient patient = patientCatalog.get(0);
        for (int i = 0; i < patientCatalog.size(); i++) {
            Patient p = patientCatalog.get(i);
            if (p.getHealthNumber() == healthNum) {
                patient = p;
                i = patientCatalog.size();
            }
        }
        return patient;
    }

    // REQUIRES: allergy is a non-zero length string
    // EFFECT: Produces a list of the names for Patients with the given allergy
    public List<String> patientsWithAllergy(String allergy) {
        List<String> patientsWithAllergy = new ArrayList<>();
        for (Patient p : patientCatalog) {
            List<String> patientAllergies = p.getAllergies();
            if (patientAllergies.contains(allergy)) {
                patientsWithAllergy.add(p.getNameAndHealthNum());
            }
        }
        return patientsWithAllergy;
    }

    // REQUIRES: condition is a non-zero-length string
    // EFFECTS: Produces a list of name for Patients with the given condition
    public List<String> patientsWithCondition(String condition) {
        List<String> patientsWithCondition = new ArrayList<>();
        for (Patient p: patientCatalog) {
            List<String> patientConditions = p.getConditions();
            if (patientConditions.contains(condition)) {
                patientsWithCondition.add(p.getNameAndHealthNum());
            }
        }
        return patientsWithCondition;
    }

    // EFFECTS: checks

    // REFERENCE: this method uses code referenced from CPSC 210 material: JsonSerializationDemo
    //            GitHub link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    // MODIFIES: JSONObject
    // EFFECTS: converts patientCatalog to JSON object and returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("patients", patientsToJson());
        return json;
    }

    // REFERENCE: this method uses code referenced from CPSC 210 material: JsonSerializationDemo
    //            GitHub link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns patients in this patient catalog as JSON array
    private JSONArray patientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Patient p : patientCatalog) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // GETTERS -------------------------------------------------------------------------------------------------

    public List<Patient> getPatients() {
        return patientCatalog;
    }

    public String getName() {
        return this.name;
    }

}
