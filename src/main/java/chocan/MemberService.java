package chocan;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.lang.System.out;

public class MemberService {

    private String providerName;
    private String service;
    private String serviceDate;
    private MemberService next;

    //Constructor
    MemberService()
    {
        providerName = null;
        service = null;
        next = null;
        serviceDate = null;
    }

    //Save member's service data to file
    public void save(PrintWriter write) {

        write.print(this.serviceDate);
        write.print(":");
        write.print(this.providerName);
        write.print(":");
        write.println(this.service);
    }

    public void delete(){
        providerName = null;
        service = null;
        next = null;
        serviceDate = null;
    }

    public MemberService goNext(){
        return next;
    }

    public void setNext(MemberService next){
        this.next = next;
    }

    //Used when loading from a file for the first time
    MemberService(String date, String ProvName, String servName){
        serviceDate = date;
        providerName = ProvName;
        service = servName;
    }

    //Adds service info manually for a member. Service date is automatically generated
    public void addService(String servName, String provName){
        service = servName;
        providerName = provName;
        serviceDate = getDate();
    }

    //Displays when member's name is displayed
    public void display(){
        out.println("Date of Service: " + serviceDate);
        out.println("Service Received: " + service);
        out.println("Provider: " + providerName + '\n');
    }

    public boolean compareDate(String toCompare){
        return serviceDate.equals(toCompare);
    }

    //Returns current date formatted
    private String getDate(){
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
        Date currentDate = new Date();
        return formatDate.format(currentDate);
    }

}
