# Patient-Management-Catalog
### Project Description 

This application is meant to be used in a clinical setting. Specifically,
doctors, nurses, and caretakers can use this to manage their patients'
information. For each patient, information such as their name, age,
COVID vaccination status, allergies, and chronic health
conditions can be stored. The user can calculate the
*Patient Risk Index* (PRI) based on patient's health information
(i.e., number of allergies, number of chronic health conditions, vaccinated or not, etc.,).

Additionally, the user can extract and search for required information as needed.

**For example:**
- Printing an overview of a patient's health information before an appointment
- searching for a list of patients with specific conditions
- adding allergies and chronic conditions to the patient's health profile 

### Personal Interest

Not only is this project a valuable way of advancing my programming knowledge, but
it also aligns with my interests! I am an Integrated Sciences student, integrating
computer science and health research. Specifically, I am interested in how
computational tools can be applied to advance research and health systems. This
project acts as a beginner-level exposure for the practical applications of software design!

### USER STORY 
- As a user, I want to be able to add a patient to my patient catalog
- As a user, I want to be able to remove a patient from my catalog
- As a user, I want to be able to select a patient in my catalog and view their health information
- As a user, I want to be able to search up a list of patients with a given allergy 
- As a user, I want to be able to search up a list of patients with a given condition 
- As a user, I want to be able to edit the Health Information of patients in my catalog 
- As a user, I want to be able to save my patient catalog to file
- As a user, I want to have the option to load a patient catalog from file

### Phase 4: Task 2
- Jennifer Nguyen: 1234 ---> ADDED to Patient Catalog
- Annabell Nguyen: 1122 ---> ADDED to Patient Catalog
- Jennifer Nguyen: 1234 ---> REMOVED from Patient Catalog
- Billie Eilish : 6666 ---> ADDED to Patient Catalog

### Phase 4: Task 3
If I had more time to work on this project, I would improve the overall design of my code. 
Specifically, I would increase cohesion of my classes and methods to
better adhere to the Single Responsibility Principle (SRP). There are
some methods that can be simplified (i.e., extract method) for better readability. Some classes involved in the
GUI are also complicated and have many responsibilities. I would look into improving SRP by
moving fields and methods into a new class (splitting one class into two/multiple).
Additionally, I would minimize coupling between the GUI component classes especially
between PatientCatalog, PatientCatalogUI, and ViewPatientsFrame. I would also 
look into improving the longevity of my code by refactoring methods to reduce 
code clones (single point of control). Finally, I would explore implementing an Observer pattern between
PatientCatalog and my ViewPatientsFrame so that the ViewPatientsFrame is automatically updated
whenever a patient is added or removed from the catalog.
