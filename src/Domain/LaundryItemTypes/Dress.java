package Domain.LaundryItemTypes;

public class Dress extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;

    public Dress(String itemCol, boolean itemStat) {
        super(itemCol, itemStat);
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
}
