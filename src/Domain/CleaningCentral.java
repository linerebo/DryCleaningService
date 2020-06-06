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
    //public ArrayList<NotAssignableLaundryItem> notAssignableLaundryItems; // no need. directly from DB for statistics.
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
        orders = Adapter.DBInstance().getOrdersFromDB();
        departments = Adapter.DBInstance().getDepartmentsFromDB();
        eventHistories = Adapter.DBInstance().getEventHistoriesFromDB();
        System.out.println("amount of eventHistories: " + eventHistories.size()); // test print
        eventTypes = Adapter.DBInstance().getEventTypesFromDB();
        // laundry_orders
        Adapter.DBInstance().getLaundryTypesFromDB();
        Adapter.DBInstance().getLaundryItemsFromDB();

        //NotAssignableLaundryItems
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

    public Order getOrderFromID(int id){
        Order tmpOrder = null;
        for(int i = 0; i< orders.size(); i++){
            if(id == orders.get(i).orderID){
                tmpOrder = orders.get(i);
            }
        }
        return tmpOrder;
    }

    public LaundryItem getItemFromID(int id){
        LaundryItem tmpItem = null;
        for(int i=0; i<orders.size(); i++){
            if(orders.get(i).isItemInOrder(id)){
                return orders.get(i).getItemFromItemID(id);
            }
        }
        return null;
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
    public ObservableList getOrderObjectsOnTruck(int userID) {
        ArrayList ordersOnTruckID = new ArrayList(); // an arraylist of orderIDs int
        // get all orderID as ints, which are currently on the truck
        for (int i = eventHistories.size() -1; i >= 0; i --) { // loop backwards, so it will not jump one item over when found one. Avoiding index out of bounds exception.
            EventHistory eve = eventHistories.get(i);
            if (eve.systemUserID == userID && eve.eventTypeID == 16 && eve.eventCurrentStatus == true) { // 16 is the eventTypeID for a pick-up event
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
        System.out.println("order objects on truck: " + ordersOnTruck);
        return ordersOnTruck;
    }

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
        System.out.println("order IDs waiting for pick up: " + waitingOrderIds);
        return waitingOrderIds;
    }

    /**
     * The method creates a list over all the events for one order.
     * @param orderID
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
            } else if (eHisToProcess.get(j).eventTypeID == 17){
                eventType = "Cleaning process started";
            } else if (eHisToProcess.get(j).eventTypeID == 18){
                eventType = "Cleaning process finished";
            } else if (eHisToProcess.get(j).eventTypeID == 19){
                eventType = "Return transportation";
            } else if (eHisToProcess.get(j).eventTypeID == 20){
                eventType = "Ready for hand-out";
            } else if (eHisToProcess.get(j).eventTypeID == 21){
                eventType = "Order Handed back";
            }
            eventStrings.add(eHisToProcess.get(j).eventDateTimeStamp + " - " + eventType + " - " + eHisToProcess.get(j).eventCurrentStatus);
        }
        ObservableList<String> evHist = FXCollections.observableArrayList(eventStrings);
        return evHist;
    }
}
