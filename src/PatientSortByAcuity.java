import java.util.Comparator;

public class PatientSortByAcuity implements Comparator<Patient> {

    // Sorts lowest acuity to highest, alphabetically
    public int compare(Patient p1, Patient p2){
        int diffAcuity = p1.getAcuity() - p2.getAcuity();
        if (diffAcuity == 0){
            return p1.getName().compareTo(p2.getName());
        } else return diffAcuity;
    }
}
