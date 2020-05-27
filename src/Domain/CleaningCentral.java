package Domain;

import java.util.ArrayList;

public class CleaningCentral {

    public ArrayList<Customer> customers;
    public ArrayList<DeliveryPoint> deliveryPoints;
    public ArrayList<Department> departments;
    public ArrayList<EventHistory> eventHistories;
    public ArrayList<EventType> eventTypes;
    //laundry_Order // TODO same for combination tables?
    public ArrayList<LaundryItem> laundryItems;
    //public ArrayList<LaundryType> laundryTypes; // TODO find solution for class LaundryType and its subclasses
    //public ArrayList<NotAssignableLaundryItem> notAssignableLaundryItems; // TODO decide whether it is an own class or a subclass of LaundryItem
    public ArrayList<Order> orders;
    public ArrayList<Payment> payments;
    //public ArrayList<PostalCode> postalCodes; // TODO do we need this?
    //public ArrayList<SystemUser> systemUsers; // TODO find solution for class SystemUser and its subclasses

    public CleaningCentral() {
    }

    public void getDataFromDB(){

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
        //laundryTypes = Adapter.DBInstance().getLaundryTypesFromDB();
        //NotAssignableLaundryItems
        orders = Adapter.DBInstance().getOrdersFromDB();
        payments = Adapter.DBInstance().getPaymentsFromDB();
        //postalCode
        //systemUser
    }

    public void updateDataFromDB () {
        //customers = Adapter.DBInstance().getCustomersFromDB();
        //orders = Adapter.DBInstance().getOrdersFromDB();
    }
}
