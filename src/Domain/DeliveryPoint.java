package Domain;

import Domain.SystemUser.ShopAssistant;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DeliveryPoint {
    public ShopAssistant shopAssistant;
    public int deliveryPointID;
    public String address;
    public ObservableList<LaundryItem> basket;
    public String zipCode;
    public String route;

    public DeliveryPoint(int deliveryPointId, String addressDP, String zipCodeDP, String routeDP){
        deliveryPointID = deliveryPointId;
        address = addressDP;
        zipCode = zipCodeDP;
        route = routeDP;
    }
}
