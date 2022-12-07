package ui;

import javax.swing.*;
import java.awt.*;
// NOTE: this code references the SimpleDrawingPlayer-Complete project (CPSC 210 Material)
// GitHub link:
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete/blob/master/src/ui/tools/Tool.java

// represents a button panel that will be displayed at the bottom on the patient catalog main frame
public class LowerButtonPanel extends JPanel {

    private JButton viewPatientsButton;
    private JButton loadCatalogButton;
    private JButton saveCatalogButton;

    // EFFECTS: creates a button panel will three buttons (view, load, save)
    public LowerButtonPanel() {
        this.setLayout(new GridLayout(0,1));
        this.setSize(new Dimension(0,0));
        this.setBorder(BorderFactory.createEmptyBorder(0,20,10,20));
        createButtons();
        this.add(viewPatientsButton);
        this.add(loadCatalogButton);
        this.add(saveCatalogButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates the three buttons (view, load, save) that will be displayed on the bottom of the patient catalog
    //          main frame
    private void createButtons() {
        viewPatientsButton = new JButton("View Patients");
        loadCatalogButton = new JButton("Load Patient Catalog");
        saveCatalogButton = new JButton("Save Patient Catalog");
    }

    // getters

    public JButton getViewPatientsButton() {
        return viewPatientsButton;
    }

    public JButton getLoadCatalogButton() {
        return loadCatalogButton;
    }

    public JButton getSaveCatalogButton() {
        return saveCatalogButton;
    }

}
