package Domain.LaundryItemTypes;

public abstract class LaundryItem {
    public int itemID;
    public String itemColor;
    public boolean itemStatus;

    public LaundryItem(int itemId, String itemCol, boolean itemStat){
        itemID = itemId;
        itemColor = itemCol;
        itemStatus = itemStat;
    }

    public abstract int timeToClean();

    public abstract int price();

}
