package Domain.LaundryItemTypes;

public class Shirt extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;

    public Shirt(String itemCol, boolean itemStat) {
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
        return "Shirt  " + itemColor + " " + "   Price:  " + itemPrice + " Kroner";
    }
}
