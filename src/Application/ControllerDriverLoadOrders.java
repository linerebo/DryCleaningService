package Application;

import Domain.Adapter;
import Domain.DeliveryPoint;
import Domain.Order;
import Domain.SystemUser.SystemUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ControllerDriverLoadOrders {

    public SystemUser su;
    ObservableList waitingOrderIDs;
    ArrayList<Integer> ordersLoad = new ArrayList<>();

    @FXML Label labelShowDriverNameAndID, labelShowDPInfo;
    @FXML MenuButton menuButton1;
    @FXML TextField textFieldInputDpID;
    @FXML ListView listViewWaitingOrders, listViewCompletedLoading;
    @FXML TextArea textAreaSelectedOrder;

    public void handleButtonHome(ActionEvent actionEvent) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo() {
        // get delivery point ID from textFieldInputDpID
        int inputDeliveryPointID = Integer.parseInt(textFieldInputDpID.getText());
        // check if the inserted delivery Point is in the system.
       DeliveryPoint dp = Adapter.cleaningCentralInstance().getDeliveryPointFromID(inputDeliveryPointID);

       if (dp.deliveryPointID == 0) {
           textFieldInputDpID.setText("invalid delivery point ID");
       } else {
           labelShowDPInfo.setText(dp.deliveryPointID + ", " + dp.address);
           waitingOrderIDs = Adapter.cleaningCentralInstance().getWaitingOrdersFromDeliveryPoint(dp.deliveryPointID);
           listViewWaitingOrders.setItems(waitingOrderIDs);
       }
    }

    public void handleListViewWaitingOrders() {
        String selectedOrder = String.valueOf(listViewWaitingOrders.getSelectionModel().getSelectedItem());
        textAreaSelectedOrder.setText(selectedOrder);
    }

    public void handleButtonLoadOnTruck() {
        int orderLoaded = Integer.parseInt(textAreaSelectedOrder.getText());
        ordersLoad.add(orderLoaded);
        listViewCompletedLoading.setItems(FXCollections.observableArrayList(ordersLoad));
        //the chosen order disappears from the listViewWaitingOrders
        waitingOrderIDs.remove(listViewWaitingOrders.getSelectionModel().getSelectedItem());
    }

    public void handleButtonLoadingCompleted(ActionEvent event) {
        for (int i = ordersLoad.size() -1; i >= 0; i--) {
             int oID = ordersLoad.get(i);
             Adapter.DBInstance().insertNewEvent(oID, 16, su.systemUserID);
             Adapter.cleaningCentralInstance().eventHistories = Adapter.DBInstance().getEventHistoriesFromDB(); // update list of eventHistories
        }

        //go back to driver Menu
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DriverMenu.fxml"));
            Parent menuScreen = loader.load();
            ControllerDriverMenu controller = (ControllerDriverMenu) loader.getController();
            controller.su = su;
            controller.ordersOnTruck = Adapter.cleaningCentralInstance().getOrderObjectsOnTruck(su.systemUserID);
            //make an arraylist of orderIDs
            ArrayList<Integer> orderIDsToDisplay = new ArrayList<>();
            for(int i = controller.ordersOnTruck.size() - 1; i >= 0; i--) {
                Order j = controller.ordersOnTruck.get(i);
                orderIDsToDisplay.add(j.orderID);
            }
            //make an observable list to be inserted to the listview below
            ObservableList observableOderIDs = FXCollections.observableArrayList(orderIDsToDisplay);
            controller.listViewOrdersCurrentlyLoaded.setItems(observableOderIDs);
            controller.LabelCurrentLoadedOrders.setText("Hello " + controller.su.systemUserFirstName + ", you have currently loaded these orders:");
            controller.labelTotalOrdersLoaded.setText("Total: " + controller.ordersOnTruck.size());
            Scene scene = new Scene(menuScreen);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (Exception e) {
            System.out.println("could not go back to driver menu");
        }
    }
}
