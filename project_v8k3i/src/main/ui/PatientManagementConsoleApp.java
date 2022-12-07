package ui;

import model.Patient;
import model.PatientCatalog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// REFERENCE: This class uses code referenced from CPSC 210 Material: TellerApp (project example)
//            GitHub link: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
// REFERENCE: this method uses code referenced from CPSC 210 material: JsonSerializationDemo
//            GitHub link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//  Represents a Patient Health Information Management Application
public class PatientManagementConsoleApp {

    private static final String JSON_STORE = "./data/catalog.json";
    private Scanner input;
    private PatientCatalog catalog;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // code reference from CPSC 210 Material: TellerApp (project example)
    // EFFECT: runs patient management application
    public PatientManagementConsoleApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        catalog = new PatientCatalog("My patient catalog");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPatientApp();
    }

    // code referenced from CPSC 210 material: TellerApp (project example)
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runPatientApp() {
        boolean keepGoing = true;
        int command;
        input = new Scanner(System.in);

        System.out.println("Welcome to Your Patient Catalog!");
        System.out.println("\n** USER NOTE: when entering multiple words "
                + "(ex. kidney disease) use Snake case (ex. kidney_disease) **");

        while (keepGoing) {
            displayWelcomeMenu();
            command = input.nextInt();

            if (command == 0) {
                keepGoing = false;
            } else {
                processWelcomeCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user's Welcome menu command
    private void processWelcomeCommand(int command) {
        if (command == 1) {
            doAddOrRemovePatient();
        } else if (command == 2) {
            doEditOrViewPatients();
        } else if (command == 3) {
            doPatientSearch();
        } else if (command == 4) {
            savePatientCatalog();
        } else if (command == 5) {
            loadPatientCatalog();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays welcome page menu of options to user
    private void displayWelcomeMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("Select from:");
        System.out.println("\t1 -> Add or Remove Patients");
        System.out.println("\t2 -> View and/or Edit Your Patient Catalog");
        System.out.println("\t3 -> Search For Patient Information");
        System.out.println("\t4 -> Save Patient Catalog to File");
        System.out.println("\t5 -> Load Patient Catalog from File");
        System.out.println("\t0 -> Quit Session");
    }

    // MODIFIES: this
    // EFFECTS: Processes user input to either add or remove a patient
    private void doAddOrRemovePatient() {
        boolean keepGoing = true;
        int command;

        while (keepGoing) {
            displayAddRemove();
            command = input.nextInt();

            if (command == 10) {
                keepGoing = false;
            } else if (command == 11) {
                doAddPatient();
            } else if (command == 12) {
                doRemovePatient();
            } else {
                System.out.println("\nSelection not valid...");
            }
        }
    }

    // EFFECTS: displays the ADD OR REMOVE PATIENT menu
    private void displayAddRemove() {
        System.out.println("\nADD OR REMOVE PATIENTS");
        System.out.println("Select:");
        System.out.println("\t11 -> To Add A Patient To Your Catalog");
        System.out.println("\t12 -> To Remove A Patient From Your Catalog");
        System.out.println("\t10 -> To Go Back To the MainConsoleApplication Menu");
    }

    // MODIFIES: this
    // EFFECTS: adds a patient to the catalog if there is no other patient with the same health number already
    //          If there already exists a patient with entered helthNum print the error message
    private void doAddPatient() {
        System.out.println("\nEnter the new patient's first name:");
        String firstName = input.next();
        System.out.println("\nEnter the patient's last name:");
        String name = firstName + " " + input.next();
        System.out.println("\nEnter the patient's Health Number:");
        int healthNum = input.nextInt();

        if (catalog.containsPatient(healthNum)) {
            System.out.println("\nERROR - A patient with the Health Number: " + healthNum
                    + " is already a patient in your catalog...");
        } else {
            System.out.println("\nEnter the patient's age:");
            int age = input.nextInt();
            System.out.println("\nEnter the patient's biological sex (M or F):");
            String sex = input.next();
            catalog.addPatient(name, healthNum, age, sex);
            System.out.println("\n" + name + ": " + healthNum + " has been added to your patient catalog!");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a patient from the patient if there is patient with entered healthNum in the catalog
    //          prints error message is there is no patient corresponding to entered healthNum
    private void doRemovePatient() {
        System.out.println("Enter the Health Number of the patient you wish to remove:");
        int healthNum = input.nextInt();

        if (catalog.containsPatient(healthNum)) {
            Patient removedPatient = catalog.searchPatient(healthNum);
            String patientNameAndNum = removedPatient.getNameAndHealthNum();
            catalog.removePatient(healthNum);
            System.out.println("\n" + patientNameAndNum + " has been removed from your patient catalog!");
        } else {
            System.out.println("\nERROR - There is no patient with the Health Number: "
                    + healthNum + " in your patient catalog...");
        }
    }

    // MODIFIES: this
    // EFFECTS: prints a list of all patients in catalog and their health number
    //          processes user input for Edit or View Patients Menu
    private void doEditOrViewPatients() {
        boolean keepGoing = true;
        int command;
        if (catalog.getPatients().size() == 0) {
            System.out.println("\nYou have no patients in your catalog yet!");
            keepGoing = false;
        } else {
            printPatients();
        }

        while (keepGoing) {
            displayEditOrViewMenu();
            command = input.nextInt();

            if (command == 20) {
                keepGoing = false;
            } else if (command == 21) {
                doEditPatient();
            } else if (command == 22) {
                doViewPatientS();
            } else {
                System.out.println("\nSelection not valid...");
            }
        }
    }

   // EFFECTS: prints a numbered list ofa all patients in the catalog and their health numbers
    private void printPatients() {
        int i = 1;
        System.out.println("\nYour Patient Catalog:");
        for (Patient p : catalog.getPatients()) {
            System.out.println(i + ". " + p.getNameAndHealthNum());
            i++;
        }
    }

    // EFFECTS: Displays the selections for the EDIT OR VIEW PATIENTS menu
    private void displayEditOrViewMenu() {
        System.out.println("\nEDIT OR VIEW PATIENTS");
        System.out.println("Select:");
        System.out.println("\t21 -> To Edit A Patient's Health Information");
        System.out.println("\t22 -> To View A Patient's Health Information");
        System.out.println("\t20 -> To Go Back To The MainConsoleApplication Menu");
    }

    // MODIFIES: this
    // EFFECTS: processes user input for the EDIT A PATIENT'S HEALTH INFORMATION menu
    private void doEditPatient() {
        boolean keepGoing = true;
        int command;

        while (keepGoing) {
            System.out.println("\nEDIT A PATIENT'S HEALTH INFORMATION");
            System.out.println("Enter the patients Health Number:");
            int healthNum = input.nextInt();
            if (!catalog.containsPatient(healthNum)) {
                System.out.println("\nERROR - There is no patient with this health number in your catalog...");
                keepGoing = false;
            } else {
                displayEditOptions(healthNum);
                command = input.nextInt();
                if (command == 210) {
                    keepGoing = false;
                } else {
                    processEditCommand(command, healthNum);
                    keepGoing = false;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes users EDIT A PATIENT"S HEALTH INFORMATION command
    private void processEditCommand(int command, int healthNum) {
        if (command == 211) {
            doAddAllergy(healthNum);
        } else if (command == 212) {
            doRemoveAllergy(healthNum);
        } else if (command == 213) {
            doAddCondition(healthNum);
        } else if (command == 214) {
            doRemoveCondition(healthNum);
        } else if (command == 215) {
            doUpdateAge(healthNum);
        } else if (command == 216) {
            doCovidStatus(healthNum);
        } else {
            System.out.println("\nSelection not valid...");
        }
    }

    // EFFECTS: displays the EDIT PATIENT selection options
    private void displayEditOptions(int healthNum) {
        Patient p = catalog.searchPatient(healthNum);
        System.out.println("\nEDIT " + p.getNameAndHealthNum());
        System.out.println("Select:");
        System.out.println("\t211 -> Add an allergy");
        System.out.println("\t212 -> Remove an allergy");
        System.out.println("\t213 -> Add a chronic health condition");
        System.out.println("\t214 -> Remove a chronic health condition");
        System.out.println("\t215 -> Update age");
        System.out.println("\t216 -> Enter COVID vaccination status");
        System.out.println("\n\t210 -> Go back to EDIT OR VEW PATIENTS MENU");
    }

    // MODIFIES: this
    // EFFECTS: adds given an allergy to the given patient Health profile
    private void doAddAllergy(int healthNum) {
        Patient p = catalog.searchPatient(healthNum);
        System.out.println("\nEnter allergy:");
        String allergy = input.next();
        p.addAllergy(allergy);
        System.out.println("\n" + allergy + " has been added to " + p.getName() + "'s Health Information!");
    }

    // MODIFIES: this
    // EFFECTS: if entered allergy is the the given patient Health profile, remove it
    //          otherwise print Error message
    private void doRemoveAllergy(int healthNum) {
        Patient p = catalog.searchPatient(healthNum);
        System.out.println("Enter allergy:");
        String allergy = input.next();
        if (p.getAllergies().contains(allergy)) {
            p.removeAllergy(allergy);
            System.out.println("\nAllergy: " + allergy + " has been removed from "
                    + p.getName() + "'s Health Information!");
        } else {
            System.out.println("\nERROR - Cannot remove an allergy that is not in "
                    + p.getName() + "'s Health Info...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds entered condition to given patient health profile
    private void doAddCondition(int healthNum) {
        Patient p = catalog.searchPatient(healthNum);
        System.out.println("\nEnter chronic health condition:");
        String condition = input.next();
        p.addCondition(condition);
        System.out.println("\nCondition: " + condition + " has been added to "
                + p.getName() + "'s Health Information!");

    }

    // MODIFIES: this
    // EFFECTS: if entered condition is in the given patient's health profile, remove it
    //          otherwise print the error message
    private void doRemoveCondition(int healthNum) {
        Patient p = catalog.searchPatient(healthNum);
        System.out.println("\nEnter condition:");
        String condition = input.next();
        if (p.getConditions().contains(condition)) {
            p.removeCondition(condition);
            System.out.println("\n" + condition + " has been removed from " + p.getName() + "'s Health Information!");
        } else {
            System.out.println("\nERROR - Cannot remove a condition that is not in "
                    + p.getName() + "'s Health Information...");
        }
    }

    // MODIFIES: this
    // EFFECTS: updates given patient's age
    private void doUpdateAge(int healthNum) {
        Patient p = catalog.searchPatient(healthNum);
        System.out.println("\nEnter age:");
        int age = input.nextInt();
        p.updateAge(age);
        System.out.println("\n" + p.getName() + "'s age has been updated to " + age);
    }

    // MODIFIES: this
    // EFFECTS: set COVID vaccination states to entered boolean (i.e., true or false)
    private void doCovidStatus(int healthNum) {
        Patient p = catalog.searchPatient(healthNum);
        System.out.println("\nIs " + p.getNameAndHealthNum() + " COVID vaccinated? Enter true or false:");
        boolean status = input.nextBoolean();
        p.vaxxStatus(status);
        System.out.println("\n" + p.getNameAndHealthNum() + "'s COVID vaccination status: " + status);
    }

    // EFFECTS: prints the full overview of the entered patient's health information
    //          (i.e., name, health number, age, sex, vaxx status, allergies, conditions, PRI)
    private void doViewPatientS() {
        System.out.println("\nVIEW A PATIENT'S HEALTH INFORMATION");
        System.out.println("Enter the patient's Health Number:");
        int healthNum = input.nextInt();
        if (catalog.containsPatient(healthNum)) {
            Patient p = catalog.searchPatient(healthNum);
            System.out.println("\n" + p.getNameAndHealthNum());
            System.out.println("AGE: " + p.getAge());
            System.out.println("SEX: " + p.getSex());
            System.out.print("\nCOVID Vaccinated: " + p.getVaxxStatus());
            printAllergies(p);
            printConditions(p);
            System.out.println("\nPATIENT RISK INDEX (PRI): " + p.calculatePRI());
        } else {
            System.out.println("\nERROR - There is no patient with the Health Number: "
                    + healthNum + " in your catalog...");
        }
    }

    // EFFECTS: prints a list of the given patient's allergies
    private void printAllergies(Patient patient) {
        int i = 1;
        if (patient.getAllergies().size() == 0) {
            System.out.print("\nALLERGIES: No allergies recorded");
        } else {
            for (String a : patient.getAllergies()) {
                if (i == 1) {
                    System.out.print("\nALLERGIES: " + a);
                    i++;
                } else {
                    System.out.print(", " + a);
                }
            }
        }
    }

    // EFFECTS: prints a list of the given patient's chronic health conditions
    private void printConditions(Patient patient) {
        int i = 1;
        if (patient.getConditions().size() == 0) {
            System.out.print("\nCHRONIC HEALTH CONDITIONS: No conditions recorded");
        } else {
            for (String a : patient.getConditions()) {
                if (i == 1) {
                    System.out.print("\nCHRONIC HEALTH CONDITIONS: " + a);
                    i++;
                } else {
                    System.out.print(", " + a);
                }
            }
        }
    }

    // EFFECTS: processes the user input for the PATIENT SEARCH menu
    private void doPatientSearch() {
        boolean keepGoing = true;
        int command;

        while (keepGoing) {
            displaySearchMenu();
            command = input.nextInt();
            if (command == 30) {
                keepGoing = false;
            } else {
                processSearchCommand(command);
            }
        }
    }

    // EFFECTS: displays the selection options for the PATIENT SEARCH menu
    private void displaySearchMenu() {
        System.out.println("\nSEARCH MENU");
        System.out.println("Select:");
        System.out.println("\t31 -> Search for all patients with a given allergy");
        System.out.println("\t32 -> Search for all patients with a given chronic health condition");
        System.out.println("\t30 -> Go back to MainConsoleApplication Menu");
    }

    // EFFECTS: processes the user's PATIENT SEARCH command
    private void processSearchCommand(int command) {
        if (command == 31) {
            doAllergySearch();
        } else if (command == 32) {
            doConditionSearch();
        } else {
            System.out.println("\nERROR - Selection not valid...");
        }
    }

    // EFFECTS: if there are no patients with the entered allergy print:
    //          - "There are 0 patients in your catalog with this allergy"
    //          otherwise print a list of patients with entered allergy
    private void doAllergySearch() {
        System.out.println("\nEnter allergy:");
        String allergy = input.next();
        int i = 1;
        List<String> allergySearch = catalog.patientsWithAllergy(allergy);
        if (allergySearch.size() == 0) {
            System.out.println("There are 0 patients in your catalog with this allergy");
        } else {
            System.out.println("\nPatient(s) with allergy: " + allergy);
            for (String s: allergySearch) {
                System.out.println(i + ". " + s);
                i++;
            }
        }
    }

    // EFFECTS: if there are no patients with the entered condition print:
    //          - "There are 0 patients in your catalog with this chronic health condition"
    //          otherwise print a list of patient(s) with entered condition
    private void doConditionSearch() {
        System.out.println("\nEnter a chronic health condition:");
        String condition = input.next();
        int i = 1;
        List<String> conditionSearch = catalog.patientsWithCondition(condition);
        if (conditionSearch.size() == 0) {
            System.out.println("There are 0 patients in your catalog with this condition");
        } else {
            System.out.println("\nPatient(s) with condition: " + condition);
            for (String s: conditionSearch) {
                System.out.println(i + ". " + s);
                i++;
            }
        }
    }

    // EFFECTS: saves patient catalog to file
    private void savePatientCatalog() {
        try {
            jsonWriter.open();
            jsonWriter.write(catalog);
            jsonWriter.close();
            System.out.println("Saved "  + catalog.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable o write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: load a patient catalog from file
    private void loadPatientCatalog() {
        try {
            catalog = jsonReader.read();
            System.out.println("Loaded " + catalog.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
