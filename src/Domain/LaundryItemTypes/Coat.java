package Domain.LaundryItemTypes;

import Domain.Adapter;

public class Coat extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;

    public Coat(int itemId, int laundryType, String itemCol, boolean itemStat) {
        super(itemId, laundryType, itemCol, itemStat);
    }

    @Override
    public int timeToClean() {
        return itemTimeToClean;
    }

    @Override
    public int price() {
        return itemPrice;
    }

    public String toString() {
        return "Coat  " + itemColor + " " + "   Price:  " + itemPrice + " Kroner";
    }

    @Override
    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
    }
}
