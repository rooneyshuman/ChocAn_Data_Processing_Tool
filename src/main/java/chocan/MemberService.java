package chocan;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberService {
    private String memberName;
    private String providerName;
    private String service;
    private String ServiceDate;
    private MemberService next;
    //String today = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());


    MemberService()
    {
        memberName = null;
        providerName = null;
        service = null;
        next = null;
        ServiceDate = null;
    }

    public void delete(){
        memberName =null;
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

    public int addService(String servName, String provName){
        service = servName;
        providerName = provName;
        ServiceDate = getDate();
        return 1;
    }

    public void display(){
        System.out.println("Member Name: " + memberName);
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
