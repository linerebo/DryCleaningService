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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

public class DB {

    public Connection connection;

    /**
     * This method will establish a connection wit the database called "DryLeaningServiceDB".
     *
     * @return an object of connection
     */
    public Connection establishConnection() {

        //get access information for the DB from a separate file called DB_Properties
        Properties prop = new Properties();
        String file = "src/Persistance/DB_Properties";
        InputStream input;

        try {
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
    public ArrayList<Customer> getCustomersFromDB() {
        ArrayList<Customer> customers = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblCustomer");
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5))); //insert SQL statement here ??
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {
        }

        return customers;
    }

    public ArrayList<Order> getOrdersFromDB() {
        ArrayList<Order> orders = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblOrder");
            while (resultSet.next()) {
                Order newOrder = new Order(
                        resultSet.getInt(1),
                        Adapter.cleaningCentralInstance().getCustomerFromID(resultSet.getInt(2)),
                        Adapter.cleaningCentralInstance().getDeliveryPointFromID(resultSet.getInt(4))
                );
                orders.add(newOrder);
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {
        }
        return orders;
    }

    public void getLaundryItemsFromDB() {
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM  (tblLaundryItem INNER JOIN tblLaundry_Order ON tblLaundryItem.fldLaundryItemID = tblLaundry_Order.fldLaundryItemID) LEFT JOIN tblLaundrySize ON tblLaundryItem.fldLaundryItemID = tblLaundrySize.fldLaundryItemID");
            while (resultSet.next()) {
                if(resultSet.getInt(2) == Shirt.laundryTypeID){
                    Shirt newShirt = new Shirt(resultSet.getString(3), resultSet.getBoolean(4));
                    newShirt.itemID = resultSet.getInt(1);
                    Adapter.cleaningCentralInstance().getOrderFromID(resultSet.getInt(7)).items.add(newShirt);
                } else if (resultSet.getInt(2) == Blazer.laundryTypeID) {
                    Blazer newBlazer = new Blazer(resultSet.getString(3), resultSet.getBoolean(4));
                    newBlazer.itemID = resultSet.getInt(1);
                    Adapter.cleaningCentralInstance().getOrderFromID(resultSet.getInt(7)).items.add(newBlazer);
                } else if (resultSet.getInt(2) == Coat.laundryTypeID) {
                    Coat newCoat = new Coat(resultSet.getString(3), resultSet.getBoolean(4));
                    newCoat.itemID = resultSet.getInt(1);
                    Adapter.cleaningCentralInstance().getOrderFromID(resultSet.getInt(7)).items.add(newCoat);
                } else if (resultSet.getInt(2) == Tshirt.laundryTypeID) {
                    Tshirt newTshirt = new Tshirt(resultSet.getString(3), resultSet.getBoolean(4));
                    newTshirt.itemID = resultSet.getInt(1);
                    Adapter.cleaningCentralInstance().getOrderFromID(resultSet.getInt(7)).items.add(newTshirt);
                } else if (resultSet.getInt(2) == Dress.laundryTypeID) {
                    Dress newDress = new Dress(resultSet.getString(3), resultSet.getBoolean(4));
                    newDress.itemID = resultSet.getInt(1);
                    Adapter.cleaningCentralInstance().getOrderFromID(resultSet.getInt(7)).items.add(newDress);
                } else if (resultSet.getInt(2) == Trousers.laundryTypeID) {
                    Trousers newTrousers = new Trousers(resultSet.getString(3), resultSet.getBoolean(4));
                    newTrousers.itemID = resultSet.getInt(1);
                    Adapter.cleaningCentralInstance().getOrderFromID(resultSet.getInt(7)).items.add(newTrousers);
                } else if (resultSet.getInt(2) == Carpet.laundryTypeID) {
                    Carpet newCarpet = new Carpet(resultSet.getString(3), resultSet.getBoolean(4), resultSet.getInt(10));
                    newCarpet.itemID = resultSet.getInt(1);
                    Adapter.cleaningCentralInstance().getOrderFromID(resultSet.getInt(7)).items.add(newCarpet);
                }
            }
            st.close();
            closeConnection();
        } catch (SQLException e) {
        }
    }

    public ArrayList<DeliveryPoint> getDeliveryPointsFromDB() {
        ArrayList<DeliveryPoint> deliveryPoints = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblDeliveryPoint");
            while (resultSet.next()) {
                deliveryPoints.add(new DeliveryPoint(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {
        }

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
            while (resultSet.next()) {
                departments.add(new Department(resultSet.getInt(1), resultSet.getString(2)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {
        }

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
            while (resultSet.next()) {
                eventHistories.add(new EventHistory(resultSet.getInt(1), resultSet.getInt(2), resultSet.getTimestamp(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getBoolean(6)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {
        }

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
            while (resultSet.next()) {
                eventTypes.add(new EventType(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {
        }

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
                if(resultSet.getInt(1) == Shirt.laundryTypeID){
                    Shirt.itemPrice = resultSet.getInt(3);
                    Shirt.itemTimeToClean = resultSet.getInt(4);
                } else if (resultSet.getInt(1) == Blazer.laundryTypeID) {
                    Blazer.itemPrice = resultSet.getInt(3);
                    Blazer.itemTimeToClean = resultSet.getInt(4);
                } else if (resultSet.getInt(1) == Coat.laundryTypeID) {
                    Coat.itemPrice = resultSet.getInt(3);
                    Coat.itemTimeToClean = resultSet.getInt(4);
                } else if (resultSet.getInt(1) == Tshirt.laundryTypeID) {
                    Tshirt.itemPrice = resultSet.getInt(3);
                    Tshirt.itemTimeToClean = resultSet.getInt(4);
                } else if (resultSet.getInt(1) == Dress.laundryTypeID) {
                    Dress.itemPrice = resultSet.getInt(3);
                    Dress.itemTimeToClean = resultSet.getInt(4);
                } else if (resultSet.getInt(1) == Trousers.laundryTypeID) {
                    Trousers.itemPrice = resultSet.getInt(3);
                    Trousers.itemTimeToClean = resultSet.getInt(4);
                } else if (resultSet.getInt(1) == Carpet.laundryTypeID) {
                    Carpet.squareMeterPrice = resultSet.getInt(3);
                    Carpet.itemTimeToClean = resultSet.getInt(4);
                }
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {
        }
    }

    public ArrayList<Payment> getPaymentsFromDB() {
        ArrayList<Payment> payments = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        try {
            establishConnection();
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM tblPayment");
            while (resultSet.next()) {
                payments.add(new Payment(resultSet.getInt(1), resultSet.getInt(2), resultSet.getTimestamp(3), resultSet.getBoolean(4), resultSet.getInt(5)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {
        }

        return payments;
    }

    /**
     * The method will get all data from the table tblSystem user in the database except from the passwords.
     *
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
            while (resultSet.next()) {
                systemUsers.add(new SystemUser(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
            }
            st.close();
            closeConnection();

        } catch (SQLException e) {
        }

        return systemUsers;
    }

    // methods to fetch data, which are not loaded in at program start, directly from the database.

    /**
     * The method takes in an user ID and returns the corresponding password from the table of system users.
     *
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
     *
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

    /**
     * The method will return a new value from the sequence in the database, which will be used as ID for a new record
     * in any of the tables.
     * @return an integer
     */
    public int generateNewID() {

        int newID = 0;
        try {
            establishConnection();
            PreparedStatement ps = connection.prepareStatement("EXECUTE sp_generateNewID");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                newID = resultSet.getInt(1);
                System.out.println("new value from DB: " + newID);
            }
            ps.close();
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newID;
    }

    /**
     * The method inserts a new item to the database
     * @param itemToInsert
     * @return
     */
    public int insertNewLaundryItem(LaundryItem itemToInsert) {

        int newItemID = generateNewID();
        try {
            establishConnection();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO tblLaundryItem VALUES (?, ?, ?, ?)");
            ps.setInt(1, newItemID);
            ps.setInt(2, itemToInsert.getLaundryTypeID());
            ps.setString(3, itemToInsert.itemColor);
            ps.setBoolean(4, itemToInsert.itemStatus);

            ps.addBatch();
            ps.executeBatch();

            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newItemID;
    }

    //TODO SQL for updata item status after cleaning
    public void updataLaundryItem(LaundryItem laundryItemToUpdate){

        try {
            establishConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE tblLaundryItem SET fldLaundryItemStatus = (?) WHERE fldLaundryItemID = (?)");
            ps.setBoolean(1, true);
            ps.setInt(2, laundryItemToUpdate.itemID);

            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }

    /**
     * The method inserts a new record for an item size.
     * @param laundryItemID the ID of the item which got entered with a size
     * @param size the size entered
     */
    public void insertNewSize(int laundryItemID, int size) {

        int newLaundrySizeID = generateNewID();

        try {
            establishConnection();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO tblLaundrySize VALUES (?, ?, ?)");
            ps.setInt(1, newLaundrySizeID);
            ps.setInt(2, laundryItemID);
            ps.setInt(3, size);

            ps.addBatch();
            ps.executeBatch();

            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method inserts the new order in to the database.
     * Also it inserts a new record in to the combination table tblLaundry_Order.
     * @param newOrder the newly created order
     * @return the new ID of this order
     */
    public int insertNewOrder(Order newOrder) {

        int newOrderID = generateNewID();

        try {
            establishConnection();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO tblOrder VALUES (?, ?, ?, ?)");
            ps.setInt(1, newOrderID);
            ps.setInt(2, newOrder.orderCustomer.customerID);
            ps.setInt(3, newOrder.items.size());
            ps.setInt(4, newOrder.orderDeliveryPoint.deliveryPointID);

            ps.addBatch();
            ps.executeBatch();

            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // add record to tblLaundry_Order for every item on the new order (order - item pair)
        for (int i = newOrder.items.size() -1; i >= 0; i--) {
            LaundryItem itemToUse = newOrder.items.get(i);
            int itemToInsert = itemToUse.itemID;
            try {
                establishConnection();

                PreparedStatement ps = connection.prepareStatement("INSERT INTO tblLaundry_Order VALUES (?, ?, ?)");
                ps.setInt(1, generateNewID());
                ps.setInt(2, itemToInsert);
                ps.setInt(3, newOrderID);

                ps.addBatch();
                ps.executeBatch();

                ps.close();
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // call create Event function TODO: find a better way to get eventType and system userID
        insertNewEvent(newOrderID, 15, 24); // the creation of a new order has the eventType 15,
                                                                // the shop assistant has the systemUserID 24

        return newOrderID;       //return newOrderID
    }

    /**
     * The method creates a new event and updates previous events for the same orderID
     * @param orderID
     * @param eventType
     * @param systemUser
     * @return
     */
    public int insertNewEvent(int orderID, int eventType, int systemUser) {
        int newEventID = generateNewID();

        try {
            establishConnection();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO tblEventHistory VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, newEventID);
            ps.setInt(2, orderID);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(4, eventType);
            ps.setInt(5, systemUser);
            ps.setBoolean(6, true);

            ps.addBatch();
            ps.executeBatch();

            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //if it is not an event for creating a new order, update all the other events with same orderID to set current status to false.
        if (eventType != 15) {

            try {
                establishConnection();

                for (int i = Adapter.cleaningCentralInstance().eventHistories.size() - 1; i >= 0; i--) {

                    EventHistory eventHistoryToCheck = Adapter.cleaningCentralInstance().eventHistories.get(i);

                    if (eventHistoryToCheck.orderID == orderID) {

                        PreparedStatement ps = connection.prepareStatement("UPDATE tblEventHistory SET fldEventHistoryCurrentStatus = (?) WHERE fldOrderID = (?) AND NOT fldEventTypeID = (?)");
                        ps.setBoolean(1, false);
                        ps.setInt(2, orderID);
                        ps.setInt(3, eventType);

                        ps.executeUpdate();
                        ps.close();
                    }
                }
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Adapter.cleaningCentralInstance().eventHistories = Adapter.DBInstance().getEventHistoriesFromDB(); // update list of eventHistories
        return newEventID;
    }

    /**
     * The method inserts a new customer objects into the database. First it gets a new value from the sequence,
     * by calling the method generateNewID. Afterwards this value and the other parameters of the new customer objects
     * will be inserted by the help of a SQL statement.
     * @param newCustomer a new customer object
     * @return returns new Customer ID of datatype int
     */
    public int insertNewCustomer(Customer newCustomer) {

        int newCustomerID = generateNewID();

        try {
            establishConnection();

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

}
