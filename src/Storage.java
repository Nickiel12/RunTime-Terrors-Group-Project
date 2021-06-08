import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String JSON_DIRECTORY = "Assets/Storage.json";  //file path of json storage

    private final ArrayList<Patient> list = new ArrayList(); //ArrayList of type patient

    //Starts storage and loads
    public Storage() throws IOException {
        load();
    }
    //Saves patient info from ArrayList to JSON file
    public void save() throws IOException {
        FileWriter clean = new FileWriter(JSON_DIRECTORY);
        FileWriter file = new FileWriter(JSON_DIRECTORY, true);

        clean.append("");
        clean.close();

        file.write("{\n");

        for (int i = 0; i < list.size() - 1; i++) {
            file.append("  \"").append(String.valueOf(i)).append("\": {\n    \"name\": \"").append(list.get(i).getName()).append("\",\n    \"acuity\": \"").append(String.valueOf(list.get(i).getAcuity())).append("\",\n    \"provider\": \"").append(list.get(i).getProvider()).append("\"\n  }");
            if (i != list.size() - 2) {
                file.append(",\n");
            }
        }
        file.append("\n}");
        file.close();
    }
    //loads all patient info into arraylist
    public void load() throws FileNotFoundException {
        System.out.println("LOADING...");
        Scanner file = new Scanner(new FileInputStream(JSON_DIRECTORY));
        if (!file.hasNext()) {
            System.out.println("File is empty");
            return;
        }
        file.nextLine();

        while (file.hasNext()) {

            file.nextLine();
            if (file.hasNext()) {   //prevents NoSuchElementException
                file.next();
            } else break;

            String name = file.next();
            file.next();
            String acuity = file.next();
            file.next();
            String provider = file.next();
            file.next();
            list.add(new Patient(removeQuotes(name), Integer.parseInt(removeQuotes(acuity)), removeQuotes(provider)));
            file.nextLine();
        }
        System.out.println("LOADED SUCCESSFULLY");
    }
    //helps with load()
    private String removeQuotes(String str) {
        String newStr;
        if (str.indexOf('\"') == 0) {
             newStr = str.substring(1);  //removes first quote and everything before it

            if (newStr.indexOf('\"') != -1) {
                newStr = newStr.substring(0, newStr.indexOf('\"'));  //removes second quote and everything after it
            } else {    // if failed then return original string
                return str;
            }
        } else {        // if failed then return original string
            return str;
        }
        return newStr;
    }
    //add a new patient
    public void add(String name, int acuity, String provider) {
        list.add(new Patient(name, acuity, provider));
    }
    public void add(Patient patient) {
        list.add(patient);
    }
    //Takes longer because arraylist
    public void remove(int index) {
        list.remove(index);
    }
}
