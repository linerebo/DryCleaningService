package Domain;

        import Domain.LaundryItemTypes.LaundryItem;

        import java.util.ArrayList;

public class Order {
    public ArrayList<LaundryItem> items = new ArrayList<>();
    public int orderID;
    public Customer orderCustomer;
    public DeliveryPoint orderDeliveryPoint;


    public Order(int id, Customer orderCust, DeliveryPoint dp){
        orderID = id;
        orderCustomer = orderCust;
        orderDeliveryPoint = dp;
    }

    public void storeToDB(){
        orderID = Adapter.DBInstance().insertNewOrder(this);
    }

}
