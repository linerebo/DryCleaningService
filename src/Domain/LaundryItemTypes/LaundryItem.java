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

    public abstract int price();
    public abstract int getLaundryTypeID();
    public abstract int timeToClean();
    public abstract String toWashableLabel();

    /**
     * This method calls insertNewLaundryItem in DB to insert a new LaundryItem in DB.
     */
    public void storeToDB(){
        itemID = Adapter.DBInstance().insertNewLaundryItem(this);
    }

    /**
     * This method calls updataLaundryItem in DB to updata a LaundryItem in DB.
     */
    public void updateToDB(){
        Adapter.DBInstance().updataLaundryItem(this);
    }

    /**
     * This method finds the order that an item is found in.
     * @return
     */
    public Order getOrderFromItem(){
        for(int i=0; i<Adapter.cleaningCentralInstance().orders.size(); i++){
            if(Adapter.cleaningCentralInstance().orders.get(i).isItemInOrder(itemID)){
                return Adapter.cleaningCentralInstance().orders.get(i);
            }
        }
        return null;
    }
}
