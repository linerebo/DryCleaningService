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

public class ControllerDriverUnloadOrders {

    public SystemUser su;
    public ObservableList<Order> ordersOnTruck;
    public ObservableList observableOrderIDsOnTruck;
    ArrayList<Integer> ordersUnload = new ArrayList<>();

    @FXML
    MenuButton menuButton1;
    @FXML
    Button buttonUnloadingCompleted, buttonUnloadFromTruck, buttonGo;
    @FXML
    ListView listViewOrdersOnTruck, listViewCompletedUnloading;
    @FXML
    TextField textFieldInputDpID;
    @FXML
    TextArea textAreaOrderToUnload;
    @FXML
    Label labelShowLocationInfo, labelShowDriverNameAndID;

    String currentLocation;

    public void handleButtonHome(ActionEvent actionEvent) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonLogout(ActionEvent event) throws IOException {
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
        }
        listViewOrdersOnTruck.setItems(observableOrderIDsOnTruck);
    }

    public void handleButtonLocationCleaningCentral() {
        currentLocation = "Cleaning Central";
        labelShowLocationInfo.setText("Cleaning Central");
        listViewOrdersOnTruck.setItems(observableOrderIDsOnTruck);
        System.out.println("observable Orders IDs On Truck: " + observableOrderIDsOnTruck);
    }

    /**
     * Will set the selected order from the listview to the middle textfield for further processing.
     */
    public void handleListViewOrdersOnTruck() {
        String selectedOrder = String.valueOf(listViewOrdersOnTruck.getSelectionModel().getSelectedItem());
        textAreaOrderToUnload.setText(selectedOrder);
    }

    /**
     * The method adds the order to the listview at the right for the unloading process and removes the order from
     * the orders on truck list to the left.
     */
    public void handleButtonUnloadFromTruck() {
        int orderUnloaded = Integer.parseInt(textAreaOrderToUnload.getText());
        ordersUnload.add(orderUnloaded);
        listViewCompletedUnloading.setItems(FXCollections.observableArrayList(ordersUnload));
        //the chosen order disappears from the listViewWaitingOrders
        observableOrderIDsOnTruck.remove(listViewOrdersOnTruck.getSelectionModel().getSelectedItem());
    }

    /**
     * The method takes the current location of the user and decides whether to create an event for "order arrived at the Cleaning central"
     * or "order ready for hand out at the delivery point". It updates the list of orders on truck and goes back to the drivers menu.
     * @param event
     */
    public void handleButtonUnloadingCompleted(ActionEvent event) {
        if (currentLocation.equals("Delivery Point")) {
            // insert new event "order ready = 20"
            for (int i = ordersUnload.size() - 1; i >= 0; i--) {
                int oID = ordersUnload.get(i);
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
        } else if (currentLocation.equals("Cleaning Central")) {
            // insert new event "unload at cleaning central = 292"
            for (int i = ordersUnload.size() - 1; i >= 0; i--) {
                int oID = ordersUnload.get(i);
                Adapter.DBInstance().insertNewEvent(oID, 292, su.systemUserID);
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
