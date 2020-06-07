package Domain;

        import Domain.LaundryItemTypes.LaundryItem;
        import java.util.ArrayList;
        import java.util.*;
        //import javax.mail.*;
        //import javax.mail.internet.*;
        import javax.activation.*;

public class Order {
    public ArrayList<LaundryItem> items = new ArrayList<>();
    public int orderID;
    public Customer orderCustomer;
    public DeliveryPoint orderDeliveryPoint;


    public Order(int id, Customer orderCust, DeliveryPoint dp){
        orderID = id;
        orderCustomer = orderCust;
        orderDeliveryPoint = dp;
    }

    public void storeToDB(){
        orderID = Adapter.DBInstance().insertNewOrder(this);
    }

    public String toString(){
        return "Order No: " + orderID + "\n" + orderDeliveryPoint + "\nCustomer: " + orderCustomer;
    }

    public String itemsToString() {
        String str = "";
        for (int i = 0; i < items.size(); i++){
            str = str + items.get(i).toString();
        }
        return str;
    }

    /**
     * This method returns the total price of an order
     * @return
     */
    public int totalPriceOfOrder(){
        int totalPriceOrder = 0;
        for(int i=0; i<items.size(); i++){
            totalPriceOrder += items.get(i).price();
        }
        return totalPriceOrder;
    }

    /**
     * This method checks if an item is found in an order.
     * @param itemID
     * @return
     */
    public boolean isItemInOrder(int itemID){
        for(int i=0; i<items.size(); i++){
            if(items.get(i).itemID == itemID){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param id
     * @return
     */
    public LaundryItem getItemFromItemID(int id){
        LaundryItem tmpItem = null;
        for(int i=0; i<items.size(); i++){
            if(id == items.get(i).itemID){
                tmpItem = items.get(i);
            }
        }
        return tmpItem;
    }

    /**
     * This method returns true if all items in an order are cleaned.
     * @return
     */
    public boolean statusOfOrderAllCleaned(){
        for(int i=0; i<items.size(); i++){
            if(items.get(i).itemStatus == false){
                return false;
            }
        }
        return true;
    }

    /**
     * This method returns true if all items in an order are not cleaned yet.
     * @return
     */
    public boolean statusOfOrderAllNoncleaned(){
        for(int i=0; i<items.size(); i++){
            if(items.get(i).itemStatus == true){
                return false;
            }
        }
        return true;
    }


   /* public void sendEmail(){
        String to = "abcd@gmail.com";
        String from = "cleaningcentral@fraemohs.de";
        String host = "fraemohs.de";
        Properties properties = System.getProperties();
        properties.setProperty("smtp.host", host);
        properties.setProperty("mail.user", "axelk");
        properties.setProperty("mail.password", "BubenHund");
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Order confirmation");
            message.setText("This is actual message");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }*/

}
