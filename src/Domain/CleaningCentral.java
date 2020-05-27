package Domain;

import java.util.ArrayList;

public class CleaningCentral {

    public ArrayList<DeliveryPoint> deliveryPoints = new ArrayList<>();
    public ArrayList<Customer> customers;
    public ArrayList<Order> orders = new ArrayList<>();

    // arrayLists of data objects:
    public ArrayList<Object> orderDataObjects;

    //Order newOrder;


    public CleaningCentral(){
        //getDataFromDB();
        orderDataObjects = Adapter.DBInstance().objectArrayListOrder;

        // TODO make order objects out of every set of four (numberOfColumns) data objects in the objectArrayListOrder
//        while (!Adapter.DBInstance().objectArrayListOrder.isEmpty()) {
//            for (int num = 0; num <= Adapter.DBInstance().numberOfColumns; num++) {
//                newOrder = new Order();
//
//                newOrder.orderID = (int) Adapter.DBInstance().objectArrayListOrder.get(0);
//                newOrder.customerID = (int) Adapter.DBInstance().objectArrayListOrder.get(1);
//                newOrder.orderAmount = (int) Adapter.DBInstance().objectArrayListOrder.get(2);
//                newOrder.deliveryPointID = (int) Adapter.DBInstance().objectArrayListOrder.get(3);
//                orders.add(newOrder); // TODO solve problem. "out of memory error: java heap space"
//            }
//        }

        // TODO repeat this for every "object - type" neede (e.g. customers, orders,...)
    }

    public void updateDataFromDB(){
        customers = Adapter.DBInstance().getCustomersFromDB();
        orders = Adapter.DBInstance().getOrdersFromDB();
        deliveryPoints = Adapter.DBInstance().getDeliveryPointsFromDB();
    }

    public DeliveryPoint getDeliveryPointFromID(int id){
        return new DeliveryPoint(1, "XY");

    }

}
