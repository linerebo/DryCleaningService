package Domain.LaundryItemTypes;

import Domain.Adapter;

public class Dress extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;
    public static int laundryTypeID = 8;

    public Dress(String itemCol, boolean itemStat) {
        super(itemCol, itemStat);
    }

    @Override
    public int getLaundryTypeID(){
        return Dress.laundryTypeID;
    }

    @Override
    public int timeToClean() {
        return itemTimeToClean;
    }

    @Override
    public int price(){
        return itemPrice;
    }

    public String toString(){
        return "Dress  " + itemColor + " " + "   Price:  " + itemPrice + " Kroner";
    }

    @Override
    public String toWashableLabel() {return "Dress  " + itemColor + " Item No: " + itemID;}

    @Override
    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
    }
}
