import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Storage {
    private final int NUMBER_OF_OBJECTS = 4; //how many objects are in patient (i.e. ID, firstname, lastname, DOB)
    private final String JSON_DIRECTORY = "Assets/Storage.JSON";  //file path of json storage

    private int CurID;   //I implemented an ID system to keep track of where each person is

    private final ArrayList<String> list = new ArrayList(); //ArrayList of type String

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

        for (int i = 0; i < list.size() - 1; i+= NUMBER_OF_OBJECTS) {
            list.set(i, i + "");
            file.append("  \"").append(String.valueOf(i)).append("\": {\n    \"ID\": \"").append(list.get(i)).append("\",\n    \"firstName\": \"").append(list.get(i + 1)).append("\",\n    \"lastName\": \"").append(list.get(i + 2)).append("\",\n    \"DOB\": \"").append(list.get(i + 3)).append("\"\n  }");
            if (i != list.size() - NUMBER_OF_OBJECTS) {
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
        CurID = -4;

        while (file.hasNext()) {
            CurID += NUMBER_OF_OBJECTS;

            file.nextLine();
            if (file.hasNext()) {   //prevents NoSuchElementException
                file.next();
            } else break;

            for (int j = 0; j < NUMBER_OF_OBJECTS; j++) {

                String temp = file.next();
                list.add(removeQuotes(temp));
                file.next();
            }
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
    //add a patient with first last and DOB TODO: create another add method that has a patient as a parameter
    public void add(String firstName, String lastName, String DOB) {
        list.add(CurID + "");
        list.add(firstName);
        list.add(lastName);
        list.add(DOB);
    }
    //Takes linger because arraylist
    public void remove(int ID) {
        if (ID == 0) {   //special case ID = 0
            list.subList(0, 4).clear();
        } else if (list.size() == 0) {       //special case list is empty
            System.out.println("File is empty, nothing was removed");
        } else {
            Iterator<String> iterator = list.iterator();
            for (int i = 0; i < ID; i++) {
                iterator.next();
            }
            for (int i = 0; i < NUMBER_OF_OBJECTS; i++) {
                iterator.remove();
                iterator.next();
            }
        }
    }
    //Lists all patients to out
    public void list() {
        System.out.println("{");

        for (int i = 0; i < list.size() - 1; i+= NUMBER_OF_OBJECTS) {
            list.set(i, i + "");
            System.out.append("  \"").append(String.valueOf(i)).append("\": {\n    \"ID\": \"").append(list.get(i)).append("\",\n    \"firstName\": \"").append(list.get(i + 1)).append("\",\n    \"lastName\": \"").append(list.get(i + 2)).append("\",\n    \"DOB\": \"").append(list.get(i + 3)).append("\"\n  }");
            if (i != list.size() - NUMBER_OF_OBJECTS) {
                System.out.append(",\n");
            }
        }
        System.out.append("\n}");
    }
}
