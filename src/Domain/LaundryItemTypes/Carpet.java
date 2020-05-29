package Domain.LaundryItemTypes;

import Domain.Adapter;

public class Carpet extends LaundryItem {
    private int squareMeterPrice;
    private int squareMeters;
    private int itemTimeToClean;


    public Carpet(String itemCol, boolean itemStat, int price, int timeToClean, int sizeCarpet) {
        super(itemCol, itemStat);
        squareMeterPrice = price;
        itemTimeToClean = timeToClean;
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

    @Override
    public void storeToDB(){
        super.storeToDB();
        Adapter.DBInstance().insertNewSize(itemID, squareMeters);
    }
}
