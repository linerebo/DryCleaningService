package Application;

import Domain.Adapter;
import Domain.Customer;
import Domain.DeliveryPoint;

import Domain.SystemUser.SystemUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerDeliveryPointMenu {

    public DeliveryPoint dp;
    public SystemUser su;
    @FXML MenuButton menuButton1;
    @FXML Label labelDeliveryPoint;

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

    public void handleButtonHandIn(ActionEvent event) throws IOException {
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

    public void handleButtonHandOut(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointHandOut.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointHandOut controller = (ControllerDeliveryPointHandOut) loader.getController();
        controller.dp = dp;
        controller.su = su;
        controller.labelDeliveryPoint.setText(controller.dp.toString());
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }
}
