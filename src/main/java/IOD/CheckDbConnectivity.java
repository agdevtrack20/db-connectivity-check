package IOD;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckDbConnectivity {

    Console cnsl = System.console();

    public void check(String connectionString, String username, String pwd) {
        Connection connection = getConnection(connectionString, username, pwd);
        if (connection == null) {
            System.out.println(
                    "\u001B[36m"
                            + "CONNECTION FAILED: Sorry! Could not make Successful connection to Database. Exiting..."
                            + "\u001B[0m");
            System.exit(1);
        }

        System.out.println("\u001B[32m" + "STATUS: CONNECTION SUCCEEDED\n" + "\u001B[0m");
        System.out.println("\u001B[36m" + "INFO: " + "\u001B[0m" + "Executing SQL Query operation:");
        runQuery(connection);

        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error while closing connection, might cause vulnerability");
            e.printStackTrace();
        }

        System.out.println("\u001B[36m"
                + "\nThank You for using this application! (run application again to perform another check) Exiting..."
                + "\u001B[0m");
        System.exit(0);
    }

    private Connection getConnection(String connectionString, String username, String pwd) {
        try {
            String url = "jdbc:oracle:thin:@" + connectionString;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println(
                    "\u001B[36m" + "\nINFO: " + "\u001B[0m" + "Trying to connect to Database...");
            Connection connection = DriverManager.getConnection(url, username, pwd);

            return connection;
        } catch (SQLException e) {
            System.err
                    .println("\u001B[31m"
                            + "ERROR: Error occured while making connection to Database\nSee the below stack trace:"
                            + "\u001B[0m");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private void runQuery(Connection connection) {
        String sqlQuery = "SELECT SYS_CONTEXT ('USERENV', 'CDB_NAME') CDB_NAME, SYS_CONTEXT ('USERENV', 'DB_DOMAIN') DB_DOMAIN,SYS_CONTEXT ('USERENV', 'CON_NAME') CON_NAME, SYS_CONTEXT ('USERENV', 'SESSION_USER') SESSION_USER, SYS_CONTEXT ('USERENV', 'HOST') HOST FROM DUAL";
        System.out.println("\u001B[36m" + "SQL Query:" + "\u001B[0m" + sqlQuery + "\n");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            presentResultset(resultSet, sqlQuery);
        } catch (SQLException e) {
            System.err
                    .println("\u001B[31m"
                            + "ERROR: Error occured while Performing SQL Query operation\nSee the below stack trace:"
                            + "\u001B[0m");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void presentResultset(ResultSet resultSet, String query) throws SQLException {
        System.out.println("\u001B[36m" + "Result:" + "\u001B[0m");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%27s", "+==========================");
        }
        System.out.println();

        System.out.print("|");
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%27s", metaData.getColumnName(i) + "  |");
        }
        System.out.println();
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%27s", "+==========================");
        }
        System.out.println();

        while (resultSet.next()) {
            System.out.print("|");
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%27s", resultSet.getObject(i) + "  |");
            }
            System.out.println();
        }

        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%27s", "+--------------------------");
        }
        System.out.println();

    }

}
