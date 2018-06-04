package chocan;

import chocan.report.ReportGenerator;
import chocan.ui.menus.MainMenu;

import java.util.Scanner;

public class Main {

    /**
     * The entry point of the application.
     * @param args Unused
     */
    public static void main(final String[] args) {
        // TODO Need to implement and initialize report generator here
        final ReportGenerator reportGenerator = null;
        // Start the user interface
        final MainMenu mainMenu = new MainMenu(() -> {
            // TODO Need to initialize member database here
            return null;
        }, () -> {
            // TODO Need to initialize provider database here
            return null;
        }, () -> {
            // TODO Need to initialize manager database here
            return null;
        }, () -> {
            // TODO Need to initialize provider service directory here
            return null;
        }, () -> {
            // TODO Need to initialize service record database here
            return null;
        }, reportGenerator);
        try (final Scanner stdin = new Scanner(System.in)) {
            // Display welcome message
            System.out.println("[Chocoholics Anonymous] Welcome!");
            // Run main menu
            mainMenu.run(stdin);
        }

        //test
       /* Member test = new Member();
        test.EditInfo();*/

        ProviderDir tree = new ProviderDir();

        //tree.Add();//Testing, This prompts user for input to build the provide dir tree.
        //tree.Add();
        //tree.Add();
        tree.Read_txt();//Testing, this reads from the provider dir text file and builds directory tree
        tree.Display();//Test, this displays the provider directory tree alphabetically by name.
        int code = 0;
        tree.Find_code(code);//Test, looks up Service Info record by service code.









    }

}