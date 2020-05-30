package Domain.LaundryItemTypes;

public class Trousers extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;

    public Trousers(String itemCol, boolean itemStat) {
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
        return "Trousers  " + itemColor + " " + "   Price:  " + itemPrice + " Kroner";
    }
}
