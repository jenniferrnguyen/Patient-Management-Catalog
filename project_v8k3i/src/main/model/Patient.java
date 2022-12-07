package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a Patient with a name, age, sex initially  (i.e., information from a health card)
// additional health information can also be added such as COVID vaccination status,
// list of allergies, and list of chronic health conditions
public class Patient implements Writable {

    public static final int PRI_ALLERGY_WEIGHT = 1;     // each allergy is worth 1 point in PRI calculation
    public static final int PRI_CONDITION_WEIGHT = 3;   // each chronic condition is worth 3 points
    public static final int PRI_TRUE_VAXXSTATUS = 1;    // if Patient is COVID vaccinated: vaxxStatus weight is 1
    public static final int PRI_FALSE_VAXXSTATUS = 3;   // if Patient is not vaccinate: vaxxStatus weight is 3

    private String name;
    private int healthNumber;
    private int age;
    private String sex; // either "M" or "F" - not gender identity
    private boolean vaxxStatus;
    private List<String> allergies;
    private List<String> conditions;

    // REQUIRES: Name is non-zero length and
    //           biological sex is either "M" or "F" (** not gender identity)
    // EFFECTS: Creates a patient with given name, age, sex,
    //          not covid vaccinated, empty list of allergies
    //          and empty list of chronic health conditions
    public Patient(String name, int healthNumber, int age, String sex) {
        this.name = name;
        this.healthNumber = healthNumber;
        this.age = age;
        this.sex = sex;
        this.vaxxStatus = false;
        this.allergies = new ArrayList<>();
        this.conditions = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: records patient's covid vaccination status as a boolean
    public void vaxxStatus(boolean status) {
        this.vaxxStatus = status;
    }

    // REQUIRES: allergy is non-zero length
    // MODIFIES: this
    // EFFECTS: adds given allergy to patient's list of allergies if
    //          it is not there, does nothing is allergy is already in list
    public void addAllergy(String allergy) {
        allergies.add(allergy);
    }

    // REQUIRES: allergy is in patient's list of allergies and non-zero length
    // MODIFIES: this
    // EFFECTS: removes given allergy from patient's list of allergies
    public void removeAllergy(String allergy) {
        for (int i = 0; i < allergies.size(); i++) {
            if (allergies.get(i).equals(allergy)) {
                allergies.remove(i);
                i = allergies.size() + 2;
            }
        }
    }

    // REQUIRES: condition is non-zero length
    // MODIFIES: this
    // EFFECTS: adds given allergy to patient's list of chronic health conditions
    //          if it is not already in the list, does nothing if it is
    public void addCondition(String condition) {
        conditions.add(condition);
    }

    // REQUIRES: given condition is in patient's list of chronic health conditions
    // MODIFIES: this
    // EFFECTS: removed given condition from the patient's list of chronic
    //          health conditions
    public void removeCondition(String condition) {
        for (int i = 0; i < conditions.size(); i++) {
            if (conditions.get(i).equals(condition)) {
                conditions.remove(i);
                i = conditions.size() + 2;
            }
        }
    }

    // EFFECTS: calculates the Patient Risk Index (PRI) by taking the weighted
    //          average of their allergies, vaccination status, and conditions
    //          [1(# allergies) + (3 is not vaccinated 1 is vaccinated) + 3(# conditions)]/3
    public double calculatePRI() {
        int vaxxWeighted;

        int allergiesWeighted = allergies.size() * PRI_ALLERGY_WEIGHT;
        int conditionsWeighted = conditions.size() * PRI_CONDITION_WEIGHT;

        if (vaxxStatus) {
            vaxxWeighted = PRI_TRUE_VAXXSTATUS;
        } else {
            vaxxWeighted = PRI_FALSE_VAXXSTATUS;
        }

        int numerator = vaxxWeighted + allergiesWeighted + conditionsWeighted;

        return numerator / 3.0;

    }

    // REQUIRES: age > 0
    // MODIFIES: this
    // EFFECTS: update patient's age
    public void updateAge(int age) {
        this.age = age;
    }

    // REFERENCE: this method uses code referenced from CPSC 210 material: JsonSerializationDemo
    //            GitHub link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    // MODIFIES: JSON object
    // EFFECTS: Converts patient to JSON object and returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("health number", healthNumber);
        json.put("age", age);
        json.put("sex", sex);
        json.put("vaxx Status", vaxxStatus);
        json.put("allergies", allergies);
        json.put("conditions", conditions);
        return json;
    }

    // GETTERS ----------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public int getHealthNumber() {
        return healthNumber;
    }

    public String getNameAndHealthNum() {
        return (name + ": " + healthNumber);
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public boolean getVaxxStatus() {
        return vaxxStatus;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public List<String> getConditions() {
        return conditions;
    }

}
