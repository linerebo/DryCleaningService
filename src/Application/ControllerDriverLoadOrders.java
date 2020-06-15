package Application;

import Domain.Adapter;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerDriverLoadOrders {

    public SystemUser su;
    ObservableList waitingOrderIDs;
    ObservableList cleanOrderIDsWaiting;
    ArrayList<Integer> ordersLoad = new ArrayList<>();
    public ObservableList observableOrderIDsOnTruck;
    @FXML Label labelShowDriverNameAndID, labelShowLocationInfo;
    @FXML MenuButton menuButton1;
    @FXML TextField textFieldInputDpID;
    @FXML ListView listViewWaitingOrders, listViewCompletedLoading;
    @FXML TextArea textAreaSelectedOrder;
    String currentLocation;

    public void handleButtonHome(ActionEvent actionEvent) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonLogout(ActionEvent event) throws IOException{
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/DriverLogin.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DriverMenu.fxml"));
        Parent menuScreen = loader.load();
        ControllerDriverMenu controller = (ControllerDriverMenu) loader.getController();
        controller.su = su;
        controller.ordersOnTruck = Adapter.cleaningCentralInstance().getOrderObjectsOnTruck(su.systemUserID);
        controller.observableOrderIDsOnTruck = Adapter.cleaningCentralInstance().getOrderIDsOnTruck(su.systemUserID);
        controller.listViewOrdersCurrentlyLoaded.setItems( Adapter.cleaningCentralInstance().getOrderIDsOnTruck(su.systemUserID));
        controller.LabelCurrentLoadedOrders.setText("Hello " + controller.su.systemUserFirstName + ", you have currently loaded these orders:");
        controller.labelTotalOrdersLoaded.setText("Total: " + controller.ordersOnTruck.size());
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo() {
        currentLocation = "Delivery Point";
        // get delivery point ID from textFieldInputDpID
        int inputDeliveryPointID = Integer.parseInt(textFieldInputDpID.getText());
        // check if the inserted delivery Point is in the system.
       DeliveryPoint dp = Adapter.cleaningCentralInstance().getDeliveryPointFromID(inputDeliveryPointID);

       if (dp.deliveryPointID == 0) {
           textFieldInputDpID.setText("invalid delivery point ID");
       } else {
           labelShowLocationInfo.setText(dp.deliveryPointID + ", " + dp.address);
           waitingOrderIDs = Adapter.cleaningCentralInstance().getWaitingOrdersFromDeliveryPoint(dp.deliveryPointID);
           listViewWaitingOrders.setItems(waitingOrderIDs);
       }
    }

    public void handleButtonLocationCleaningCentral() {
        currentLocation = "Cleaning Central";
        labelShowLocationInfo.setText("Cleaning Central");
        cleanOrderIDsWaiting = Adapter.cleaningCentralInstance().getWaitingOrdersAtCleaningCentral();
        listViewWaitingOrders.setItems(cleanOrderIDsWaiting);
    }

    /**
     * By selecting an order, it will be moved to the field in the middle for individual further processing.
     * This also simulates the scanning process.
     */
    public void handleListViewWaitingOrders() {
        String selectedOrder = String.valueOf(listViewWaitingOrders.getSelectionModel().getSelectedItem());
        textAreaSelectedOrder.setText(selectedOrder);
    }

    /**
     * The method adds the order to the listview at the right for the loading process and removes the order from
     * the waiting order list to the left.
     */
    public void handleButtonLoadOnTruck() {
        int orderLoaded = Integer.parseInt(textAreaSelectedOrder.getText());
        ordersLoad.add(orderLoaded);
        listViewCompletedLoading.setItems(FXCollections.observableArrayList(ordersLoad));
        //the chosen order disappears from the listViewWaitingOrders
        if(currentLocation.equals("Delivery Point")) {
            waitingOrderIDs.remove(listViewWaitingOrders.getSelectionModel().getSelectedItem());
        } else {
            // the current location is the cleaning central
            cleanOrderIDsWaiting.remove(listViewWaitingOrders.getSelectionModel().getSelectedItem());
        }
    }

    public void handleButtonLoadingCompleted(ActionEvent event) {
        if (currentLocation.equals("Delivery Point")) {
            // insert a new event for every order that got added on truck. event type 16 = order on pick-up transportation
            for (int i = ordersLoad.size() - 1; i >= 0; i--) {
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
                controller.observableOrderIDsOnTruck = Adapter.cleaningCentralInstance().getOrderIDsOnTruck(su.systemUserID);
                controller.listViewOrdersCurrentlyLoaded.setItems(Adapter.cleaningCentralInstance().getOrderIDsOnTruck(su.systemUserID));
                controller.LabelCurrentLoadedOrders.setText("Hello " + controller.su.systemUserFirstName + ", you have currently loaded these orders:");
                controller.labelTotalOrdersLoaded.setText("Total: " + controller.ordersOnTruck.size());
                Scene scene = new Scene(menuScreen);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

            } catch (Exception e) {
                System.out.println("could not go back to driver menu");
            }
        } else if (currentLocation.equals("Cleaning Central")) {
            // insert new event "return transportation = event type ID 19"
            for (int i = ordersLoad.size() - 1; i >= 0; i--) {
                int oID = ordersLoad.get(i);
                Adapter.DBInstance().insertNewEvent(oID, 20, su.systemUserID);
                // update list of eventHistories
                Adapter.cleaningCentralInstance().eventHistories = Adapter.DBInstance().getEventHistoriesFromDB();
            }
            //go back to driver Menu
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DriverMenu.fxml"));
                Parent menuScreen = loader.load();
                ControllerDriverMenu controller = (ControllerDriverMenu) loader.getController();
                controller.su = su;
                controller.ordersOnTruck = Adapter.cleaningCentralInstance().getOrderObjectsOnTruck(su.systemUserID);
                controller.observableOrderIDsOnTruck = Adapter.cleaningCentralInstance().getOrderIDsOnTruck(su.systemUserID);
                controller.listViewOrdersCurrentlyLoaded.setItems( Adapter.cleaningCentralInstance().getOrderIDsOnTruck(su.systemUserID));
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
}
