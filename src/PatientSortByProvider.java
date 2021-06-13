import java.util.Comparator;

public class PatientSortByProvider implements Comparator<Patient> {
    public int compare(Patient p1, Patient p2){
        //deals with null values
        if (p1.getProvider() == null) {
            return 1;
        }
        if (p2.getProvider() == null) {
            return -1;
        }
        
        int diff = p1.getProvider().compareTo(p2.getProvider());

        // if providers are the same, return in alphabetical order
        return (diff != 0) ? diff : p1.getName().compareTo(p2.getName());
    }
}
