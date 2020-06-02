package Domain.LaundryItemTypes;

import Domain.Adapter;

public class Carpet extends LaundryItem {
    public static int squareMeterPrice;
    public static int itemTimeToClean;
    private int squareMeters;


    public Carpet(int itemId, int laundryType, String itemCol, boolean itemStat, int sizeCarpet) {
        super(itemId, laundryType, itemCol, itemStat);
        squareMeters = sizeCarpet;
    }

    @Override
    public int timeToClean() {
        return itemTimeToClean;
    }

    @Override
    public int price(){
        return squareMeterPrice*squareMeters;
    }

    public String toString(){
        return "Carpet  " + itemColor + " " + "   Price:  " + price() + " Kroner";
    }

    @Override
    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
        Adapter.DBInstance().insertNewSize(itemID, squareMeters);
    }
}
