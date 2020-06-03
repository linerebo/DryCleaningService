package Domain.LaundryItemTypes;

import Domain.Adapter;

public class Shirt extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;
    public static int laundryTypeID = 4;

    public Shirt(String itemCol, boolean itemStat) {
        super(itemCol, itemStat);
    }

    @Override
    public int getLaundryTypeID(){
        return Shirt.laundryTypeID;
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
        return "Shirt  " + itemColor + " " + "   Price:  " + itemPrice + " Kroner";
    }

    @Override
    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
    }
}
