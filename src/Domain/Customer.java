package Domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Customer {
    public int customerID;
    public String customerFirstName;
    public String customerLastName;
    public String customerPhoneNumber;
    public String customerEmail;


    public Customer(int custID, String firstName, String lastName, String phoneNo, String email){
        customerID = custID;
        customerFirstName = firstName;
        customerLastName = lastName;
        customerPhoneNumber = phoneNo;
        customerEmail = email;
    }

    public String toString(){
        return customerFirstName + " " + customerLastName;
    }

    public void storeToDB(){
        customerID = Adapter.DBInstance().insertNewCustomer(this);
    }

    /**
     *This method returns a list of all orders from a customer.
     * @return
     */
    public ObservableList getOrders(){
        ObservableList ordersFromCustomer = FXCollections.observableArrayList();
        for(int i=0; i<Adapter.cleaningCentralInstance().orders.size(); i++){
            if(Adapter.cleaningCentralInstance().orders.get(i).orderCustomer == this){
                ordersFromCustomer.add(Adapter.cleaningCentralInstance().orders.get(i));
            }
        }
        return ordersFromCustomer;
    }

}
