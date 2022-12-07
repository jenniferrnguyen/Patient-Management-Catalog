package ui;

// represents an exception that is thrown when the user tries to add patients with the same health number
public class PatientAlreadyInCatalogException extends Exception {

    // EFFECTS: creates PatientAlreadyInCatalogException
    public PatientAlreadyInCatalogException() {
        super();
    }
}
