/**@Text description of the whole class/ overall purpose
 *@author
 *@since /deliver date 2010-10-10
 *@version
 *@deprecated (outdated)
 *@see
 */

package Persistance;

import Domain.*;
import Domain.LaundryItemTypes.*;
import Domain.Customer;
import Domain.DeliveryPoint;
import Domain.Order;
import Domain.SystemUser.SystemUser;

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
                Order newOrder = new Order(
                        resultSet.getInt(1),
                        Adapter.cleaningCentralInstance().getCustomerFromID(resultSet.getInt(2)),
                        Adapter.cleaningCentralInstance().getDeliveryPointFromID(resultSet.getInt(4))
                        );
                orders.add(newOrder);
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
                //laundryItems.add(new LaundryItem(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getBoolean(4)));
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
                eventHistories.add(new EventHistory(resultSet.getInt(1), resultSet.getInt(2), resultSet.getTimestamp(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getBoolean(6)));
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

    public void getLaundryTypesFromDB() {
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblLaundryType");
            while(resultSet.next()) {
                //laundryTypes.add(new LaundryType(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4)));
                if(resultSet.getString(2).equals("Shirt")){
                    Shirt.itemPrice = resultSet.getInt(3);
                    Shirt.itemTimeToClean = resultSet.getInt(4);
                }
                else if(resultSet.getString(2).equals("Blazer")){
                    Blazer.itemPrice = resultSet.getInt(3);
                    Blazer.itemTimeToClean = resultSet.getInt(4);
                }
                else if(resultSet.getString(2).equals("Coat")){
                    Coat.itemPrice = resultSet.getInt(3);
                    Coat.itemTimeToClean = resultSet.getInt(4);
                }
                else if(resultSet.getString(2).equals("Tshirt")){
                    Tshirt.itemPrice = resultSet.getInt(3);
                    Tshirt.itemTimeToClean = resultSet.getInt(4);
                }
                else if(resultSet.getString(2).equals("Dress")){
                    Dress.itemPrice = resultSet.getInt(3);
                    Dress.itemTimeToClean = resultSet.getInt(4);
                }
                else if(resultSet.getString(2).equals("Trousers")){
                    Trousers.itemPrice = resultSet.getInt(3);
                    Trousers.itemTimeToClean = resultSet.getInt(4);
                }
                else if(resultSet.getString(2).equals("Carpet")){
                    Carpet.squareMeterPrice = resultSet.getInt(3);
                    Carpet.itemTimeToClean = resultSet.getInt(4);
                }
            }
            st.close();
           closeConnection();

        } catch (SQLException e) {}
    }

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

    /**
     * The method will get all data from the table tblSystem user in the database except from the passwords.
     * @return an array list of system user objects.
     */
    public ArrayList<SystemUser> getSystemUsersFromDB() {
        ArrayList<SystemUser> systemUsers = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT fldSystemUserID, fldSystemUserFirstName, fldSystemUserLastName, fldDepartmentID FROM tblSystemUser");
            while(resultSet.next()) {
                systemUsers.add(new SystemUser(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {}

        return systemUsers;
    }

    // methods to fetch data, which are not loaded in at program start, directly from the database.

    /**
     * The method takes in an user ID and returns the corresponding password from the table of system users.
     * @param inputUserID int
     * @return the password of the given system user as a String
     */
    public String getPasswordSystemUser(int inputUserID) {
        String passwordString = null;
        try {
            establishConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT fldSystemUserPassword FROM tblSystemUser WHERE fldSystemUserID = (?)");
            ps.setInt(1, inputUserID);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                passwordString = resultSet.getString(1);
            }

            ps.close();
            closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return passwordString;
    }

    // TODO decide if it can be deleted, since there is a list for system users now
    /**
     * The method checks if the user exists in the database.
     * @return a boolean value
     */
    public boolean checkUserExistence(int inputUserID) {
        boolean exists = false;

        try {
            establishConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tblSystemUser WHERE fldSystemUserID = (?)");
            ps.setInt(1, inputUserID);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                exists = true;
            }

            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return exists;
    }

    // TODO decide if it can be deleted, since there is a list for system users now?
    public int checkUserDepartment(int inputUserID) {
        int userDep = 0;

        try {
            establishConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT fldDepartmentID FROM tblSystemUser WHERE fldSystemUserID = (?)");
            ps.setInt(1, inputUserID);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                userDep = resultSet.getInt(1);
                System.out.println("found user department in the DB: " + userDep);
            }

            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userDep;
    }


    // TODO: create a new event for tblEventHistory: SP with all parameters to insert.


    //TODO SQL for insert of new item, return generated keys (ItemID)
    //https://www.codejava.net/java-se/jdbc/get-id-of-inserted-record-in-database
    public int insertNewLaundryItem(LaundryItem itemToInsert){
        return 0;
    }


    //TODO method to insert item size: int
    public void insertNewSize(int laundryItemID, int size){
    }

    /**
     * The method inserts a new customer objects into the databse. First it gets a new value from the sequence,
     * to generate an ID for the customer. Afterwards this value and the other parameters of the new customer objects
     * will be inserted by the help of a SQL statement. TODO: change prep. statement 1 to stored procedure.
     * @param newCustomer a new cutomer object
     * @return returns new Customer ID of datatype int
     */
    public int insertNewCustomer(Customer newCustomer){

        int newCustomerID = 0;

        try {
            establishConnection();

            try{
                PreparedStatement ps = connection.prepareStatement("SELECT NEXT VALUE FOR dbo.SequenceGenerateIDs");
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    newCustomerID = resultSet.getInt(1);
                    System.out.println("new value from DB: " + newCustomerID);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            PreparedStatement ps = connection.prepareStatement("INSERT INTO tblCustomer VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, newCustomerID);
            ps.setString(2, newCustomer.customerFirstName);
            ps.setString(3, newCustomer.customerLastName);
            ps.setString(4, newCustomer.customerPhoneNumber);
            ps.setString(5, newCustomer.customerEmail);

            ps.addBatch();
            ps.executeBatch();

            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newCustomerID;  //return newCustomer ID
    }

    //TODO SQL to insert new Order in DB, return new Order ID
    public int insertNewOrder(Order newOrder){
        return 0;       //return newOrderID
    }

}
