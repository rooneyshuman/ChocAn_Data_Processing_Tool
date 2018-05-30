package chocan;

public class MemberService {
    private String date;
    private String provider;
    private String service;
    private MemberService left;
    private MemberService right;

    MemberService()
    {
        date = null;
        provider = null;
        service = null;
        left = null;
        right = null;
    }

    public MemberService goLeft() {
        return left;
    }

    public MemberService goRight() {
        return right;
    }

    public void setLeft(MemberService left) {
        this.left = left;
    }

    public void setRight(MemberService right) {
        this.right = right;
    }

    public void display(){
        System.out.println("Date: " + date);
        System.out.println("Provider: " + provider);
        System.out.println("Service: " + service);
    }

    public int compareDate(String newDate){
        //returns 99 if there is no date.
        if(date == null)
            return 99;
        //compareTo is a String class method that compares the string against each other.
        int check = 0;
        //returns 0 if same, return negative number if newDate is before date, returns pos number if newDate is after date.
        return check = newDate.compareTo(date);
    }
}
