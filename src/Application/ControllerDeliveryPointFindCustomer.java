package Application;

import Domain.Adapter;
import Domain.Customer;
import Domain.DeliveryPoint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerDeliveryPointFindCustomer implements Initializable {

    DeliveryPoint dp;

    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldInputCustomerName;
    @FXML ListView listViewShowCustomers, listViewCustomerCard;
    @FXML Label labelCustomerName, labelCustomerPhone, labelCustomerEmail;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        listViewShowCustomers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
                Customer selectedCustomer = (Customer) listViewShowCustomers.getSelectionModel().getSelectedItem();
                labelCustomerName.setText(selectedCustomer.customerFirstName + " " + selectedCustomer.customerLastName);
                labelCustomerPhone.setText(selectedCustomer.customerPhoneNumber);
                labelCustomerEmail.setText(selectedCustomer.customerEmail);
            }
        });
    }

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
        controller.labelDeliveryPoint.setText(controller.dp.toString());
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
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

}
