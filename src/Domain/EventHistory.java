package Domain;


import java.sql.Timestamp;

public class EventHistory {
    public int eventID;
    public int orderID;
    public Timestamp eventDateTimeStamp;

    public int eventTypeID;
    public int systemUserID;

    public EventHistory(int eventId, int orderId, Timestamp eventDateTimeStmp, int eventTypeId, int systemUserId) {
        eventID = eventId;
        orderID = orderId;
        eventDateTimeStamp = eventDateTimeStmp;
        eventTypeID = eventTypeId;
        systemUserID = systemUserId;
    }
}
