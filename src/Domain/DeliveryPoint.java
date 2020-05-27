package Domain;

import Domain.SystemUser.ShopAssistant;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DeliveryPoint {
    public ShopAssistant shopAssistant;
    public int deliveryPointID;
    public String address;
    public ObservableList<LaundryItem> basket;

    public DeliveryPoint(int id, String deliveryAddress){
        deliveryPointID = id;
        address = deliveryAddress;
    }
}
