package Domain;

        import java.util.ArrayList;

public class Order {
    public ArrayList<LaundryItem> items = new ArrayList<>();
    public int orderID;
    public int customerID;
    public int orderAmount;
    public int deliveryPointID;


    public Order(int id, int customerId, int amount, int deliveryPointId){
        orderID = id;
        customerID = customerId;
        orderAmount = amount;
        deliveryPointID = deliveryPointId;
    }

//    public int customerID;
//    public int orderAmount;
//    public int deliveryPointID;
//
//    public Order(int orderID, int customerID, int orderAmount, int deliveryPointID) {
//
//        this.orderID = orderID;
//        this.customerID = customerID;
//        this.orderAmount = orderAmount;
//        this.deliveryPointID = deliveryPointID;
//    }
//
//    public Order() {}
}
