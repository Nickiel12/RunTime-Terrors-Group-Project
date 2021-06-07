import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        // Using ArrayList instead of LinkedList because I am assuming
        // that we will have random access more that adding and removing patients
        ArrayList<Patient> patients = new ArrayList<>();

        System.out.println("Welcome to [Error: NotImplementedYet]");
        System.out.println("Please [Error: NotImplementedYet]");

        System.out.println("\n");

        boolean doLoop = true;
        while (doLoop) {
            int commandNum = listAndGetCommand(scnr);


            switch (commandNum) {
                case 0 -> viewPatients(scnr, patients);
                case 1 -> System.out.println("NotImplementedYet!");
                case 2 -> System.out.println("NotImplementedYet!");
                case 3 -> System.out.println("NotImplementedYet!");
                case 4 -> System.out.println("NotImplementedYet!");
                case 5 -> System.out.println("NotImplementedYet!");

                case 9 -> doLoop = false;
            }
        }

        //TODO actually do something meaningful
        System.out.println("Real Doctors shouldn't treat Patients like Objects!!!");
    }


    public static void viewPatients(Scanner scnr, ArrayList<Patient> patients){
        Patient[] patients1 = patients.toArray(Patient[]::new);
        System.out.println("[0] Alphabetical");
        System.out.println("[1] By Acuity");
        System.out.println("[2] By provider");

        int i = scnr.nextInt();
        switch (i) {
            case 0 -> Arrays.sort(patients1, new PatientSortByName());
            case 1 -> Arrays.sort(patients1, new PatientSortByAcuity());
            case 2 -> Arrays.sort(patients1, new PatientSortByProvider());
        }
        for (Patient p :
                patients1) {
            System.out.println(p);
        }

    }

    public static int listAndGetCommand(Scanner scnr){
        System.out.println("[0] View current patients");
        System.out.println("[1] Search current patients");
        System.out.println("[2] Edit patient info");
        System.out.println("[3] Add patient");
        System.out.println("[4] Remove patient");

        System.out.println("[9] Quit");
        System.out.println();
        return scnr.nextInt();
    }

}
