package Domain.LaundryItemTypes;

import Domain.Adapter;

public abstract class LaundryItem {
    public int itemID;
    public int laundryTypeID;
    public String itemColor;
    public boolean itemStatus;

    public LaundryItem(int itemId, int laundryTypeId, String itemCol, boolean itemStat){
        itemID = itemId;
        laundryTypeID = laundryTypeId;
        itemColor = itemCol;
        itemStatus = itemStat;
    }

    public abstract int timeToClean();

    public abstract int price();

    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
    }


}
