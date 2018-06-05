package chocan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.System.out;

public class MemberServiceDB {
    private MemberService head;
    private Member memberInfo;

   MemberServiceDB(){
        this.head = null;
        memberInfo = null;
    }

    //will load a members service record to create a BST pf services provided to that member
    //will load this information from a text file
    public void Load(){
       try {

           Scanner input = new Scanner(System.in);
           out.println("Enter Member number to access service records: ");
           String fileName = input.nextLine();
           File file = new File("/src/main/java/chocan/db/members/" + fileName + ".txt");
           Scanner read = new Scanner(file);
       }
       catch (FileNotFoundException error){
           error.printStackTrace();
       }
    }

    //will allow a service to be added to a members service record
    public boolean addServiceRecord(){
        if(head == null){
            head = new MemberService();
            Scanner read = new Scanner(System.in);
            System.out.println("Enter the name of the service: ");
            String service = read.nextLine();
            System.out.println("Enter the name of the Provider: ");
            String provName = read.nextLine();
            head.addService(service, provName);
            return true;
        }
        else{
            this.head.setNext(addServiceRecord(head.goNext()));
            return true;
        }
    }

    //recursivly adds a service to the members records
    private MemberService addServiceRecord(MemberService current){
       if(current == null){
           current = new MemberService();
           Scanner read = new Scanner(System.in);
           System.out.println("Enter the name of the service: ");
           String service = read.nextLine();
           System.out.println("Enter the name of the Provider: ");
           String provName = read.nextLine();
           current.addService(service, provName);
           return current;
       }
       else{
           current.setNext(addServiceRecord(current.goNext()));
           return current;
       }
    }

    //dissplays the entire list of services for the loaded member, wrapper
    public void display()
    {
        if(head == null)
            return;
        head.display();
        display(head.goNext());
        return;
    }

    //recursivley displays the entire list of services for the loaded member
    private void display(MemberService current){
       if(current == null)
           return;
       current.display();
       display(current.goNext());
    }

    //This wrapper is used to search for a service a member received on a specific date.
    public void find(){
       if(head == null)
           return;
       else{
           out.println("Enter the date of the service you are looking for(MM-dd-yyyy): ");
           Scanner read = new Scanner(System.in);
           String date = read.nextLine();
           boolean test = head.compareDate(date);
           if(test)
               head.display();
           find(head.goNext(), date);
       }
    }

    private void find(MemberService current, String date){
        if(current == null)
            return;
        else{
            //out.println("Enter the date of the service you are looking for(MM-dd-yyyy): ");
            //Scanner read = new Scanner(System.in);
            //String date = read.nextLine();
            boolean test = current.compareDate(date);
            if(test)
                current.display();
            find(current.goNext(), date);
        }
    }

   public void remove(){
       if(head == null)
           return;
       out.println("Enter the date of the service you would like to rmeove(MM-dd-yyyy): ");
       Scanner read = new Scanner(System.in);
       String date = read.nextLine();
       boolean test = head.compareDate(date);
       if(test){
           head.display();
           out.println("Is this the service you would like to remove(y/n): ");
           String answer = read.nextLine();
           boolean check1 = answer.equals('y');
           boolean check2 = answer.equals('Y');
           if(check1 || check2){
               MemberService temp = head.goNext();

           }
       }
   }

    //used to test the classes
    public static void main(String[] args)
    {
        MemberServiceDB serviceList = new MemberServiceDB();
        serviceList.addServiceRecord();
        serviceList.addServiceRecord();

        serviceList.display();

        serviceList.find();

        serviceList.display();
    }
}
