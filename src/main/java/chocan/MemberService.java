package chocan;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberService {
    private String providerName;
    private String service;
    private String ServiceDate;
    private MemberService next;

    MemberService()
    {
        providerName = null;
        service = null;
        next = null;
        ServiceDate = null;
    }
    // Save provider data.
    public void save(PrintWriter write) {

        write.print(this.ServiceDate);
        write.print(":");
        write.print(this.providerName);
        write.print(":");
        write.println(this.service);
    }

    public void delete(){
        providerName = null;
        service = null;
        next = null;
        ServiceDate = null;
    }

    public MemberService goNext(){
        return next;
    }

    public void setNext(MemberService next){
        this.next = next;
    }

    //used when Loading from a file fro mthe first time
    MemberService(String date, String ProvName, String servName){
        ServiceDate = date;
        providerName = ProvName;
        service = servName;
    }

    //this add is used when a user is manually entering a service record. date is done for them
    public void addService(String servName, String provName){
        service = servName;
        providerName = provName;
        ServiceDate = getDate();
    }

    //used to display when the members name needs to be displayed
    public void display(){
        System.out.println("Service Received: " + service);
        System.out.println("Date of service: " + ServiceDate);
        System.out.println("Provider name: " + providerName);
    }

    public boolean compareDate(String toCompare){
        return ServiceDate.equals(toCompare);
    }

    private String getDate(){
        String date = null;
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
        Date currentDate = new Date();
        return date = formatDate.format(currentDate);
    }

}
