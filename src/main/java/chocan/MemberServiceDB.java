package chocan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import static java.lang.System.out;

public class MemberServiceDB {

    private MemberService head;
    private File file;
    private String fileName;
    private Scanner read;

    //Constructor
    MemberServiceDB(){
        this.head = null;
        file = null;
    }

    //Loads a member's service data from a text file into LLL by file name (not including file extension)
    //File names should be a member's membership number
    public void load(){
       try {
           if(head != null)
               head = null;

           read = new Scanner(System.in);
           out.println("Enter a member number to access service records: ");
           fileName = read.nextLine();
           file = new File("src/main/java/chocan/db/Members/" + fileName + ".txt");
           Scanner readFromFile = new Scanner(file);
           readFromFile.useDelimiter("[:\\n]"); //ignore colon and new line

           String date, providerName, service;

           while(readFromFile.hasNext()) {
               date = readFromFile.next();
               providerName = readFromFile.next();
               service = readFromFile.next();
               this.head = addFromFile(this.head, date, providerName, service);
           }
       }
       catch (FileNotFoundException error){
           error.printStackTrace();
       }
    }

    //Adds member service information from a file to LLL
    public MemberService addFromFile(MemberService current,String date, String provName, String serviceName){
       if(current == null){
           current = new MemberService(date, provName, serviceName);
           return current;
       }
       else{
           current.setNext(addFromFile(current.goNext(), date, provName, serviceName));
           return current;
       }
    }

    //Adds service to a members service record through user input
    //Also acts as a wrapper for recursive method if LLL exists
    public boolean addServiceRecord(){
        if(head == null){
            head = new MemberService();
            read = new Scanner(System.in);
            out.println("Enter the name of the service: ");
            String service = read.nextLine();
            out.println("Enter the name of the Provider: ");
            String provName = read.nextLine();
            head.addService(service, provName);
            return true;
        }
        else{
            this.head.setNext(addServiceRecord(head.goNext()));
            return true;
        }
    }

    //Recursively adds a service to the members records
    private MemberService addServiceRecord(MemberService current){
       if(current == null){
           current = new MemberService();
           read = new Scanner(System.in);
           out.println("Enter the following information for the member's new service:");
           out.println("Service name: ");
           String service = read.nextLine();
           out.println("Provider name: ");
           String provName = read.nextLine();
           current.addService(service, provName);
           return current;
       }
       else{
           current.setNext(addServiceRecord(current.goNext()));
           return current;
       }
    }

    //Displays LLL of services for the loaded member - wrapper
    public void display()
    {
        if(head == null)
            out.println("Your file is empty. Nothing to display.");

        else {
            head.display();
            display(head.goNext());
        }
    }

    //Recursively displays the entire list of services for the loaded member
    private void display(MemberService current){
       if(current == null)
           return;
       current.display();
       display(current.goNext());
    }

    //Search for a service a member received on a specific date - wrapper
    public void find(){
       if(head == null)
           out.println("Your file is empty. Nothing to search.");

       else{
           out.println("Enter the date of the service you are looking for (MM-dd-yyyy): ");
           read = new Scanner(System.in);
           String date = read.nextLine();
           boolean test = head.compareDate(date);
           if(test)
               head.display();
           find(head.goNext(), date);
       }
    }

    //Recursive search for a service received by member using the date
    private void find(MemberService current, String date){
        if(current == null)
            out.println("No matches found.");
        else{
            if(current.compareDate(date))
                current.display();
            find(current.goNext(), date);
        }
    }

    //Remove a service a member received on a specific date - wrapper
    public void remove(){
        if(head == null)
            return;
        out.println("Enter the date of the service you would like to remove (MM-dd-yyyy): ");
        read = new Scanner(System.in);
        String date = read.nextLine();
        remove(head, date);
    }

    //Recursively remove a service received by a member using its date
    private MemberService remove(MemberService current, String date){
        if(current == null)
            return current;

        if(current.compareDate(date)){
            current.display();
            out.println("Is this the service you would like to remove(y/n): ");
            read = new Scanner(System.in);
            String answer = read.nextLine();
            if(answer.equalsIgnoreCase("y")){
                MemberService temp = current.goNext();
                current.delete();
                return temp;
            }
            else {
                current.setNext(remove(current.goNext(), date));
                return current;
            }
        }
        else {
            current.setNext(remove(current.goNext(), date));
            return current;
        }
    }

    //Save LLL to member's service record file - wrapper
    public void save(){
           File file = new File("src/main/java/chocan/db/Members/" + fileName + ".txt");
           file.getParentFile().mkdirs();
           PrintWriter write = null;

           try {
               write = new PrintWriter(file);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }

           save(this.head, write);
           out.println("Member service list has been saved.");
           write.close();

   }

    //Recursively save LLL to member's service record file
    private void save(MemberService head, PrintWriter write) {

        if (head == null)
            return;

        head.save(write);
        save(head.goNext(), write);
    }

    //Clears out data in head
    public void reset(){
        head.delete();
    }

    //Test class methods
    public static void main(String[] args)
    {
        MemberServiceDB serviceList = new MemberServiceDB();
        serviceList.load();
        serviceList.display();
        //serviceList.addServiceRecord();
        serviceList.addServiceRecord();

        serviceList.remove();

        serviceList.save();

        serviceList.display();
    }
}
