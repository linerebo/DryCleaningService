package Application;

import Domain.Order;
import Domain.SystemUser.SystemUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerDriverUnloadOrders {

    public SystemUser su;
    public ObservableList<Order> ordersOnTruck;

    @FXML MenuButton menuButton1;
    @FXML Button buttonUnloadingCompleted, buttonUnloadFromTruck, buttonGo;
    @FXML ListView listViewOrdersOnTruck, listViewCompletedUnloading;
    @FXML TextField textFieldInputDpID;
    @FXML TextArea textAreaOrderToUnload;

    @FXML Label labelShowDPInfo, labelShowDriverNameAndID;

    public void handleButtonHome(ActionEvent actionEvent) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo() {
        listViewOrdersOnTruck.setItems(ordersOnTruck);
    }

    /**
     * Will set the selected order from the listview to the middle textfield for further processing.
     */
    public void handleListViewOrdersOnTruck() {
        String selectedOrder = String.valueOf(listViewOrdersOnTruck.getSelectionModel().getSelectedItem());
        textAreaOrderToUnload.setText(selectedOrder);
    }

    public void handleButtonUnloadFromTruck() {
        //Order orderToUnload = textAreaOrderToUnload.
    }

    public void handleButtonUnloadingCompleted() {

    }


}
