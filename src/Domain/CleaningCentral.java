package Domain;

import Domain.LaundryItemTypes.LaundryItem;
import Domain.SystemUser.SystemUser;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.*;

public class CleaningCentral {

    public ArrayList<Customer> customers;
    public ArrayList<DeliveryPoint> deliveryPoints;
    public ArrayList<Department> departments;
    public ArrayList<EventHistory> eventHistories;
    public ArrayList<EventType> eventTypes;
    //laundry_Order // not sure if we need this.
    public ArrayList<LaundryItem> laundryItems;
    //public ArrayList<LaundryType> laundryTypes; // No need for this.
    //public ArrayList<NotAssignableLaundryItem> notAssignableLaundryItems; // no need. directly from DB.
    public ArrayList<Order> orders;
    public ArrayList<Payment> payments;
    //public ArrayList<PostalCode> postalCodes; // no need for this
    public ArrayList<SystemUser> systemUsers; //  all user information excluding the passwords

    public CleaningCentral() {
    }

    public void getDataFromDB() {

        customers = Adapter.DBInstance().getCustomersFromDB();
        System.out.println("amount of customers: " + customers.size()); // test print
        deliveryPoints = Adapter.DBInstance().getDeliveryPointsFromDB();
        System.out.println("amount of deliveryPoints: " + deliveryPoints.size()); // test print
        departments = Adapter.DBInstance().getDepartmentsFromDB();
        eventHistories = Adapter.DBInstance().getEventHistoriesFromDB();
        System.out.println("amount of eventHistories: " + eventHistories.size()); // test print
        eventTypes = Adapter.DBInstance().getEventTypesFromDB();
        // laundry_orders
        laundryItems = Adapter.DBInstance().getLaundryItemsFromDB();
        Adapter.DBInstance().getLaundryTypesFromDB();
        //NotAssignableLaundryItems
        orders = Adapter.DBInstance().getOrdersFromDB();
        payments = Adapter.DBInstance().getPaymentsFromDB();
        //postalCode
        systemUsers = Adapter.DBInstance().getSystemUsersFromDB();
    }

    public DeliveryPoint getDeliveryPointFromID(int id) {
        DeliveryPoint dp = new DeliveryPoint(0, "", "", "");
        for (int i = 0; i < deliveryPoints.size(); i++) {
            if (id == deliveryPoints.get(i).deliveryPointID) {
                dp = deliveryPoints.get(i);
            }
        }
        return dp;
    }

    public Customer getCustomerFromID(int id){
        Customer tmpCustomer = null;
        for (int i = 0; i < customers.size(); i++) {
            if (id == customers.get(i).customerID) {
                tmpCustomer = customers.get(i);
            }
        }
        return tmpCustomer;
    }

    public ObservableList<Customer> getCustomersFromName(String inputName) {
        ObservableList<Customer> resultListCustomers = FXCollections.observableArrayList();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).customerLastName.toLowerCase().startsWith(inputName.toLowerCase())) {
                resultListCustomers.add(customers.get(i));
            }
        }
        return resultListCustomers;
    }

    /**
     * The method instantiates a new system user object and gives it the variables of the object found in the array
     * of system users by the given ID.
     * @param id of the
     * @return a system user object
     */
    public SystemUser getSystemUserFromID(int id) {
        SystemUser su = new SystemUser(0, "", "", 0);

        for (int i = 0; i < systemUsers.size(); i++) {
            if (id == systemUsers.get(i).systemUserID) {
                su = systemUsers.get(i);
            }
        }
        return su;
    }

    /**
     * The method checks if a user with the provided userID exists in the system.
     * @param id user id
     * @return a boolean of existence
     */
    public boolean doesUserExist(int id) {
        boolean existence = false;

        for (SystemUser u : systemUsers) {
            if (u.systemUserID == id) {
                existence = true;
            }
        }

        return existence;
    }

    /**
     * The method finds all orders, which are currently loaded on the truck of a specific driver/systemUser.
     * This means, they haven't been processed further than this.
     * @param userID of the system user
     * @return an observable List of order objects.
     */
    public ObservableList<Order> getOrderObjectsOnTruck(int userID) {
        ArrayList ordersOnTruckID = new ArrayList(); // an arraylist of orderIDs int
        // get all orderID as ints, which are currently on the truck
        for (int i = eventHistories.size() -1; i >= 0; i --) { // loop backwards, so it will not jump one item over when found one. Avoiding index out of bounds exception.
            EventHistory eve = eventHistories.get(i);
            if (eve.systemUserID == userID && eve.eventTypeID == 2 && eve.eventCurrentStatus == true) {
                int eveOrderID = eve.orderID;
                ordersOnTruckID.add(eveOrderID);
            }
        }
        System.out.println("array orderIDs on truck: " + ordersOnTruckID);
        //find the corresponding order objects in the orders list.
        ArrayList<Order> onTruckOrders = new ArrayList<>();
        for (int x = orders.size() - 1; x >= 0; x--) {
            Order o = orders.get(x);
            for(int y = ordersOnTruckID.size() - 1; y >= 0; y--) {
                int otID = (int) ordersOnTruckID.get(y);
                if (o.orderID == otID) {
                    onTruckOrders.add(o);
                }
            }
        }
        ObservableList ordersOnTruck = FXCollections.observableArrayList(onTruckOrders);
        return ordersOnTruck;
    }
}
