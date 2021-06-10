import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Storage {
    private final String JSON_DIRECTORY = "Assets/Storage.csv";  //file path of json storage

    private final ArrayList<Patient> list = new ArrayList(); //ArrayList of type patient

    //Starts storage and loads
    public Storage() throws IOException {
        load();
    }
    //Saves patient info from ArrayList to csv file
    public void save() throws IOException {
        FileWriter clean = new FileWriter(JSON_DIRECTORY);
        FileWriter file = new FileWriter(JSON_DIRECTORY, true);

        clean.append("{firstName, lastName, birthday, ID, provider, Acuity, [problems list]}");
        clean.close();

        file.write("\n{\n");

        for (int i = 0; i < list.size(); i++) {
            file.append("{ ");
            file.append(list.get(i).getFirstName()).append(", ");
            file.append(list.get(i).getLastName()).append(", ");
            if (list.get(i).getBirthday() != null) {
                file.append(list.get(i).getBirthday().toString()).append(", ");
            } else {
                file.append("null, ");
            }
            file.append(String.valueOf(list.get(i).getIdNumber())).append(", ");
            if (list.get(i).getProvider() != null) {
                file.append(list.get(i).getProvider()).append(", ");
            } else {
                file.append("null, ");
            }
            file.append(String.valueOf(list.get(i).getAcuity())).append(", ");
            file.append(list.get(i).getProblemList().toString()).append("}");

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

            // This is the magic sauce:
            // This RegularExpression basically says, "Hey, it's a match when
            // it is a {comma followed by a space} a {open bracket followed by a space}
            // a {closed bracket followed by no or more commas}
            file.useDelimiter(",\s|\\{\s|}(,*)");
            String firstName = file.next().strip();
            String lastName = file.next();
            //checks if null
            String sBirthday = file.next();
            LocalDate birthday;
            if (!sBirthday.equals("null")) {
                birthday = LocalDate.parse(sBirthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else {
                birthday = null;
            }
            int id = Integer.parseInt(file.next());
            //checks if null
            String tProvider = file.next();
            String provider;
            if (!tProvider.equals("null")) {
                provider = tProvider;
            } else {
                provider = null;
            }
            int acuity = Integer.parseInt(file.next());
            //checks if null
            String tProblems = file.nextLine();
            LinkedList<String> problems;
            if (!tProblems.equals(" null}")) {
                problems = parseLinkedList(tProblems);
            } else {
                problems = null;
            }
            list.add(new Patient(firstName, lastName, birthday, id, provider, acuity, problems));
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
        strA[0] = strA[0].substring(1);

        return new LinkedList<>(Arrays.asList(strA));
    }
    public void add(Patient patient) {
        list.add(patient);
    }
    //Takes longer because arraylist
    public void remove(int index) {
        list.remove(index);
    }
    public ArrayList<Patient> getList() {
        return list;
    }
}
