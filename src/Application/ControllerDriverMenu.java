package Application;

import Domain.Order;
import Domain.SystemUser.SystemUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class ControllerDriverMenu {


    public SystemUser su;
    public ObservableList<Order> ordersOnTruck;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML Label LabelCurrentLoadedOrders, labelTotalOrdersLoaded;
    @FXML ListView listViewOrdersCurrentlyLoaded;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonLoadOrders(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DriverLoadOrders.fxml"));
        Parent menuScreen = loader.load();

        ControllerDriverLoadOrders controller = (ControllerDriverLoadOrders) loader.getController();
        controller.su = su;

        controller.labelShowDriverNameAndID.setText(su.systemUserFirstName + ", ID: " + su.systemUserID);
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }
}
