package Domain.LaundryItemTypes;

public class Coat extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;

    public Coat(String itemCol, boolean itemStat) {
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
        return "Coat  " + itemColor + " " + "   Price:  " + itemPrice + " Kroner";
    }
}
