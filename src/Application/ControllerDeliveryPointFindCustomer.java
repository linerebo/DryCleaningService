package Application;

import Domain.Adapter;
import Domain.Customer;
import Domain.DeliveryPoint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

//import java.awt.*;
//import java.awt.event.KeyEvent;
import java.io.IOException;

public class ControllerDeliveryPointFindCustomer {

    DeliveryPoint dp;

    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldInputCustomerName;
    @FXML ListView listViewShowCustomers, listViewCustomerCard;
    @FXML Label labelCustomerName, labelCustomerPhone, labelCustomerEmail;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }


    public void handleButtonNewOrder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointHandIn.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointHandIn controller = (ControllerDeliveryPointHandIn) loader.getController();
        controller.dp = dp;
        controller.selectedCustomer = (Customer) listViewShowCustomers.getSelectionModel().getSelectedItem();
        controller.labelCustomerInfo.setText(String.valueOf(controller.selectedCustomer));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonCreateNewCustomer (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointNewCustomer.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointNewCustomer controller = (ControllerDeliveryPointNewCustomer) loader.getController();
        controller.dp = dp;
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo(ActionEvent event){
        String text = txtFieldInputCustomerName.getText();
        listViewShowCustomers.setItems(Adapter.cleaningCentralInstance().getCustomersFromName(text));
        Customer selectedCustomer = (Customer) listViewShowCustomers.getSelectionModel().getSelectedItem();
        labelCustomerName.setText(selectedCustomer.customerFirstName + " " + selectedCustomer.customerLastName);
        labelCustomerPhone.setText(selectedCustomer.customerPhoneNumber);
        labelCustomerEmail.setText(selectedCustomer.customerEmail);
    }

    public void handleButtonEditCustomer(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointEditCustomer.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointEditCustomer controller = (ControllerDeliveryPointEditCustomer) loader.getController();
        controller.dp = dp;
        controller.selectedCustomer = (Customer) listViewShowCustomers.getSelectionModel().getSelectedItem();
        controller.txtFieldEditName.setText(controller.selectedCustomer.customerFirstName);
        controller.txtFieldEditLastname.setText(controller.selectedCustomer.customerLastName);
        controller.txtFieldEditPhone.setText(String.valueOf(controller.selectedCustomer.customerPhoneNumber));
        controller.txtFieldEditEmail.setText(String.valueOf(controller.selectedCustomer.customerEmail));

        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

}
