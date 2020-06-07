package Domain;

import java.sql.Timestamp;

public class EventHistory {
    public int eventID;
    public int orderID;
    public Timestamp eventDateTimeStamp;

    public int eventTypeID;
    public int systemUserID; // better system user object?
    public boolean eventCurrentStatus;

    public EventHistory(int eventId, int orderId, Timestamp eventDateTimeStmp, int eventTypeId, int systemUserId, boolean eventCurrentStat) {
        eventID = eventId;
        orderID = orderId;
        eventDateTimeStamp = eventDateTimeStmp;
        eventTypeID = eventTypeId;
        systemUserID = systemUserId;
        eventCurrentStatus = eventCurrentStat;
    }
}
