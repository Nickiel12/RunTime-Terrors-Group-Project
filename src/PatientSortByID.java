import java.util.Comparator;

public class PatientSortByID implements Comparator<Patient> {
    public int compare(Patient p1, Patient p2){return p1.getIdNumber()-p2.getIdNumber();}
}
