package chocan;

//import javafx.util.converter.DateStringConverter;

import java.io.PrintWriter;
import static java.lang.System.out;


public class MemberService {

    private String providerName;
    private String service;
    private String serviceDate;
    private MemberService next;

    //Constructor
    MemberService() {
        this.providerName = null;
        this.service = null;
        this.next = null;
        this.serviceDate = null;
    }

    //Save member's service data to file
    public void save(PrintWriter write) {

        write.print(this.serviceDate);
        write.print("|");
        write.print(this.providerName);
        write.print("|");
        write.println(this.service);

    }

    public void delete() {
        providerName = null;
        service = null;
        next = null;
        serviceDate = null;
    }

    public MemberService goNext() {
        return next;
    }

    public void setNext(MemberService next) {
        this.next = next;
    }

    //Used when loading from a file for the first time
    MemberService(String date, String ProvName, String serviceName) {
        this.serviceDate = date;
        this.providerName = ProvName;
        this.service = serviceName;
    }

    //Adds service info manually for a member.
    public void addService(String serviceDate, String providerName, String serviceName) {
        this.service = serviceName;
        this.providerName = providerName;
        this.serviceDate = serviceDate;

    }

    //Displays when member's name is displayed
    public void display() {
        out.println("Date of Service: " + serviceDate);
        out.println("Service Received: " + service);
        out.println("Provider: " + providerName + '\n');
    }

    public int compareDate(String toCompare)
    {
        return serviceDate.compareToIgnoreCase(toCompare);
    }
}
