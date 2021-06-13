import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

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
            System.out.println("[1] Search for patient");
            System.out.println("[2] Edit patient info");
            System.out.println("[3] Add patient");
            System.out.println("[4] Remove patient");
            System.out.println("[5] View working employees");


            System.out.println("[-1] Quit");

            int commandNum = getInputNumber(scnr);
            switch (commandNum) {
                case 0 -> viewPatients(scnr, patients);
                case 1 -> searchAndView(scnr, patients);
                case 2 -> editPatientInfo(scnr, patients, employees);
                case 3 -> createNewPatient(scnr, patients);
                case 4 -> removePatient(scnr, patients);
                case 5 -> viewEmployees(scnr, employees);

                case -1 -> doLoop = false;
                default -> System.out.println("Whoops! I didn't recognize that command! Please try again.");
            }
            System.out.println();
        }

        patients.save();
        System.out.println("Real Doctors shouldn't treat Patients like Objects!!!");
    }

    public static int getInputNumber(Scanner scnr){
        boolean doExit = false;
        int output = 0;
        while(!doExit){
            try {
                output = scnr.nextInt();
                doExit = true;
            } catch (Exception e){
                System.out.println("Whoops! I didn't catch that number. Please try again");
            }
        }
        scnr.nextLine();
        return output;
    }

    public static void viewEmployees(Scanner scnr, ArrayList<Employee> employees){
        System.out.println("Employees on the clock:");
        System.out.println("Name              ID     Birthdate  Title");
        for (Employee e : employees) {
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
        System.out.println("[-1] Return to main menu");

        int i = getInputNumber(scnr);
        switch (i) {
            case -1 -> {System.out.println("Returning to main menu"); return;}
            case 0 -> patients1.sort(new PatientSortByLastName());
            case 1 -> patients1.sort(new PatientSortByFirstName());
            case 2 -> patients1.sort(new PatientSortByAcuity());
            case 3 -> patients1.sort(new PatientSortByProvider());
            default -> {System.out.println("I didn't recognize that command. Returning to main menu"); return;}
        }
        for (Patient p: patients1) {
            System.out.println(p);
        }
        System.out.println("Press enter to continue to main menu");
        scnr.nextLine();
    }

    public static void searchAndView(Scanner scnr, Storage patients){
        Patient target = findPatient(scnr, patients);
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
        if (target == null) return;
        System.out.println(target);
        System.out.println("What would you like to change?");
        System.out.println("[0] Acuity");
        System.out.println("[1] Provider");
        System.out.println("[2] Problems List");
        System.out.println("[3] Name");
        System.out.println("[-1] Return to main menu");

        int i = getInputNumber(scnr);
        if (i == -1) {System.out.println("Returning to main menu"); return;}
        if (i == 2) { editPatientProblems(scnr, target); return; }

        switch(i){
            case 0 -> {
                System.out.println("Enter the new value: ");
                int newAcuity = getInputNumber(scnr);
                scnr.nextLine();
                target.setAcuity(newAcuity);
            }
            case 1 -> {
                System.out.println("Select a new provider");
                Employee newProvider = listAndGetEmployee(scnr, employees);
                if (newProvider != null)
                target.setProvider(newProvider.getFirstName() + " " + newProvider.getLastName());
            }
            case 3 -> {
                System.out.println("Which name would you like to change?");
                System.out.println("[0] First Name");
                System.out.println("[1] Last Name");
                System.out.println("[-1] Cancel");
                int j = getInputNumber(scnr);
                switch (j){
                    case 0 ->  {
                        System.out.print("Enter a new First Name: ");
                        String newName = scnr.nextLine();
                        target.setFirstName(newName);
                    }
                    case 1 -> {
                        System.out.print("Enter a new Last Name:");
                        String newName = scnr.nextLine();
                        target.setLastName(newName);
                    }
                }
            }
        }
        System.out.println("Would like to make another change? (y/n)");
        if (scnr.nextLine().toLowerCase().startsWith("y")) editPatientProblems(scnr, target);
        System.out.println("Updated patient information");
        System.out.println(target);
        target.printProblemList();
    }

    public static void editPatientProblems(Scanner scnr, Patient target){
        System.out.println("Would you like to remove or add problem to list?");
        System.out.println("[0] Remove index");
        System.out.println("[1] Add new");
        System.out.println("[-1] Return to main menu");
        int i = getInputNumber(scnr);

        if (i == -1){System.out.println("Returning to main menu"); return;}

        LinkedList<String> currentProblems = target.getProblemList();
        if (i == 0){
            int j = 0;
            for (String s : currentProblems) {
                System.out.printf("[%d] %s", j++, s);
            }
            i = getInputNumber(scnr);
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
        int acuity = getInputNumber(scnr);
        System.out.print("Patient Provider: ");
        String provider = scnr.nextLine();
        provider = (provider.equals("")) ? null : provider;

        Patient p = new Patient(firstName, lastName, birthday, provider, acuity);
        patientList.add(p);

        System.out.println("Anything to add to the Problems Chart? (y/n)");
        if (scnr.nextLine().toLowerCase().startsWith("y")){
            System.out.println("Enter an entry for the Problems Chart: ");
            String next = getProblemListNewProblem(scnr);
            if (next != null) p.getProblemList().add(next);
        }
        System.out.println("\n" + p);
    }

    public static ArrayList<Patient> searchPatients(Scanner scnr, Storage patients){
        Patient[] patients1 = patients.getList().toArray(Patient[]::new);
        Arrays.sort(patients1, new PatientSortByAcuity());
        System.out.println("[0] Search by Name");
        System.out.println("[1] Search by Acuity");
        System.out.println("[2] Search by ID");
        System.out.println("[3] Search by Provider");
        System.out.println("[-1] Return to main menu");

        int i = getInputNumber(scnr);
        ArrayList<Patient> found = new ArrayList<>();

        switch(i){
            case -1 -> {System.out.println("Returning to main menu"); return null;}
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
                int score = getInputNumber(scnr);
                for (Patient p: patients1){
                    if (p.getAcuity() == score){
                        found.add(p);
                    }
                }
            }
            case 2 -> {
                System.out.print("Enter the ID of the patient: ");
                int id = getInputNumber(scnr);
                for (Patient p: patients1){
                    if (p.getIdNumber() == id){
                        found.add(p);
                    }
                }
            }
            case 3 -> {
                System.out.print("Enter the Provider you would like to search: ");
                String name = scnr.nextLine();
                for (Patient p : patients1) {
                    if (p.getProvider().contains(name)){
                        found.add(p);
                    }
                }
            }
        }
        if (found.size() == 0) {
            System.out.println("No Patients were found! would you like to try again? (y/n)");
            if (scnr.nextLine().toLowerCase().startsWith("y")) return searchPatients(scnr, patients);
        }
        return found;
    }

    public static Patient findPatient(Scanner scnr, Storage patients){
        ArrayList<Patient> found = searchPatients(scnr, patients);

        if (found == null) return null;
        System.out.println();
        System.out.printf("%d Patient(s) found\n", found.size());
        Patient target;
        if (found.size() > 1){
            target = listAndGetPatient(scnr, found);
        } else if (found.size() == 1) target = found.get(0);
        else {
            System.out.println("No Patients were found");
            return null;
        }
        System.out.println();
        return target;
    }

    public static Patient listAndGetPatient(Scanner scnr, ArrayList<Patient> patients){
        for (int i = 0; i < patients.size(); i++) {
            System.out.printf("[%d] %s\n", i, patients.get(i));
        }
        System.out.println("[-1] Return to main menu\n");
        System.out.print("Which Patient did you mean? ");
        int patNum = getInputNumber(scnr);
        if (patNum == -1) return null;
        return patients.get(patNum);
    }

    public static Employee listAndGetEmployee(Scanner scnr, ArrayList<Employee> employees){
        for (int i = 0; i < employees.size(); i++) {
            System.out.printf("[%d] %s\n", i, employees.get(i));
        }
        System.out.println("[-1] Null");
        System.out.println("Which employee did you mean?");
        int patNum = getInputNumber(scnr);
        if (patNum == -1) return null;
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
                    System.out.println("Enter the date of birth (DD-MM-YYYY): ");
                    String dob = sc.next();

                    if (dob.toLowerCase().startsWith("q")) break;

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    ld = LocalDate.parse(dob, formatter);
                } catch (Exception e){
                    System.out.println("I'm sorry, I wasn't able to parse that, please try again." +
                            "Remember to include the dashes, DD-MM-YYYY (Enter Q/q to quit");
                    continue;
                }
                dateCorrect = true;
            }
            return ld;
        }
}
