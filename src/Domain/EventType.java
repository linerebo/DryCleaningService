package Domain;

public class EventType {
    public int eventTypeID;
    public String eventTypeName;
    public String eventTypeDescription;

    public EventType(int eventTypeId, String eventTypeNam, String eventTypeDescript) {
        eventTypeID = eventTypeId;
        eventTypeName = eventTypeNam;
        eventTypeDescription = eventTypeDescript;
    }
}
