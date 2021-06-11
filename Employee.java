import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.Period;

public class Employee{
    private String firstName;
    private String lastName;
    private int empIdNumber;
    private static int idCounter = 200000;
    private LocalDate birthday;
    final LocalDate currentDate = LocalDate.now();
    private String title;

//Constructors
   public Employee(String firstName, String lastName, LocalDate birthday){
       new Employee(firstName, lastName, createID(), birthday, null);
    }
    public Employee(String firstName, String lastName, int ID){
       new Employee(firstName, lastName, ID, null, null);
    }
    public Employee(String firstName, String lastName){
       new Employee(firstName, lastName, createID(), null, null);
    }
    public Employee(String firstName, String lastName, String title){
       new Employee(firstName, lastName, createID(), null, title);
    }
    public Employee(String firstName, String lastName, int ID, String title){
       new Employee(firstName, lastName, ID, null, title);
    }
    public Employee(String firstName, String lastName, LocalDate birthday, String title){
       new Employee(firstName, lastName, createID(), birthday, title);
    }

    public Employee(String firstName, String lastName, int ID, LocalDate birthday, String title){
        this.firstName = firstName;
        this.lastName = lastName;
        this.empIdNumber = ID;
        this.birthday= birthday;
        this.title = title;

    }

//ID Methods
    public static synchronized int createID(){
       return idCounter++;
    }

    public void setEmpIdNumber(){ this.empIdNumber = createID();}
    public void printEmpIdNumber(){ System.out.println("Employee ID Number: " + empIdNumber);}
    public int getEmpIdNumber(){ return empIdNumber;}

//Birthday Methods
    public void setBirthday(Scanner scnr){
        boolean dateCorrect = false;

    //Converting String to Date

    // I made a loop so that if it fails, it asks again
    LocalDate ld = null;
        while(!dateCorrect){
            try{
            System.out.println("Enter your date of birth (dd/MM/yyyy): ");
            String dob = scnr.next();

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
    public void setBirthday(LocalDate ld){this.birthday=ld;}
    public void printBirthday(){ System.out.println("DOB: " + birthday);}
    public LocalDate getBirthday(){ return birthday;}

//Name Methods
    public void setName(Scanner scnr){
        System.out.println("Enter employee's first name: ");
        this.firstName = scnr.next();
        System.out.println("Enter employee's last name: ");
        this.lastName = scnr.next();
    }
    public String getName(){return lastName + ", " + firstName;}
    public void printName(){
        System.out.println("Employee Name:\n" +lastName +", "+ firstName);
    }
    public void setFirstName(Scanner scnr){
        System.out.println("Enter employee's first name: ");
        this.firstName = scnr.next();
   }
    public void setLastName(Scanner scnr){
        System.out.println("Enter employee's last name: ");
        this.lastName = scnr.next();
    }
    public String getFirstName(){ return firstName;}
    public String getLastName(){ return lastName;}



//Age methods
    public void printAge(){
        System.out.println("Age: " + Period.between(birthday, currentDate).getYears());
    }
    public int getAge(){
       return Period.between(birthday, currentDate).getYears();
   }

//Other Methods

    public boolean equals(Employee e1){
       return (e1.getName().equals(this.getName())&&
               e1.getEmpIdNumber() == this.getEmpIdNumber()&&
               e1.getAge() == this.getAge());
    }
    public void newEmployee(Scanner scnr){
       this.setName(scnr);
       this.setBirthday(scnr);
       this.setEmpIdNumber();
       this.setTitle(scnr);

    }
    public void printEmployee(){
       this.printName();
       this.printTitle();
       this.printEmpIdNumber();
       this.printBirthday();
       this.printAge();
    }

//Title Methods
    public void setTitle(Scanner scnr){
        System.out.println("Enter employee title: ");
        this.title = scnr.next();
    }
    public String getTitle(){
       return title;
    }
    public void printTitle(){
        System.out.println("Job Title: " + title);
    }
}

