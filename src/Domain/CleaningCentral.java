package Domain;

import java.util.ArrayList;

public class CleaningCentral {

    public ArrayList<DeliveryPoint> deliveryPoints = new ArrayList<>();
    public ArrayList<Customer> customers;
    public ArrayList<Order> orders = new ArrayList<>();




    public CleaningCentral(){
        //getDataFromDB();

    }

    public void updateDataFromDB(){
        customers = Adapter.DBInstance().getCustomersFromDB();
        orders = Adapter.DBInstance().getOrdersFromDB();

    }

}
