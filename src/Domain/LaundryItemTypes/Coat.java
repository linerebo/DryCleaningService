package Domain.LaundryItemTypes;

import Domain.Adapter;

public class Coat extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;
    public static int laundryTypeID = 7;

    public Coat(String itemCol, boolean itemStat) {
        super(itemCol, itemStat);
    }

    @Override
    public int getLaundryTypeID(){
        return Coat.laundryTypeID;
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
        return "Coat  " + itemColor + "    Price:  " + itemPrice + " Kroner\n";
    }

    @Override
    public String toWashableLabel() {return "Coat  " + itemColor + " Item No: " + itemID;}

    @Override
    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
    }
}
