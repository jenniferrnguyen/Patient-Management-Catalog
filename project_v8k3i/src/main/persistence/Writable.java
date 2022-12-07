package persistence;

import org.json.JSONObject;

// REFERENCE: this class uses code referenced from CPSC 210 material: JsonSerializationDemo
//            GitHub link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();

}
