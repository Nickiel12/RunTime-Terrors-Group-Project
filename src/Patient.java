
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDate;
import java.time.Period;

public class Patient {
    private String firstName;
    private String lastName;
    private LinkedList<String> problemList;
    private LocalDate birthday;
    private int acuity;
    private String provider;
    private int idNumber;
    private static int idCounter = 100000;
    final LocalDate currentDate = LocalDate.now();

    public Patient(String firstName, String lastName){
        this(firstName, lastName, null, createID(), "", 0, null);
    }
    public Patient(String firstName, String lastName, String birthday) throws DateTimeParseException{
        this(firstName, lastName, LocalDate.parse(birthday), createID(), null, 0, null);
    }
    public Patient(String firstName, String lastName, int acuityScore){
        this(firstName, lastName, null, createID(), null, acuityScore, null);
    }
    public Patient(String firstName, String lastName, String birthday, int acuityScore) throws DateTimeParseException {
        this(firstName, lastName, LocalDate.parse(birthday), createID(), null, acuityScore, null);
    }
    public Patient(String firstName, String lastName, LocalDate birthday, int acuityScore){
        this(firstName, lastName, birthday, createID(), null, acuityScore, null);
    }
    public Patient(String firstName, String lastName, String birthday, String provider, int acuityScore) throws DateTimeParseException {
        this(firstName, lastName, LocalDate.parse(birthday), createID(), provider, acuityScore, null);
    }
    public Patient(String firstName, String lastName, LocalDate birthday, String provider, int acuityScore){
        this(firstName, lastName, birthday, createID(), provider, acuityScore, null);
    }
    public Patient(String firstName, String lastName, LocalDate birthday, int ID, String provider, int acuityScore, LinkedList<String> problems){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.idNumber = ID;
        this.provider = provider;
        this.acuity = acuityScore;
        this.problemList = (problems == null) ? new LinkedList<>() : problems;
    }

    public static synchronized int createID(){
        return idCounter++;
    }

    // Name
    public void setName(Scanner scnr){
        System.out.println("Enter Patient First Name:");
        this.firstName = scnr.next();
        System.out.println("Enter Patient Last Name:");
        this.lastName = scnr.next();
    }
    public void printName(){
        System.out.println("Patient Name: ");
        System.out.println(lastName + ", "+ firstName);
    }
    public String getName(){
        return lastName + ", " + firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }

    public void setProvider(String provider){
        this.provider = provider;
    }
    public String getProvider(){
        return (provider == null) ? "None" : provider;
    }

    // Birthday
    public void setBirthday(Scanner sc) {
        boolean dateCorrect = false;

        //Converting String to Date

        // I made a loop so that if it fails, it asks again
        LocalDate ld = null;
        while(!dateCorrect){
            try{
                System.out.println("Enter your date of birth (dd/MM/yyyy): ");
                String dob = sc.next();

                if (dob.toLowerCase().startsWith("q")) break;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                ld = LocalDate.parse(dob, formatter);
            } catch (Exception e){
                System.out.println("I'm sorry, I wasn't able to parse that, please try again. (Enter Q/q to quit");
                continue;
            }
            dateCorrect = true;
        }
        this.birthday = ld;

    }
    public void setBirthday(LocalDate ld){
        this.birthday = ld;
    }
    public void printBirthday(){
        System.out.println("DOB: " + birthday);
    }
    public LocalDate getBirthday(){
        return birthday;
    }

    // Age
    public void printAge(){
        System.out.println("Age: " + Period.between(birthday, currentDate).getYears());
    }
    public int getAge(){
        return Period.between(birthday, currentDate).getYears();
    }

    // ProblemList
    public void setProblemList(Scanner scnr){
        this.problemList = new LinkedList<>();
        System.out.println("Input patient diagnosis: \nEnter 'exit' when finished");
        String input = scnr.next();
        while(!input.equals("exit")){
            problemList.add(input);
            input = scnr.next();
        }
    }
    public void setProblemList(LinkedList<String> newList){
        this.problemList = newList;
    }
    public LinkedList<String> getProblemList(){return problemList;}
    public void printProblemList(){
        System.out.println("Problem List: ");
        for (String s : problemList) {
            System.out.println("\t " + s);
        }
    }

    // Id Number
    public void setIdNumber(){
        this.idNumber = createID();
    }
    public void setIdNumber(int newId){
        this.idNumber = newId;
    }
    public void printIdNumber(){
        System.out.println("Patient ID: " + idNumber);
    }
    public int getIdNumber(){return this.idNumber;}

    // Acuity
    public int getAcuity(){
        return acuity;
    }
    public void setAcuity(int acuityScore){
        this.acuity = acuityScore;
    }
    public void printAcuity(){System.out.println("Patient Acuity: " + acuity);}

    public void printPatientInfo(){
        this.getName();
        this.printIdNumber();
        this.printBirthday();
        this.getAge();
        this.printProblemList();
    }
    public void newPatient(Scanner scnr){
        this.setName(scnr);
        this.setBirthday(scnr);
        this.setIdNumber();
        this.setProblemList(scnr);
    }

    public boolean equals(Patient p1){
        return (p1.getName().equals(this.getName())
            && p1.getIdNumber() == this.getIdNumber()
            && p1.getAge() == this.getAge());
    }

    public String toString(){
        return String.format("%-19s DOB: %-10s Acuity Score: %-2d ID: %-7d Provider: %s",
                getName(), getBirthday().toString(), getAcuity(), getIdNumber(), getProvider());
    }

    public static void setIdCounter(int idCounter) {
        Patient.idCounter = idCounter;
    }

    public static int getIdCounter() {
        return idCounter;
    }
}
