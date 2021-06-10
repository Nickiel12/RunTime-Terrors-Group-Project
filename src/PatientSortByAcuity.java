import java.util.Comparator;

public class PatientSortByAcuity implements Comparator<Patient> {

    // Sorts lowest acuity to highest, alphabetically
    public int compare(Patient p1, Patient p2){
        int diffAcuity = p2.getAcuity() - p1.getAcuity();
        if (diffAcuity == 0){
            return p1.getLastName().compareTo(p2.getLastName());
        } else return diffAcuity;
    }
}
