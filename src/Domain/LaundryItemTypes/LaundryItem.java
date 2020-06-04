package Domain.LaundryItemTypes;

import Domain.Adapter;
import Domain.Order;

public abstract class LaundryItem {
    public int itemID;
    public String itemColor;
    public boolean itemStatus;

    public LaundryItem(String itemCol, boolean itemStat){
        itemColor = itemCol;
        itemStatus = itemStat;
    }

    public abstract int getLaundryTypeID();

    public abstract int timeToClean();

    public abstract int price();

    public abstract String toWashableLabel();

    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
    }

    public void updateToDB(){
        Adapter.DBInstance().updataLaundryItem(this);
    }

    public Order getOrderFromItem(){
        for(int i=0; i<Adapter.cleaningCentralInstance().orders.size(); i++){
            if(Adapter.cleaningCentralInstance().orders.get(i).isItemInOrder(itemID)){
                return Adapter.cleaningCentralInstance().orders.get(i);
            }
        }
        return null;
    }
}
