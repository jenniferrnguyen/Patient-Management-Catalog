package ui;

import javax.swing.*;
import java.awt.*;

// this class references code from a Java Tutorials Code Sample - FormattedTextFieldDemo.java
// link: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https:
//       docs.oracle.com/javase/tutorial/uiswing/examples/components/FormattedTextFieldDemoProject
//       src/components/FormattedTextFieldDemo.java

// this class produces the fields and corresponding labels to collect info to add a new patient to the catalog
public class AddPatientFields extends JPanel {

    private static final int TEXT_COLUMNS = 20;

    protected JLabel nameLabel;
    protected JLabel healthNumLabel;
    protected JLabel ageLabel;
    protected JLabel sexLabel;

    protected JTextField nameField;
    protected JTextField healthNumField;
    protected JTextField ageField;
    protected JTextField sexField;

    private JPanel labelPane;
    private JPanel fieldPane;
    protected JButton addButton;

    // EFFECTS: creates labels and fields for user to input the information required to create a new Patient
    //          which will be added to the patient catalog
    public AddPatientFields() {
        super(new BorderLayout());
        createLabels();

        // create test fields
        nameField = new JTextField();
        nameField.setColumns(TEXT_COLUMNS);
        healthNumField = new JTextField();
        healthNumField.setColumns(TEXT_COLUMNS);
        ageField = new JTextField();
        ageField.setColumns(TEXT_COLUMNS);
        sexField = new JTextField();
        sexField.setColumns(TEXT_COLUMNS);

        // Tell accessibility tools about label/textfield pairs
        nameLabel.setLabelFor(nameField);
        healthNumLabel.setLabelFor(healthNumField);
        ageLabel.setLabelFor(ageField);
        sexLabel.setLabelFor(sexField);

        layOutLabels();
        layOutTextFields();
        addButton = new JButton("Add Patient");

        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(addButton, BorderLayout.NORTH);
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
    }

    // MODIFIES: this
    // EFFECTS: creates the appropriate labels that asks user for the patient name, health number
    //          age, and biological sex
    private void createLabels() {
        nameLabel = new JLabel("Enter the new patient's name:");
        healthNumLabel = new JLabel("Enter the patient's Health Number:");
        ageLabel = new JLabel("Enter the patient's age:");
        sexLabel = new JLabel("Enter the patient's biological sex (M or F):");
    }

    // MODIFIES: this
    // EFFECTS: adds the labels for the fields that collect the patients name, health number
    //          age, and sex
    private void layOutLabels() {
        labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.add(nameLabel);
        labelPane.add(healthNumLabel);
        labelPane.add(ageLabel);
        labelPane.add(sexLabel);
    }

    // MODIFIES: this
    // EFFECTS: lays out the text fields that are used to collect the users name, health number, age, and sex
    private void layOutTextFields() {
        fieldPane = new JPanel(new GridLayout(0, 1));
        fieldPane.add(nameField);
        fieldPane.add(healthNumField);
        fieldPane.add(ageField);
        fieldPane.add(sexField);
    }

    // GETTERS --------------------------------------------------------------------------------------------------
    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getHealthNumField() {
        return healthNumField;
    }

    public JTextField getAgeField() {
        return ageField;
    }

    public JTextField getSexField() {
        return sexField;
    }

    public JButton getAddButton() {
        return addButton;
    }

}