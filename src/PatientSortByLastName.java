import java.util.Comparator;

public class PatientSortByLastName implements Comparator<Patient> {
    public int compare(Patient p1, Patient p2) {
        return p1.getLastName().compareTo(p2.getLastName());
    }
}
