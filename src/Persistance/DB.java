/**@Text description of the whole class/ overall purpose
 *@author
 *@since /deliver date 2010-10-10
 *@version
 *@deprecated (outdated)
 *@see
 */

package Persistance;

import Domain.Customer;
import Domain.DeliveryPoint;
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
    public int numberOfColumns;

    public ArrayList<Object> objectArrayListOrder = new ArrayList<>();
    public ArrayList<Object> objectArrayListCustomer = new ArrayList<>();
    public ArrayList<Object> objectArrayListEventHistory = new ArrayList<>();
    public ArrayList<Object> objectArrayListLaundryItem = new ArrayList<>();
    // TODO continue for all tables of which we need the data.

    /**
     * The method calls the getDataFromDB method for every table of which we need the data.
     */
    public void tabledataToFetch() {

        getDataFromDB("SELECT * FROM tblOrder", objectArrayListOrder);
        getDataFromDB("SELECT * FROM tblCustomer", objectArrayListCustomer);
        getDataFromDB("SELECT * FROM tblEventHistory", objectArrayListEventHistory);
        getDataFromDB("SELECT * FROM tblLaundryItem", objectArrayListLaundryItem);
        // TODO continue for all tables of which we need the data.

        // only print out checks
        System.out.println("Arraylist of order data objects: " + objectArrayListOrder);
        System.out.println("Arraylist of customer data objects: " + objectArrayListCustomer);
        System.out.println("Arraylist of eventHistory data objects: " + objectArrayListEventHistory);
        System.out.println("Arraylist of laundryItem data objects: " + objectArrayListLaundryItem);
    }

    /**
     * This method will establish a connection wit the database called "DryLeaningServiceDB".
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
     * This method checks which kind of datatype the chosen table holds and stores the data as objects in an arraylist.
     * As it is unknown beforehand which data typ the table holds, the data fetched is stored in an arraylist of objects,
     * so that there can be different data types in the array list.
     * @param sqlString the SQL statement which chooses the table from which the data shall be fetched.
     * @param arrayList the arrayList in which the data shall be stored.
     */
    public void getDataFromDB(String sqlString, ArrayList<Object> arrayList) {

        Statement st;
        ResultSet resultSet;

        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery(sqlString);

            // as the number of columns is unknown, it will get the amount of columns by the metadata and loop over all columns
            ResultSetMetaData rsmd = resultSet.getMetaData();
            numberOfColumns = rsmd.getColumnCount();

            while (resultSet.next()) {

                // As long as num is smaller than number of columns, for every column in the table do:
                // 1) print the name of the datatype
                // 2)  check which datatype it is and get the corresponding object
                // 3) add the new object to the temporary arraylist
                for (int num = 1; num <= (numberOfColumns); num++) {

                    // print out check : what kind of data type name gets returned.
                    String checkColumnTypeName = rsmd.getColumnTypeName(num);
                    System.out.println("Column datatype: " + checkColumnTypeName);

                    if(rsmd.getColumnTypeName(num).equals("int")) {
                        int newInt = resultSet.getInt(num);
                        arrayList.add(newInt);
                    } else if (rsmd.getColumnTypeName(num).equals("varchar")) { //getColumnTypeName returns a String value representing the name of the SQL data type. https://www.tutorialspoint.com/java-resultsetmetadata-getcolumntypename-method-with-example
                        String newString = resultSet.getString(num);
                        arrayList.add(newString);
                    } else if (rsmd.getColumnTypeName(num).equals("datetime")) {
                        Timestamp newTimeStamp = resultSet.getTimestamp(num); // returns a TimeStamp object https://docs.microsoft.com/de-de/sql/connect/jdbc/reference/gettimestamp-method-java-lang-string-java-util-calendar?view=sql-server-ver15
                        arrayList.add(newTimeStamp);
                    } else if (rsmd.getColumnTypeName(num).equals("bit")){
                        boolean newBoolean = resultSet.getBoolean(num);
                        arrayList.add(newBoolean);
                    } else {System.out.print("No matching datatype");
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

    public ArrayList<DeliveryPoint> getDeliveryPointsFromDB(){
        ArrayList<DeliveryPoint> deliveryPoints = new ArrayList<>();
        deliveryPoints.add(new DeliveryPoint(1,"Kirkevej"));
        deliveryPoints.add(new DeliveryPoint(2,"SuperBrugsen"));
        return deliveryPoints;
    }

}
