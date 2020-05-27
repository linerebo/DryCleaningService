package Domain;

        import java.util.ArrayList;

public class Order {
    public ArrayList<LaundryItem> items = new ArrayList<>();
    public int orderID;
    public int orderAmount;

    public Order(int id, int amount){
        orderID = id;
        orderAmount = amount;
    }

}
