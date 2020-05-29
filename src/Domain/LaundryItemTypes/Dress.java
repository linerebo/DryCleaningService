package Domain.LaundryItemTypes;

public class Dress extends LaundryItem {
    private int itemPrice;
    private int itemTimeToClean;

    public Dress(int itemId, String itemCol, boolean itemStat, int price, int timeToClean) {
        super(itemId, itemCol, itemStat);
        itemPrice = price;
        itemTimeToClean = timeToClean;
    }

    @Override
    public int timeToClean() {
        return itemTimeToClean;
    }

    @Override
    public int price(){
        return itemPrice;
    }
}
