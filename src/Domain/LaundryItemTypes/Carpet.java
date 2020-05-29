package Domain.LaundryItemTypes;

public class Carpet extends LaundryItem {
    private int squareMeterPrice;
    private int squareMeters;
    private int itemTimeToClean;


    public Carpet(int itemId, String itemCol, boolean itemStat, int price, int timeToClean, int sizeCarpet) {
        super(itemId, itemCol, itemStat);
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
}
