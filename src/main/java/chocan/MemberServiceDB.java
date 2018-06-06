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

    //Loads a member's service data from a text file into LLL by file name (not including file extension)
    //File names should be a member's membership number
    public void load(int id){
       try {
           if(head != null)
               head = null;

           fileName = Integer.toString(id);

           file = new File("src/main/java/chocan/db/Members/" + fileName + ".txt");
           Scanner readFromFile = new Scanner(file);
           readFromFile.useDelimiter("[|\\n]"); //ignore colon and new line

           String date, providerName, service, ignore;

           ignore = readFromFile.nextLine();

           while(readFromFile.hasNext()) {
               date = readFromFile.next();
               providerName = readFromFile.next();
               service = readFromFile.next();
               this.head = addServiceRecord(this.head, date, providerName, service);
           }

           readFromFile.close();

       }
       catch (FileNotFoundException error){
           error.printStackTrace();
       }
    }
    

    //Adds service to a members service record through user input
    //Also acts as a wrapper for recursive method if LLL exists
    public void addServiceRecord(String serviceDate, String providerName, String serviceName){

        head = addServiceRecord(head, serviceDate, providerName, serviceName);

    }

    //Recursively adds a service to the members records
    private MemberService addServiceRecord(MemberService current, String serviceDate, String providerName, String serviceName){
       if(current == null){
           return current = new MemberService( serviceDate, providerName, serviceName);

       }
       else{
           current.setNext(addServiceRecord(current.goNext(),serviceDate,providerName,serviceName));
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

           try {
               File file = new File("src/main/java/chocan/db/Members/" + fileName + ".txt");
               file.getParentFile().mkdirs();
               if (!file.exists()) file.createNewFile();

               FileWriter fw = new FileWriter(file,true);
               BufferedWriter bw = new BufferedWriter(fw);
               PrintWriter write = new PrintWriter(bw);
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


    //Opens the text file and checks for exceptions.
    public void openFile() {
        try {
            read = new Scanner(new File("src/main/java/chocan/db/provider directory.txt"));
        } catch (Exception e) {
            System.out.println("Can't find file");
            e.printStackTrace();
        }
    }

    //Reads in from text file and calls the insert function to build the tree.
    public void readFile() {
        this.openFile();
        read.useDelimiter("#|\\n");

        String date, providerName, service;
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
        serviceList.readFile();
        serviceList.display();
        //serviceList.addServiceRecord();
        //serviceList.addServiceRecord();

        //serviceList.remove();

        serviceList.save();

        serviceList.display();
    }
}
