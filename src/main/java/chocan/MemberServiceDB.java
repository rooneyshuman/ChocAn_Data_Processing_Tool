package chocan;

import java.io.*;
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

    //Adds service to a members service record through user input
    //Also acts as a wrapper for recursive method if LLL exists
    public void addServiceRecord(String serviceDate, String providerName, String serviceName){

        head = addServiceRecord(head, serviceDate, providerName, serviceName);

    }

    //Recursively adds a service to the members records
    private MemberService addServiceRecord(MemberService current, String serviceDate, String providerName, String serviceName){
       if(current == null){
           return new MemberService( serviceDate, providerName, serviceName);}

           else if(current.compareDate(serviceDate) > 0){
           MemberService temp = new MemberService(serviceDate, providerName, serviceName);
           temp.setNext(current);
           return temp;}

        else {
           current.setNext(addServiceRecord(current.goNext(), serviceDate, providerName, serviceName));
           return current;
       }

    }

    //Displays LLL of services for the loaded member - wrapper
    public void display()
    {
        if(head == null){
            out.println("Your file is empty. Nothing to display.");
            return; }

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
           if(head.compareDate(date) == 0)
               head.display();
           find(head.goNext(), date);
       }
    }

    //Recursive search for a service received by member using the date
    private void find(MemberService current, String date){
        if(current == null)
            out.println("No matches found.");
        else{
            if(current.compareDate(date) == 0)
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

        if(current.compareDate(date) == 0){
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
    public void save(String name, int id, String address, String city, String state, String zip){

           try {
               File file = new File("src/main/java/chocan/db/Members/" + fileName + ".txt");
               file.getParentFile().mkdirs();
               if (!file.exists()) file.createNewFile();

               PrintWriter write = new PrintWriter(file);

               // Write first line.
               write.print(name + "|");
               write.print(id + "|");
               write.print(address + "|");
               write.print(city + "|");
               write.print(state + "|");
               write.println(zip);

               save(this.head, write);
               out.println("Member service list has been saved.");
               write.close();

           } catch (IOException e) {
               e.printStackTrace();
           }
   }

    //Recursively save LLL to member's service record file
    private void save(MemberService head, PrintWriter write) {

        if (head == null)
            return;

        head.save(write);
        save(head.goNext(), write);
    }

    //Opens the text file and checks for exceptions
    public void openFile() {
        try {
            File file = new File("src/main/java/chocan/db/Members/" + fileName + ".txt");
            file.getParentFile().mkdir();

            if (!file.exists())
                return;

            read = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Can't find file");
            e.printStackTrace();
        }
    }

    //Reads in from text file and calls the insert function to build the tree
    public void readFile(String fileName) {
        this.fileName = fileName;

        try {
            File file = new File("src/main/java/chocan/db/Members/" + fileName + ".txt");
            file.getParentFile().mkdir();

            if (!file.exists())
                return;

            read = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Can't find file");
            e.printStackTrace();
        }

        read.useDelimiter("[|\\n]");

        String date, providerName, service;

        read.nextLine(); // This will ignore the first line of the file.

        while (read.hasNext()) {
            date = read.next();
            providerName = read.next();
            service = read.next();
            addServiceRecord(date, providerName, service);
        }

        read.close();
    }

    //Clears out data in head
    public void reset(){
        head.delete();
    }

    //Test class methods
    public static void main(String[] args)
    {
        MemberServiceDB serviceList = new MemberServiceDB();
        //serviceList.load();
        serviceList.readFile("test");
        serviceList.display();
        //serviceList.addServiceRecord();
        //serviceList.addServiceRecord();

        //serviceList.remove();

        //serviceList.save();

        //serviceList.display();
    }
}
