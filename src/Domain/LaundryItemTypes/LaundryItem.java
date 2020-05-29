package Domain.LaundryItemTypes;

import Domain.Adapter;

public abstract class LaundryItem {
    public int itemID;
    public String itemColor;
    public boolean itemStatus;

    public LaundryItem(String itemCol, boolean itemStat){
        itemColor = itemCol;
        itemStatus = itemStat;
    }

    public abstract int timeToClean();

    public abstract int price();

    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
    }

}
