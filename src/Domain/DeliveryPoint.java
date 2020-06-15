package Domain;

public class DeliveryPoint {

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
    public String toString(){
        return "Delivery Point: " + deliveryPointID + "  Address: " + address + ", Zip Code: " + zipCode;
    }
}
