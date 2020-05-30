package Domain.LaundryItemTypes;

public class Tshirt extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;

    public Tshirt(String itemCol, boolean itemStat) {
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
