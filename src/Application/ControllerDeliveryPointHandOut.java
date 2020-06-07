package Application;

import Domain.Adapter;
import Domain.DeliveryPoint;
import Domain.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerDeliveryPointHandOut {

    DeliveryPoint dp;
    Order orderToBeHandedOut;
    int orderToBeHandedOutID;
    @FXML Label labelDeliveryPoint, labelOrderNo, labelCustomer;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldEnterOrderNo;
    @FXML ListView listViewOrderHistory;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonLogout(ActionEvent event) throws IOException{
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/DeliveryPointLogin.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointMenu.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointMenu controller = (ControllerDeliveryPointMenu) loader.getController();
        controller.dp = dp;
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo(){
        labelOrderNo.setText("");
        labelCustomer.setText("");
        orderToBeHandedOutID = Integer.parseInt(txtFieldEnterOrderNo.getText());
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
        listViewOrderHistory.setItems(Adapter.cleaningCentralInstance().getEventHistoryFromOrderID(orderToBeHandedOutID));
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
