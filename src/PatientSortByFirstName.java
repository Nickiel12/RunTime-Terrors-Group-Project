import java.util.Comparator;

public class PatientSortByFirstName implements Comparator<Patient> {
    public int compare(Patient p1, Patient p2) {
        return p1.getFirstName().compareTo(p2.getFirstName());
    }
}
