package Domain.LaundryItemTypes;

import Domain.Adapter;

public class Tshirt extends LaundryItem {
    public static int itemPrice;
    public static int itemTimeToClean;

    public Tshirt(int itemId, int laundryType, String itemCol, boolean itemStat) {
        super(itemId, laundryType, itemCol, itemStat);
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

    @Override
    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
    }
}
