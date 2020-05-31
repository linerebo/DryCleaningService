package Domain;

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
}
