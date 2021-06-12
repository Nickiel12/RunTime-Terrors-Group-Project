import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

// TODO standardize and error catch user-inputs
// TODO Patient Search ID

public class Main {

    public static void main(String[] args) throws IOException {

        //Employee(String firstName, String lastName, LocalDate birthday, String title)
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Kathrine", "Watkins",
                LocalDate.of(1950, 6, 5), "Head Doctor"));
        employees.add(new Employee("Colin", "Wilkins",
                LocalDate.of(2055, 5, 24), "Lead Tech"));
        employees.add(new Employee("Nicholas", "Young",
                LocalDate.of(2000, 5, 23), "Psychoanologist"));
        employees.add(new Employee("St.", "Nick",
                null, "Head of Pediatrics"));
        employees.add(new Employee("Jack", "Frost",
                null, "Cryogenics Researcher"));
        //employees.add(new Employee(""))
        Scanner scnr = new Scanner(System.in);

        Storage patients = new Storage();

        System.out.println("Welcome to the RunTime Terrors Patient Tracking Software");
        System.out.println("Follow the prompts to navigate\n");

        boolean doLoop = true;
        while (doLoop) {
            System.out.println("[0] View patients");
            System.out.println("[1] Search patients");
            System.out.println("[2] Edit patient info");
            System.out.println("[3] Add patient");
            System.out.println("[4] Remove patient");
            System.out.println("[5] View working employees");


            System.out.println("[9] Quit");
            int commandNum = scnr.nextInt();
            scnr.nextLine();
            switch (commandNum) {
                case 0 -> viewPatients(scnr, patients);
                case 1 -> searchAndView(scnr, patients);
                case 2 -> editPatientInfo(scnr, patients, employees);
                case 3 -> createNewPatient(scnr, patients);
                case 4 -> removePatient(scnr, patients);
                case 5 -> viewEmployees(scnr, employees);

                case 9 -> doLoop = false;
            }
            System.out.println();
        }

        patients.save();
        System.out.println("Real Doctors shouldn't treat Patients like Objects!!!");
    }

    public static void viewEmployees(Scanner scnr, ArrayList<Employee> employees){
        System.out.println("Employees on the clock:");
        System.out.println("Name              ID     Birthdate  Title");
        for (Employee e :
                employees) {
            System.out.println(e);
        }
        System.out.println("\nPress Enter to return to menu");
        scnr.nextLine();
    }

    public static void viewPatients(Scanner scnr, Storage patients){
        ArrayList<Patient> patients1 = patients.getList();
        System.out.println("Sort patients by: ");
        System.out.println("[0] Last Name");
        System.out.println("[1] First Name");
        System.out.println("[2] By Acuity");
        System.out.println("[3] By provider");

        int i = scnr.nextInt();
        scnr.nextLine();
        switch (i) {
            case 0 -> patients1.sort(new PatientSortByLastName());
            case 1 -> patients1.sort(new PatientSortByFirstName());
            case 2 -> patients1.sort(new PatientSortByAcuity());
            case 3 -> patients1.sort(new PatientSortByProvider());
        }

        Patient target = listAndGetPatient(scnr, patients1);
        if (target != null) {
            System.out.println(target);
            target.printProblemList();
            System.out.println();
        }
        System.out.println("Press enter to continue");
        scnr.nextLine();
    }

    public static void searchAndView(Scanner scnr, Storage patients){

        Patient[] patients1 = patients.getList().toArray(Patient[]::new);
        Arrays.sort(patients1, new PatientSortByAcuity());
        System.out.println("[0] Search by Name");
        System.out.println("[1] Search by Acuity");
        System.out.println("[2] Search by ID");
        System.out.println("[3] Search by Provider");

        int i = scnr.nextInt();
        scnr.nextLine();
        ArrayList<Patient> found = new ArrayList<>();

        switch(i){
            case 0 -> {
                System.out.print("Enter the Name you would like to search: ");
                String name = scnr.nextLine();
                for (Patient p : patients1) {
                    if (p.getName().contains(name) || (p.getFirstName() + " " + p.getLastName()).contains(name)
                        || (p.getFirstName() + ", " + p.getLastName()).contains(name)){
                        found.add(p);
                    }
                }
            }
            case 1 -> {
                System.out.print("Enter the Acuity score: ");
                int score = scnr.nextInt();
                for (Patient p: patients1){
                    if (p.getAcuity() == score){
                        found.add(p);
                    }
                }
            }
            case 2 -> {
                System.out.print("Enter the ID of the patient: ");
                int id = scnr.nextInt();
                for (Patient p: patients1){
                    if (p.getIdNumber() == id){
                        found.add(p);
                    }
                }
            }
            case 3 -> {
                System.out.print("Enter the Provider you would like to search: ");
                String name = scnr.nextLine();
                for (Patient p :
                        patients1) {
                    if (p.getProvider().contains(name)){
                        found.add(p);
                    }
                }
            }
        }
        Patient target;
        if (found.size() > 1){
            target = listAndGetPatient(scnr, found);
        } else if (found.size() == 1) target = found.get(0);
        else {
            System.out.println("No Patients were found");
            return;
        }
        if (target != null) {
            System.out.println(target);
            target.printProblemList();
            System.out.println();
        }
        System.out.println("Press enter to continue");
        scnr.nextLine();

    }

    public static void removePatient(Scanner scnr, Storage patients) {
        Patient target = findPatient(scnr, patients);
        if (target != null) {
            System.out.println("Are you sure you would like to remove " + target + "? (y/n)");
            String answer = scnr.nextLine();
            if (answer.toLowerCase().startsWith("y")) {
                patients.getList().remove(target);
            } else {
                System.out.println("Removal aborted");
            }
        } else {
            System.out.println("Patient not found!");
        }
    }

    public static void editPatientInfo(Scanner scnr, Storage patients, ArrayList<Employee> employees){
        Patient target = findPatient(scnr, patients);
        System.out.println(target);
        if (target == null) return;
        System.out.println("What would you like to change?");
        System.out.println("[0] Acuity");
        System.out.println("[1] Provider");
        System.out.println("[2] Problems List");
        System.out.println("[9] Quit");

        int i = scnr.nextInt();
        scnr.nextLine();
        if (i == 2)
        {
            editPatientProblems(scnr, target);
            return;
        }
        System.out.print("Enter the new value: ");
        switch(i){
            case 0 -> {
                int newAcuity = scnr.nextInt();
                scnr.nextLine();
                target.setAcuity(newAcuity);
            }
            case 1 -> {
                Employee newProvider = listAndGetEmployee(scnr, employees);
                target.setProvider(newProvider.getFirstName() + " " + newProvider.getLastName());
            }
        }
        System.out.println("Would like to make another change? (y/n)");
        if (scnr.nextLine().toLowerCase().startsWith("y")) editPatientProblems(scnr, target);
    }

    public static void editPatientProblems(Scanner scnr, Patient target){
        LinkedList<String> currentProblems = target.getProblemList();
        System.out.println("Would you like to remove or add problem to list?");
        System.out.println("[0] Remove index");
        System.out.println("[1] Add new");
        int i = scnr.nextInt();
        scnr.nextLine();

        if (i == 0){
            int j = 0;
            for (String s :
                    currentProblems) {
                System.out.printf("[%d] %s", j++, s);
            }
            i = scnr.nextInt();
            try{
                currentProblems.remove(i);
            } catch (IndexOutOfBoundsException e){
                System.out.println("That index was out of bounds\nWould you like to try again?\n(y/n)");
                if (scnr.next().toLowerCase().startsWith("y")) editPatientProblems(scnr, target);
            }
        } else if (i == 1){
            currentProblems.add(getProblemListNewProblem(scnr));
        }
    }

    public static void createNewPatient(Scanner scnr, Storage patientList){
        System.out.print("Patient First Name: ");
        String firstName = scnr.nextLine();
        System.out.print("Patient Last Name: ");
        String lastName = scnr.nextLine();
        LocalDate birthday = getBirthdayFromScanner(scnr);
        System.out.print("Enter the Patient's acuity score: ");
        int acuity = scnr.nextInt();
        scnr.nextLine();
        System.out.print("Patient Provider: ");
        String provider = scnr.nextLine();

        Patient p = new Patient(firstName, lastName, birthday, provider, acuity);
        patientList.add(p);

        System.out.println("Anything to add to the Problems Chart? (y/n)");
        if (scnr.nextLine().toLowerCase().startsWith("y")){
            System.out.println("Enter an entry for the Problems Chart: ");
            String next = getProblemListNewProblem(scnr);
            if (next != null) p.getProblemList().add(next);
        }
    }

    public static Patient findPatient(Scanner scnr, Storage patients){
        System.out.println("Enter a Patients Name: ");
        String patName = scnr.nextLine();
        ArrayList<Patient> found = new ArrayList<>();
        for (Patient p1 : patients.getList()) {
            if (p1.getName().contains(patName)) found.add(p1);
        }
        if (found.size() == 0){
            System.out.println("No patients with that name found");
            return null;
        } else if (found.size() == 1){
            return found.get(0);
        } else {
            System.out.println("Multiple patients with that name were found,\nPlease select with one you want:");
            return listAndGetPatient(scnr, found);
        }
    }

    public static Patient listAndGetPatient(Scanner scnr, ArrayList<Patient> patients){
        for (int i = 0; i < patients.size(); i++) {
            System.out.printf("[%d] %s\n", i, patients.get(i));
        }
        System.out.println("Which Patient did you mean?");
        int patNum = scnr.nextInt();
        scnr.nextLine();
        return patients.get(patNum);
    }
    public static Employee listAndGetEmployee(Scanner scnr, ArrayList<Employee> employees){
        System.out.println("[-1] Null");
        for (int i = 0; i < employees.size(); i++) {
            System.out.printf("[%d] %s\n", i, employees.get(i));
        }
        System.out.println("Which employee did you mean?");
        int patNum = scnr.nextInt();
        scnr.nextLine();
        return employees.get(patNum);
    }

    public static String getProblemListNewProblem(Scanner scnr){
        while (true){
            System.out.println("Enter the new note, No commas allowed! (Q/q to quit)");
            String newNote = scnr.nextLine();
            if (newNote.toLowerCase().startsWith("q")) break;
            if (newNote.contains(",")) System.out.println("Unacceptable entry! Remember, no commas");
            else {
                return newNote;
            }
        }
        return null;
    }

    public static LocalDate getBirthdayFromScanner(Scanner sc){
            boolean dateCorrect = false;
            //Converting String to Date
            // I made a loop so that if it fails, it asks again
            LocalDate ld = null;
            while(!dateCorrect){
                try{
                    System.out.println("Enter the date of birth (dd-MM-yyyy): ");
                    String dob = sc.next();

                    if (dob.toLowerCase().startsWith("q")) break;

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    ld = LocalDate.parse(dob, formatter);
                } catch (Exception e){
                    System.out.println("I'm sorry, I wasn't able to parse that, please try again. (Enter Q/q to quit");
                    continue;
                }
                dateCorrect = true;
            }
            return ld;
        }
}
