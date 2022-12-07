package ui;

import model.Event;
import model.EventLog;
import model.PatientCatalog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// NOTE: this code references the SimpleDrawingPlayer-Complete project (CPSC 210 Material)
// GitHub link:
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete/blob/master/src/ui/tools/Tool.java

// represents the main frame of a patient management app GUI
public class PatientCatalogUI extends JFrame implements ActionListener {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 375;

    private JButton addPatientButton;
    private JButton viewPatientsButton;
    private JButton saveCatalogButton;
    private JButton loadCatalogButton;

    private AddPatientFields addPatientFields;

    private PatientCatalog patientCatalog;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/catalog.json";

    private LowerButtonPanel lowerButtonPanel;
    private ViewPatientsFrame viewPatientsFrame;

    // EFFECTS: creates patient management app GUI with buttons, labels, and field required to add patient
    //          (buttons for: save catalog, load catalog, and view patients)
    public PatientCatalogUI() {
        super("Patient Management Application");
        this.patientCatalog = new PatientCatalog("My Patient Catalog");
        this.lowerButtonPanel = new LowerButtonPanel();
        initializeGraphics();
        setupLowerButtons();
        viewPatientsFrame = null;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: sets up the graphics for the main frame and makes the frame visible
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.add(new JLabel(createPatientCatalogIcon()));
        createTopTitleIcon();
        createAddButtonAndFields();
        add(lowerButtonPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowListenerForEventLog();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Reference: this method references code from StackOverflow (Call a method when application closes)
    // link: https://stackoverflow.com/questions/13800621/call-a-method-when-application-closes
    // MODIFIES: this
    // EFFECTS: creates a windowListener that prints eventLog to console when application is closed
    private void windowListenerForEventLog() {
        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event currentEvent : EventLog.getInstance()) {
                    System.out.println(currentEvent.getDescription());
                }
            }
        };
        this.addWindowListener(listener);
    }

    // EFFECTS: creates a panel placed at the top of the frame layout and places patient icon on it
    private void createTopTitleIcon() {
        JPanel iconPane = new JPanel();
        iconPane.setLayout(new GridLayout(0, 1));
        iconPane.setSize(new Dimension(0, 0));
        iconPane.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        this.add(iconPane, BorderLayout.PAGE_START);
        ImageIcon patientCatalogTextIcon = new ImageIcon("./data/oneLineTitleIcon2.png");
        Image image = patientCatalogTextIcon.getImage();
        Image resize = image.getScaledInstance(WIDTH - 225, 45, 100);
        patientCatalogTextIcon = new ImageIcon(resize);
        JLabel patientCatalogLabel = new JLabel(patientCatalogTextIcon);
        iconPane.add(patientCatalogLabel);
    }

    // MODIFIES: this
    // EFFECTS: create add button and field for the information required to add a patient to the catalog
    private void createAddButtonAndFields() {
        JPanel addPane = new JPanel();
        addPane.setLayout(new GridLayout(0, 1));
        addPane.setSize(new Dimension(0, 0));
//        add(addPane, BorderLayout.AFTER_LAST_LINE);
//        addPane.add(new JLabel(createPatientCatalogIcon()));
//        addPatientButton = new JButton("Add Patient");
//        addPane.add(addPatientButton);
        addPatientFields = new AddPatientFields();
        add(addPatientFields, BorderLayout.CENTER);
    }

    // EFFECTS: creates a Patient Catalog text icon resized to fit the frame
    public ImageIcon createPatientCatalogIcon() {
        ImageIcon patientCatalogTextIcon = new ImageIcon("./data/patientCatalogText.png");
        Image image = patientCatalogTextIcon.getImage();
        Image resize = image.getScaledInstance(WIDTH - 250, 50, java.awt.Image.SCALE_SMOOTH);
        patientCatalogTextIcon = new ImageIcon(resize);
        return patientCatalogTextIcon;
    }

    // EFFECTS: adds actionsListeners to all the buttons on main frame
    private void setupLowerButtons() {
        addPatientButton = addPatientFields.getAddButton();
        addPatientButton.addActionListener(this);
        viewPatientsButton = lowerButtonPanel.getViewPatientsButton();
        viewPatientsButton.addActionListener(this);
        loadCatalogButton = lowerButtonPanel.getLoadCatalogButton();
        loadCatalogButton.addActionListener(this);
        saveCatalogButton = lowerButtonPanel.getSaveCatalogButton();
        saveCatalogButton.addActionListener(this);
    }

    // MODIFIES: JSON_STORE
    // EFFECTS: saves patient catalog to file (JSON_STORE)
    public void savePatientCatalog() {
        try {
            jsonWriter.open();
            jsonWriter.write(patientCatalog);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            createErrorFrame("ERROR: unable to save your patient catalog to file!");
        }
    }

    // MODIFIES: this
    // EFFECTS: load patient catalog from file
    public void loadPatientCatalog() {
        try {
            patientCatalog = jsonReader.read();
        } catch (IOException e) {
            createErrorFrame("ERROR: unable to load patient catalog from file");
        }
    }

    // MODIFIES: this
    // EFFECTS: listens for specific actions (button presses)
    //          if the viewPatientButton is pressed, crate a new pop up frame to view patients
    //          otherwise, if the loadPatientButton is pressed, load patient catalog from file
    //          otherwise, if the savePatientButton is pressed, save current patient catalog to file
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPatientButton) {
            addPatientToCatalog();
        } else if (e.getSource() == viewPatientsButton) {
            viewPatientsFrame = new ViewPatientsFrame(this);
        } else if (e.getSource() == loadCatalogButton) {
            loadPatientCatalog();
        } else if (e.getSource() == saveCatalogButton) {
            savePatientCatalog();
        }
    }

    // MODIFIES: this
    // EFFECTS: if the input to the addPatientFields is valid, add patient to patientCatalog
    //          otherwise, create the appropriate input error frame
    private void addPatientToCatalog() {

        try {
            String name = addPatientFields.getNameField().getText();
            int healthNum = Integer.parseInt(addPatientFields.getHealthNumField().getText());
            int age = Integer.parseInt(addPatientFields.getAgeField().getText());
            String sex = addPatientFields.getSexField().getText();
            checkContainsSameHealthNum(healthNum);
            patientCatalog.addPatient(name, healthNum, age, sex);
        } catch (PatientAlreadyInCatalogException e) {
            createErrorFrame("ERROR: Cannot have patients with the same health number!");
        } catch (Exception e) {
            createErrorFrame("ERROR: Invalid input! Try again.");
        }
    }

    // EFFECTS: if there is a patient in catalog already throw an exception
    //          otherwise, do nothing
    private void checkContainsSameHealthNum(int healthNum) throws PatientAlreadyInCatalogException {
        if (patientCatalog.containsPatient(healthNum)) {
            throw new PatientAlreadyInCatalogException();
        }
    }

    // EFFECTS: creates input error frame
    private void createErrorFrame(String errorString) {
        JFrame errorFrame = new JFrame("ERROR");
        errorFrame.setMinimumSize(new Dimension(500, 100));

        JLabel errorMessage = new JLabel(errorString, createErrorIcon(), JLabel.CENTER);
        errorMessage.setForeground(Color.RED);
        errorMessage.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        errorFrame.add(errorMessage, BorderLayout.CENTER);
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorFrame.setLocationRelativeTo(null);
        errorFrame.setVisible(true);
    }

    // EFFECTS: creates an error icon
    private Icon createErrorIcon() {
        ImageIcon errorIcon = new ImageIcon("./data/errorIcon.png");
        Image image = errorIcon.getImage();
        Image resize = image.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH);
        errorIcon = new ImageIcon(resize);
        return errorIcon;
    }


    // getter
    public PatientCatalog getPatientCatalog() {
        return patientCatalog;
    }

    // EFFECTS: runs main frame for patient catalog UI
    public static void main(String[] args) {
        new PatientCatalogUI();
    }

}
