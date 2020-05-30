package Domain.LaundryItemTypes;

public class Blazer extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;;

    public Blazer(String itemCol, boolean itemStat) {
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
        return "Blazer  " + itemColor + " " + "   Price:  " + itemPrice + " Kroner";
    }

}
