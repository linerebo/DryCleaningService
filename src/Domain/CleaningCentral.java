package Domain;

import Domain.LaundryItemTypes.LaundryItem;
import Domain.SystemUser.SystemUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;

public class CleaningCentral {

    public ArrayList<Customer> customers;
    public ArrayList<DeliveryPoint> deliveryPoints;
    public ArrayList<Department> departments;
    public ArrayList<EventHistory> eventHistories;
    public ArrayList<EventType> eventTypes;
    public ArrayList<LaundryItem> laundryItems;
    public ArrayList<Order> orders;
    public ArrayList<Payment> payments;
    public ArrayList<SystemUser> systemUsers; //  all user information excluding the passwords

    public CleaningCentral() {
    }

    public void getDataFromDB() {

        customers = Adapter.DBInstance().getCustomersFromDB();
        //System.out.println("amount of customers: " + customers.size()); // test print to console
        deliveryPoints = Adapter.DBInstance().getDeliveryPointsFromDB();
        orders = Adapter.DBInstance().getOrdersFromDB();
        departments = Adapter.DBInstance().getDepartmentsFromDB();
        eventHistories = Adapter.DBInstance().getEventHistoriesFromDB();
        eventTypes = Adapter.DBInstance().getEventTypesFromDB();
        Adapter.DBInstance().getLaundryTypesFromDB();
        Adapter.DBInstance().getLaundryItemsFromDB();
        payments = Adapter.DBInstance().getPaymentsFromDB();
        systemUsers = Adapter.DBInstance().getSystemUsersFromDB();
    }

    /**
     * This method returns DeliveryPoint from deliveryPointID.
     * @param id
     * @return
     */
    public DeliveryPoint getDeliveryPointFromID(int id) {
        DeliveryPoint dp = new DeliveryPoint(0, "", "", "");
        for (int i = 0; i < deliveryPoints.size(); i++) {
            if (id == deliveryPoints.get(i).deliveryPointID) {
                dp = deliveryPoints.get(i);
            }
        }
        return dp;
    }

    /**
     * This method returns Customer from customerID.
     * @param id
     * @return
     */
    public Customer getCustomerFromID(int id){
        Customer tmpCustomer = null;
        for (int i = 0; i < customers.size(); i++) {
            if (id == customers.get(i).customerID) {
                tmpCustomer = customers.get(i);
            }
        }
        return tmpCustomer;
    }

    /**
     * This method returns Order from orderID.
     * @param id
     * @return
     */
    public Order getOrderFromID(int id){
        Order tmpOrder = null;
        for(int i = 0; i< orders.size(); i++){
            if(id == orders.get(i).orderID){
                tmpOrder = orders.get(i);
            }
        }
        return tmpOrder;
    }

    /**
     *This method search the list of Orders and returns a LaundryItem from itemID.
     * @param id
     * @return
     */
    public LaundryItem getItemFromID(int id){
        LaundryItem tmpItem = null;
        for(int i=0; i<orders.size(); i++){
            if(orders.get(i).isItemInOrder(id)){    //if item is found in an order
                return orders.get(i).getItemFromItemID(id); // returns LaundryItem from itemID
            }
        }
        return null;
    }

    /**
     * This method return a list of Customers where customerLastName starts with the input String.
     * The search is case insensitive.
     * @param inputName
     * @return
     */
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
     * The method finds all order IDs from the event history which are currently loaded on the truk of the given user.
     * @param userID the ID of the systemuser /driver.
     * @return an observable list of orderIDs
     */
    public ObservableList getOrderIDsOnTruck(int userID) {
        ObservableList ordersOnTruckID = FXCollections.observableArrayList();
        // get all orderID as ints, which are currently on the truck
        for (int i = eventHistories.size() -1; i >= 0; i --) { // loop backwards, so it will not jump one item over when found one. Avoiding index out of bounds exception.
            EventHistory eve = eventHistories.get(i);
            if (eve.systemUserID == userID && eve.eventCurrentStatus == true && eve.eventTypeID != 17 && eve.eventTypeID != 21) { // event type 292 = order got unloaded at cleaning central, event type 20 = order is ready at the delivery point
                int eveOrderID = eve.orderID;
                ordersOnTruckID.add(eveOrderID);
            }
        }
        //System.out.println("observable list of orderIDs on truck: " + ordersOnTruckID); // test print to console
        return ordersOnTruckID;
    }

    /**
     * The method finds all order objects, which are currently loaded on the truck of a specific driver/systemUser.
     * This means, they haven't been processed further than this.
     * @param userID of the system user
     * @return an observable List of order objects.
     */
    public ObservableList getOrderObjectsOnTruck(int userID) {
        ObservableList ordersOnTruckID = getOrderIDsOnTruck(userID); // an observable list of orderIDs int
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
        //System.out.println("order objects on truck: " + ordersOnTruck); // test print to console
        return ordersOnTruck;
    }

    /**
     * The method finds the orders, that are newly created at a deliverypoint and are waiting for the driver
     * to be transported to the cleaning central.
     * @param deliveryPointID the ID of the given delivery point
     * @return an obsesrvablelist of orderIDs
     */
    public ObservableList getWaitingOrdersFromDeliveryPoint(int deliveryPointID) {
        ArrayList<Order> orderObjectsCreatedOnDeliveryPoint = new ArrayList();
        // get orders with matching delivery point ID from tblOrder
        for (int i = orders.size() - 1; i >= 0; i--) {
            Order o = orders.get(i);
            if (o.orderDeliveryPoint.deliveryPointID == deliveryPointID) {
                orderObjectsCreatedOnDeliveryPoint.add(o);
            }
        }
        // for the found orders, find out in the event table, if they have eventtype 15(order created) and current status true.
        ArrayList orderIdsOnDeliveryPoint = new ArrayList(); // an arraylist of OrderIDs
        for (int j = eventHistories.size() - 1; j >= 0; j--) {
            EventHistory e = eventHistories.get(j);
            for(int k = orderObjectsCreatedOnDeliveryPoint.size() - 1; k >= 0; k--) {
                Order oID = orderObjectsCreatedOnDeliveryPoint.get(k);
                if (e.orderID == oID.orderID && e.eventTypeID == 15 && e.eventCurrentStatus == true) {
                    orderIdsOnDeliveryPoint.add(e.orderID);
                }
            }
        }
        ObservableList waitingOrderIds = FXCollections.observableArrayList(orderIdsOnDeliveryPoint);
        //System.out.println("order IDs waiting for pick up: " + waitingOrderIds); // test print to console
        return waitingOrderIds;
    }

    /**
     * The method collects all the orders in an observable list, which are finished for cleaning at the cleaning central.
     * @return observablelist of int "waitingCleanOrderIDsAtCC"
     */
    public ObservableList getWaitingOrdersAtCleaningCentral() {
        ObservableList waitingCleanOrderIDsAtCC = FXCollections.observableArrayList();
        for (int i = eventHistories.size() - 1; i >= 0; i--) {
            EventHistory e = eventHistories.get(i);
            if (e.eventTypeID == 19 && e.eventCurrentStatus == true) { // event type 19 = "cleaning finished"
                waitingCleanOrderIDsAtCC.add(e.orderID);
            }
        }
        return waitingCleanOrderIDsAtCC;
    }

    /**
     * The method creates a list over all the events for one order.
     * @param orderID the ID of the given order
     * @return an observable list of Strings
     */
    public ObservableList<String> getEventHistoryFromOrderID(int orderID) {
        // find and store all event histories for the given order ID
        ArrayList<EventHistory> eHisToProcess = new ArrayList<>();
        for(int i = eventHistories.size()-1; i >= 0; i--) {
            if(eventHistories.get(i).orderID == orderID) {
                eHisToProcess.add(eventHistories.get(i));
            }
        }
        // from the list above, get the information needed from every event
        ArrayList<String> eventStrings = new ArrayList<>();
        for(int j = eHisToProcess.size()-1; j >= 0; j--){
            // if statement to "translate" event type
            String eventType = "";
            if(eHisToProcess.get(j).eventTypeID == 15){
                eventType = "Order Created";
            } else if (eHisToProcess.get(j).eventTypeID == 16){
                eventType = "Order on transportation";
            } else if (eHisToProcess.get(j).eventTypeID == 17) {
                eventType = "Order arrived at Cleaning Central";
            } else if (eHisToProcess.get(j).eventTypeID == 18){
                eventType = "Cleaning process started";
            } else if (eHisToProcess.get(j).eventTypeID == 19){
                eventType = "Cleaning process finished";
            } else if (eHisToProcess.get(j).eventTypeID == 20){
                eventType = "Return transportation";
            } else if (eHisToProcess.get(j).eventTypeID == 21){
                eventType = "Ready for hand-out";
            } else if (eHisToProcess.get(j).eventTypeID == 22){
                eventType = "Order Handed back";
            }
            eventStrings.add(eHisToProcess.get(j).eventDateTimeStamp + " - " + eventType + " - " + eHisToProcess.get(j).eventCurrentStatus);
        }
        ObservableList<String> evHist = FXCollections.observableArrayList(eventStrings);
        return evHist;
    }
}
