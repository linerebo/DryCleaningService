package Domain.LaundryItemTypes;

public class Trousers extends LaundryItem {
    private int itemPrice;
    private int itemTimeToClean;

    public Trousers(String itemCol, boolean itemStat, int price, int timeToClean) {
        super(itemCol, itemStat);
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
