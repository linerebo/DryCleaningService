package Application;

import Domain.Adapter;
import Domain.DeliveryPoint;
import Domain.Order;
import Domain.SystemUser.SystemUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerDeliveryPointHandOut {

    public DeliveryPoint dp;
    public SystemUser su;
    public Order orderToBeHandedOut;
    public int orderToBeHandedOutID;
    @FXML Label labelDeliveryPoint, labelOrderNo, labelCustomer, labelWrongDP;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldEnterOrderNo;
    @FXML ListView listViewOrderHistory;
    @FXML Button buttonPrintInvoice;

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
        labelWrongDP.setText("");
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
        // check if the order is at the right delivery point and enable/disable print invoice button accordingly
        if(orderToBeHandedOut.orderDeliveryPoint.deliveryPointID == dp.deliveryPointID) {
            System.out.println("Delivery points are matching");
            buttonPrintInvoice.setVisible(true);
        } else {
            System.out.println("Delivery points are not matching");
            buttonPrintInvoice.setVisible(false);
            labelWrongDP.setText("The order does not belong to this delivery point. Print invoice is not possible.");
        }
    }

    public void handleButtonPrintInvoice(ActionEvent event) throws IOException{
        // updates eventhistory for orderToBeHandedOut
        Adapter.DBInstance().insertNewEvent(orderToBeHandedOutID, 22, su.systemUserID);
        System.out.println("Print Invoice: ");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++\n" +
                           "Invoice:\n" + "Order No: " + orderToBeHandedOut.orderID +
                           "\nCustomer: " + orderToBeHandedOut.orderCustomer.toString() +"\n" +
                           orderToBeHandedOut.orderDeliveryPoint.toString() + "\n" +
                           orderToBeHandedOut.itemsToString() +
                           "\nTotal price: " + orderToBeHandedOut.totalPriceOfOrder() + " Kroner\n" +
                           "+++++++++++++++++++++++++++++++++++++++++++++++\n");
        //go back to delivery point menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointMenu.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointMenu controller = (ControllerDeliveryPointMenu) loader.getController();
        controller.dp = dp;
        controller.labelDeliveryPoint.setText(controller.dp.toString());
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }
}
