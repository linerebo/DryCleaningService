package Domain.LaundryItemTypes;

import Domain.Adapter;

public class Carpet extends LaundryItem {
    public static int squareMeterPrice;
    public static int itemTimeToClean;
    public static int laundryTypeID = 6;
    private int squareMeters;


    public Carpet(String itemCol, boolean itemStat, int sizeCarpet) {
        super(itemCol, itemStat);
        squareMeters = sizeCarpet;
    }

    @Override
    public int getLaundryTypeID(){
        return Carpet.laundryTypeID;
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

    public String toWashableLabel() {return "Carpet  " + itemColor + " ItemID: " + itemID;}

    @Override
    public void storeToDB(){
        super.storeToDB();
        Adapter.DBInstance().insertNewSize(itemID, squareMeters);
    }


}
