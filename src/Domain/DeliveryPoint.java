package Domain;

import Domain.SystemUser.ShopAssistant;

public class DeliveryPoint {
    public ShopAssistant shopAssistant;
    public int deliveryPointID;
    public String address;
    public String zipCode;
    public String route;

    public DeliveryPoint(int deliveryPointId, String addressDP, String zipCodeDP, String routeDP){
        deliveryPointID = deliveryPointId;
        address = addressDP;
        zipCode = zipCodeDP;
        route = routeDP;
    }
}
