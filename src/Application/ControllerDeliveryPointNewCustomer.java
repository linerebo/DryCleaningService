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


public class ControllerDeliveryPointNewCustomer {
    DeliveryPoint dp;

    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldinputFirstName,txtFieldinputLastName,txtFieldinputPhoneNo, txtFieldinputEmail;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointFindCustomer.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointFindCustomer controller = (ControllerDeliveryPointFindCustomer) loader.getController();
        controller.dp = dp;
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonSave(ActionEvent event) throws IOException {
        String newFirstName = txtFieldinputFirstName.getText();
        String newLastName = txtFieldinputLastName.getText();
        String newPhoneNo = txtFieldinputPhoneNo.getText();
        String newEmail = txtFieldinputEmail.getText();
        Customer newCustomer = new Customer(0, newFirstName, newLastName, newPhoneNo, newEmail);
        newCustomer.storeToDB();
        Adapter.cleaningCentralInstance().customers.add(newCustomer);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointFindCustomer.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointFindCustomer controller = (ControllerDeliveryPointFindCustomer) loader.getController();
        controller.dp = dp;
        ObservableList<Customer> newCustomerList = FXCollections.observableArrayList();
        newCustomerList.add(newCustomer);
        controller.listViewShowCustomers.setItems(newCustomerList);
        controller.listViewShowCustomers.getSelectionModel().selectFirst();
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

}
