import java.util.Comparator;

public class PatientSortByDOB implements Comparator<Patient> {

    public int compare(Patient p1, Patient p2){
        return p1.getBirthday().compareTo(p2.getBirthday());
    }
}
