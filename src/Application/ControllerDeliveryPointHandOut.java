package Application;

import Domain.Adapter;
import Domain.DeliveryPoint;
import Domain.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerDeliveryPointHandOut {

    DeliveryPoint dp;
    Order orderToBeHandedOut;

    @FXML Label labelDeliveryPoint, labelOrderNo, labelCustomer;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldEnterOrderNo;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo(){
        labelOrderNo.setText("");
        labelCustomer.setText("");
        int orderToBeHandedOutID = Integer.parseInt(txtFieldEnterOrderNo.getText());
        orderToBeHandedOut = Adapter.cleaningCentralInstance().getOrderFromID(orderToBeHandedOutID);
        if(orderToBeHandedOut == null) {
            labelOrderNo.setText("Order number not found");
        }
        else
            {labelOrderNo.setText(String.valueOf(orderToBeHandedOutID));
                labelCustomer.setText(orderToBeHandedOut.orderCustomer.toString()+"\n" +
                        "tel: " + orderToBeHandedOut.orderCustomer.customerPhoneNumber + "\n" +
                        "mail: " + orderToBeHandedOut.orderCustomer.customerEmail);
        }
        //TODO insert eventhistory for orderToBeHandedOut in label or listview?
    }

    public void handleButtonPrintInvoice(){
        //TODO updata eventhistory for orderToBeHandedOut
        System.out.println("Print Invoice: ");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++\n" +
                           "Invoice:\n" + "Order No: " + orderToBeHandedOut.orderID +
                           "\nCustomer: " + orderToBeHandedOut.orderCustomer.toString() +"\n" +
                           orderToBeHandedOut.orderDeliveryPoint.toString() + "\n" +
                           orderToBeHandedOut.itemsToString() +
                           "\nTotal price: " + orderToBeHandedOut.totalPriceOfOrder() + " Kroner\n" +
                           "+++++++++++++++++++++++++++++++++++++++++++++++\n");
    }
}
