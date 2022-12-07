package ui;

import model.Patient;
import model.PatientCatalog;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// NOTE: this class references code from the Java Swing Tutorial: ListDemo
// Link:
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java

// represents as frame that displays a list of patients in the patient catalog with a remove button to remove patients
public class ViewPatientsFrame extends JFrame implements ListSelectionListener {

    private static final String REMOVE_STRING = "Remove Patient";
    private JButton removeButton;
    private static final int WIDTH = 550;
    private static final int HEIGHT = 600;

    private PatientCatalog patientCatalog;
    private JList patientList;
    private DefaultListModel<String> listModel;

    // EFFECTS: if the patient catalog is empty, create a frame telling the uses that their catalog is empty and
    //          tell the user to return to the main frame
    //          otherwise, create a frame that displays a list of patients in catalog and remove button
    public ViewPatientsFrame(PatientCatalogUI pcUI) {
        super("View Patients in Catalog");
        patientCatalog = pcUI.getPatientCatalog();
        setLayout(new FlowLayout());
        removeButton = new JButton(REMOVE_STRING);
        removeButton.setActionCommand(REMOVE_STRING);
        removeButton.addActionListener(new RemoveListener());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        if (patientCatalog.getPatients().size() == 0) {
            initEmptyCatalog();
        } else {
            initViewPatients();
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds graphics for the frame - a list of patients and remove button
    private void initViewPatients() {
        listModel = new DefaultListModel();
        addCurrentPatients();
        createPatientList();
        createButtons();
    }

    // MODIFIES: this
    // EFFECTS: creates a remove button and adds it to a panel
    private void createButtons() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(0, 1));
        buttonPane.setSize(new Dimension(0, 0));
        buttonPane.add(removeButton);
        add(buttonPane, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: adds all current patients to the list model
    private void addCurrentPatients() {
        for (Patient p : patientCatalog.getPatients()) {
            listModel.addElement(p.getNameAndHealthNum());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the list with current patients and puts it in a scroll pane
    private void createPatientList() {
        patientList = new JList(listModel);
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientList.setPreferredSize(new Dimension(400, 360));
        patientList.setSelectedIndex(0);
        patientList.addListSelectionListener(this);
        patientList.setVisibleRowCount(15);
        JScrollPane listScrollPane = new JScrollPane(patientList);
        this.add(listScrollPane, BorderLayout.CENTER);
    }


    // MODIFIES: this
    // EFFECTS: creates a label telling uses that there are no patients in their catalog (with a corresponding image)
    private void initEmptyCatalog() {
        JLabel noPatientLabel = new JLabel("There are no patients in your"
                + " catalog yet! Exit and add a patient.");
        noPatientLabel.setVerticalAlignment(JLabel.TOP);
        noPatientLabel.setHorizontalAlignment(JLabel.CENTER);
        noPatientLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        noPatientLabel.setFont(new Font("Serif", Font.BOLD, 18));

        try {
            BufferedImage emptyIcon = ImageIO.read(new File("./data/EmptyImage.png"));
            JLabel emptyLabel = new JLabel(new ImageIcon(emptyIcon));
            this.add(noPatientLabel);
            this.add(emptyLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: if there are no patients in the catalog, disables remove patient button
    //          otherwise, enables function of the remove patient button
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (patientList.getSelectedIndex() == -1) {
                removeButton.setEnabled(false);
//                viewButton.setEnabled(false);
            } else {
                removeButton.setEnabled(true);
//                viewButton.setEnabled(true);
            }
        }
    }


    // represents a listener that removes a selected patient
    public class RemoveListener implements ActionListener {

        // REQUIRES: valid selection
        // EFFECTS: Removes selected patients from patient catalog
        @Override
        public void actionPerformed(ActionEvent e) {

            int index = patientList.getSelectedIndex();

            listModel.remove(index);
            Patient p = patientCatalog.getPatients().get(index);
            patientCatalog.removePatient(p.getHealthNumber());

            int size = listModel.getSize();

            if (size == 0) { // Nobody's left, in catalog, disable remove button
                removeButton.setEnabled(false);
            } else { // Valid selected index
                if (index == listModel.getSize()) {
                    // removed item in last position
                    removeButton.setEnabled(true);
                    index--;
                }
                patientList.setSelectedIndex(index);
                patientList.ensureIndexIsVisible(index);
            }
        }
    }
}


