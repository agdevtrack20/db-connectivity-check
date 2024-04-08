package IOD;

import java.io.Console;

/**
 * Application to check Database Connectivity
 *
 */

public class App {
    public static void main(String[] args) {
        Console cnsl = System.console();
        if (cnsl == null) {
            System.err.print("ERROR: Application Console Not Available (Missing JAVA IO Package). Exiting...");
            System.exit(1);
        }
 
        System.out.println("===============================================================");
        System.out.println("\u001B[36m"
                + "***** Welcome to Database Connectivity check application!*****" + "\u001B[0m");
        System.out.println("===============================================================");
        System.out.println("\u001B[36m"
                + "HELP: " + "\u001B[0m" + "To Perform connectivity check to Database you need to provide:"
                + "\n1. Connection String \n2. Username \n3. Password\n");

        String confirmation = cnsl.readLine("Are you sure you want to continue(yes/no):");
        if (confirmation.equalsIgnoreCase("yes")) {
            String connectionString = cnsl.readLine("\u001B[35m" + "Enter Connection String:" + "\u001B[0m");
            String username = cnsl.readLine("\u001B[35m" + "Enter Username:" + "\u001B[0m");
            String pwd = String.valueOf(cnsl.readPassword("\u001B[35m" + "Enter Password:" + "\u001B[0m"));

            CheckDbConnectivity checkDbConnectivity = new CheckDbConnectivity();
            checkDbConnectivity.check(connectionString, username, pwd);
        } else {
            System.out.println("Thank you! Exiting...");
            System.exit(0);
        }

    }

}
