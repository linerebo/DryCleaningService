package Domain.LaundryItemTypes;

import Domain.Adapter;

public class Trousers extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;
    public static int laundryTypeID = 10;

    public Trousers(String itemCol, boolean itemStat) {
        super(itemCol, itemStat);
    }

    @Override
    public int getLaundryTypeID(){
        return Trousers.laundryTypeID;
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
        return "Trousers  " + itemColor + " " + "   Price:  " + itemPrice + " Kroner";
    }

    @Override
    public String toWashableLabel() {return "Trousers  " + itemColor + " Item No: " + itemID;}

    @Override
    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
    }
}
