package Application;

import Domain.Adapter;
import Domain.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;


import java.io.IOException;

public class ControllerCleaningCentralWashLabels {

    @FXML ListView listViewOrderDetails;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldEnterOrderNo;
    int selectedOrderID;
    Order selectedOrder;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo(){
        ObservableList itemsInOrder = FXCollections.observableArrayList();
        selectedOrderID = Integer.parseInt(txtFieldEnterOrderNo.getText());
        selectedOrder = Adapter.cleaningCentralInstance().getOrderFromID(selectedOrderID);
        itemsInOrder.setAll(selectedOrder.items);
        listViewOrderDetails.setItems(itemsInOrder);
    }

    /**
     * This method prints out a washable label for each item in the selected order
     */
    public void handleButtonPrintLabels(){
        for(int i = 0; i < selectedOrder.items.size(); i++){
        System.out.println("\nPrint Washable label:\n" +
                "************************************\n" +
                "Order No: " + selectedOrderID + "\n" +
                selectedOrder.items.get(i).toWashableLabel() + "\n" +
                "************************************\n");
        }
    }
}
