/**@Text description of the whole class/ overall purpose
 *@author
 *@since /deliver date 2010-10-10
 *@version
 *@deprecated (outdated)
 *@see
 */

package Persistance;

import Domain.*;
import Domain.LaundryType.LaundryType;

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

    // a method for each table to get the data from the database
    public ArrayList<Customer> getCustomersFromDB(){
        ArrayList<Customer> customers = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblCustomer");
            while(resultSet.next()) {
                customers.add(new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5))); //insert SQL statement here ??
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {}

        return customers;
    }

    public ArrayList<Order> getOrdersFromDB(){
        ArrayList<Order> orders = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblOrder");
            while(resultSet.next()) {
                orders.add(new Order(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {}

        return orders;
    }

    public ArrayList<LaundryItem> getLaundryItemsFromDB() {
        ArrayList<LaundryItem> laundryItems = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblLaundryItem");
            while(resultSet.next()) {
                laundryItems.add(new LaundryItem(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getBoolean(4)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {}

        return laundryItems;
    }

    public ArrayList<DeliveryPoint> getDeliveryPointsFromDB() {
        ArrayList<DeliveryPoint> deliveryPoints = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblDeliveryPoint");
            while(resultSet.next()) {
                deliveryPoints.add(new DeliveryPoint(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {}

        return deliveryPoints;
    }

    public ArrayList<Department> getDepartmentsFromDB() {
        ArrayList<Department> departments = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblDepartment");
            while(resultSet.next()) {
                departments.add(new Department(resultSet.getInt(1), resultSet.getString(2)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {}

        return departments;
    }

    public ArrayList<EventHistory> getEventHistoriesFromDB() {
        ArrayList<EventHistory> eventHistories = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblEventHistory");
            while(resultSet.next()) {
                eventHistories.add(new EventHistory(resultSet.getInt(1), resultSet.getInt(2), resultSet.getTimestamp(3), resultSet.getInt(4), resultSet.getInt(5)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {}

        return eventHistories;
    }

    public ArrayList<EventType> getEventTypesFromDB() {
        ArrayList<EventType> eventTypes = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblEventType");
            while(resultSet.next()) {
                eventTypes.add(new EventType(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {}

        return eventTypes;
    }

    // TODO
//    public ArrayList<LaundryType> getLaundryTypesFromDB() {
//        ArrayList<LaundryType> laundryTypes = new ArrayList<>();
//        Statement st;
//        ResultSet resultSet;
//        try {
//            establishConnection();
//            st = connection.createStatement();
//            resultSet = st.executeQuery("SELECT * FROM tblLaundryType");
//            while(resultSet.next()) {
//                laundryTypes.add(new LaundryType(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4)));
//            }
//            st.close();
//            closeConnection();
//
//        } catch (SQLException e) {}
//
//        return laundryTypes;
//    }

    public ArrayList<Payment> getPaymentsFromDB() {
        ArrayList<Payment> payments = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblPayment");
            while(resultSet.next()) {
                payments.add(new Payment(resultSet.getInt(1), resultSet.getInt(2), resultSet.getTimestamp(3), resultSet.getBoolean(4), resultSet.getInt(5)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {}

        return payments;
    }

}
