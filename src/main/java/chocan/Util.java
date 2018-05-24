package chocan;

import java.util.Scanner;
//Supports the Scanner function, will be used by other classes through inheritance.
public class Util {
    protected Scanner input;
    public Util(){
        input = new Scanner(System.in);
    }

}
