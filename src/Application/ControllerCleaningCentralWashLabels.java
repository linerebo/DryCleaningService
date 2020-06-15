package Application;

import Domain.Adapter;
import Domain.Order;
import Domain.SystemUser.SystemUser;
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

    public SystemUser su;
    public int selectedOrderID;
    public Order selectedOrder;
    @FXML ListView listViewOrderDetails;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldEnterOrderNo;


    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonLogout(ActionEvent event) throws IOException{
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/CleaningCentralLogin.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/CleaningCentralMenu.fxml"));
        Parent menuScreen = loader.load();
        ControllerCleaningCentralMenu controller = loader.getController();
        controller.su = su;
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo(){
        ObservableList itemsInOrder = FXCollections.observableArrayList();
        //get Order ID from textFieldEnterOrderNo
        selectedOrderID = Integer.parseInt(txtFieldEnterOrderNo.getText());
        //get the Order object from OrderID
        selectedOrder = Adapter.cleaningCentralInstance().getOrderFromID(selectedOrderID);
        //add all Items from Order to ObservableList in order to show in ListView
        itemsInOrder.setAll(selectedOrder.items);
        listViewOrderDetails.setItems(itemsInOrder);
    }

    /**
     * This method prints out a washable label for each item in the selected order
     */
    public void handleButtonPrintLabels() {
        for(int i = 0; i < selectedOrder.items.size(); i++){
            System.out.println("\nPrint Washable label:\n" +
                    "************************************\n" +
                    (i+1) + " of " + selectedOrder.items.size() +
                    "\nOrder No: " + selectedOrderID + "\n" +
                    selectedOrder.items.get(i).toWashableLabel() + "\n" +
                    "************************************\n");
        }
        Adapter.DBInstance().insertNewEvent(selectedOrder.orderID, 18, su.systemUserID); // event type 18 for "cleaning process started"
    }
}
