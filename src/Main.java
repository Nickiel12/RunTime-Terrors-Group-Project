import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        // Using ArrayList instead of LinkedList because I am assuming
        // that we will have random access more that adding and removing patients
        ArrayList<Patient> patients = new ArrayList<>();

        System.out.println("Welcome to [Error: NotImplementedYet]");
        System.out.println("Please [Error: NotImplementedYet]");

        listCommands();

        String nextLine = scnr.nextLine();

        handleInput(nextLine);

        //TODO actually do something meaningful

    }


    public static void handleInput(String input){

    }

    public static void listCommands(){
        System.out.println("[0] View current patients");
        System.out.println("[1] Search current patients");
        System.out.println("[2] Edit patient info");
        System.out.println("[3] Add patient");
        System.out.println("[4] Remove patient");

        System.out.println("[Q/q] Quit");
    }
}
