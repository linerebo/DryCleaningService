package Domain;

        import Domain.LaundryItemTypes.LaundryItem;
        import java.util.ArrayList;
        import java.util.*;
        //import javax.mail.*;
        //import javax.mail.internet.*;
        import javax.activation.*;

public class Order {
    public ArrayList<LaundryItem> items = new ArrayList<>();        //TODO get items from DB?
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

    public int totalPriceOfOrder(){
        int totalPriceOrder = 0;
        for(int i=0; i<items.size(); i++){
            totalPriceOrder += items.get(i).price();
        }
        return totalPriceOrder;
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
