import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Storage {
    private final String JSON_DIRECTORY = "Assets/Storage.csv";  //file path of json storage

    private final ArrayList<Patient> list = new ArrayList<>(); //ArrayList of type patient

    //Starts storage and loads
    public Storage() throws IOException {
        load();
    }
    //Saves patient info from ArrayList to JSON file
    public void save() throws IOException {
        FileWriter clean = new FileWriter(JSON_DIRECTORY);
        FileWriter file = new FileWriter(JSON_DIRECTORY, true);

        clean.append("{firstName, lastName, birthday, ID, Acuity, [problems list]}");
        clean.close();

        file.write("\n{\n");

        for (int i = 0; i < list.size(); i++) {



            file.append("{ ").append(list.get(i).getFirstName()).append(", ").append(list.get(i).getLastName()).append(", ").append(list.get(i).getBirthday().toString()).append(", ").append(String.valueOf(list.get(i).getIdNumber())).append(", ").append(String.valueOf(list.get(i).getAcuity())).append(", ").append(list.get(i).getProblemList().toString()).append("}");
            if (i != list.size() - 1) {
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
        file.nextLine();
        file.next();

        while (file.hasNext()) {

            String firstName = removeComma(file.next());
            String lastName = removeComma(file.next());
            LocalDate birthday = LocalDate.parse(removeComma(file.next()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int id = Integer.parseInt(removeComma(file.next()));
            int acuity = Integer.parseInt(removeComma(file.next()));
            LinkedList<String> problems = parseLinkedList(file.nextLine());
            list.add(new Patient(firstName, lastName, birthday, id, null, acuity, problems));
            file.next();
        }
        System.out.println("LOADED SUCCESSFULLY");
    }
    //helps with load()
    private String removeComma(String str) {
        return str.substring(0, str.length() - 1);
    }
    private LinkedList<String> parseLinkedList(String str) {
        if (str.length() <= 5) {
            return new LinkedList<>();
        }

        str = str.substring(2, str.length() - 3);
        String[] strA = str.split(",");

        return new LinkedList<>(Arrays.asList(strA));
    }
    public void add(Patient patient) {
        list.add(patient);
    }
    //Takes longer because arraylist TODO: change to use ID instead of index
    public void remove(int index) {
        list.remove(index);
    }
    public void remove(Patient p){list.remove(p);}
    public ArrayList<Patient> getList() {
        return list;
    }
}
