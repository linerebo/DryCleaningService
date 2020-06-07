package Application;

import Domain.Adapter;
import Domain.Customer;
import Domain.DeliveryPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerDeliveryPointEditCustomer {

    public DeliveryPoint dp;
    public Customer selectedCustomer;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldEditName, txtFieldEditLastname, txtFieldEditPhone, txtFieldEditEmail;

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

    public void handleButtonSaveEditCustomer(ActionEvent event)throws IOException{
        String newFirstName = txtFieldEditName.getText();
        String newLastName = txtFieldEditLastname.getText();
        String newPhoneNo = txtFieldEditPhone.getText();
        String newEmail = txtFieldEditEmail.getText();
        Customer editCustomer = new Customer (selectedCustomer.customerID, newFirstName, newLastName, newPhoneNo, newEmail);
        //update DB
        //update customers
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointFindCustomer.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointFindCustomer controller = (ControllerDeliveryPointFindCustomer) loader.getController();
        controller.dp = dp;
        ObservableList<Customer> newCustomerList = FXCollections.observableArrayList();
        newCustomerList.add(editCustomer);
        controller.listViewShowCustomers.setItems(newCustomerList);
        controller.listViewShowCustomers.getSelectionModel().selectFirst();
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonCancelEditCustomer(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointFindCustomer.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointFindCustomer controller = (ControllerDeliveryPointFindCustomer) loader.getController();
        controller.dp = dp;
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        allCustomers.setAll(Adapter.cleaningCentralInstance().customers);
        controller.listViewShowCustomers.setItems(allCustomers);
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }
}
