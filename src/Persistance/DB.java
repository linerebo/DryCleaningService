/**@Text description of the whole class/ overall purpose
 *@author
 *@since /deliver date 2010-10-10
 *@version
 *@deprecated (outdated)
 *@see
 */

package Persistance;

import Domain.Customer;
import Domain.Order;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DB {

    public Connection connection;

    /**
     * This method will establish a connection wit the database called DryLeaningServiceDB.
     * @return an object of connection
     */
    public Connection establishConnection(){

        //get access information for the DB from a separate file called DB_Properties
        Properties prop = new Properties();
        String file = "src/Persistance/DB_Properties";
        InputStream input;

        try{
            //read information from file
            input = new FileInputStream(file);
            prop.load(input);
            String port = prop.getProperty("port");
            String databaseName = prop.getProperty("databaseName");
            String userName = prop.getProperty("userName");
            String password = prop.getProperty("password");

            // load the driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            //establish connection
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:" + port + ";databaseName=" + databaseName, userName, password);
            System.out.println("The connection to the database is established."); // test connection, works

            return connection;

        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Connection not established: " + e);
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method will close the connection to the database.
     */
    public void closeConnection() {
        try {
            connection.close();
            System.out.println("The connection to the database is now closed.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The method takes an sql String and prints the corresponding resultset in a formatted way to the console.
     * @param sqlString a String of a sql statement
     */
    public void getDataFromDB(String sqlString) {

        Statement st;
        ResultSet resultSet;

        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery(sqlString);

            // as the number of columns is unknown, it will get the amount of columns by the metadata and loop over all columns
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            System.out.println("Result from DB: ");
            while (resultSet.next()) {

                for (int num = 1; num <= numberOfColumns; num++) {

                    System.out.print(resultSet.getString(num)); //other way, here  you need to know the indexNumber of the columns: System.out.println("result from DB: "+ resultSet.getString(1));
                    System.out.print(" | ");

                    if (num == numberOfColumns) {
                        System.out.println("");
                    }
                }
            }

            st.close();
            closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Customer> getCustomersFromDB(){
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1,"Bo", "Jensen", "12345678", "bj@mail.dk")); //insert SQL statement here
        customers.add(new Customer(2,"Ib", "Jensen", "12345679", "ij@mail.dk"));
        return customers;
    }

    public ArrayList<Order> getOrdersFromDB(){
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order(1, 3));
        orders.add(new Order(2, 5));    //insert SQL statement here
        return orders;
    }

}
