import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

public class Patient {
    private String firstName;
    private String lastName;
    private List<String> problemList;
    private LocalDate birthday;
    private int idNumber;
    private static int idCounter = 100000;
    final LocalDate currentDate = LocalDate.now();

    public Patient(){

    }
    public Patient(String name, LocalDate birthday, List<String> problemList, int idNumber){
        this.birthday = birthday;
        this.problemList = new ArrayList<>();
        this.idNumber = idNumber;

    }
    private void setName(){
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter Patient First Name:");
        this.firstName = scnr.next();
        System.out.println("Enter Patient Last Name:");
        this.lastName = scnr.next();
    }
    private void getName(){
        System.out.println("Patient Name: ");
        System.out.println(lastName + ", "+ firstName);
    }

    private static synchronized int createID(){
        return Integer.valueOf(idCounter++);
    }


    private void setBirthday() throws ParseException{
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your date of birth (dd/MM/yyyy): ");
        String dob = sc.next();
        //Converting String to Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate ld = LocalDate.parse(dob, formatter);
        this.birthday = ld;

    }

    private void getBirthday(){
        System.out.println("DOB: " + birthday);
    }
    private void getAge(){
        System.out.println("Age: " + Period.between(birthday, currentDate).getYears());
    }

    private void setProblemList(){
        Scanner scnr = new Scanner(System.in);
        this.problemList = new ArrayList<>();
        System.out.println("Input patient diagnosis: \nEnter 'exit' when finished");
        String input = scnr.next();
        while(!input.equals("exit")){
            problemList.add(input);
            input = scnr.next();
        }
    }
    private void getProblemList(){
        System.out.println("Problem List: ");
        System.out.println(problemList);
    }



    private void setIdNumber(){
        this.idNumber = createID();
    }
    private void getIdNumber(){
        System.out.println("Patient ID: " + idNumber);
    }
    public void patientInfo(){
        this.getName();
        this.getIdNumber();
        this.getBirthday();
        this.getAge();
        this.getProblemList();
    }
    public void newPatient() throws ParseException{
        this.setName();
        try {
            this.setBirthday();
        }catch(ParseException e){
            System.out.println("Parse Exception");
        }
        this.setIdNumber();
        this.setProblemList();
    }


}
