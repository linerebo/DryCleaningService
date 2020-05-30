package Application;

import Domain.Adapter;
import Domain.Order;
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
import java.util.ArrayList;

public class ControllerDriverLogin {

    @FXML
    MenuButton menuButton2;
    @FXML
    MenuItem menuItem3;
    @FXML
    TextField txtFldInputDriverID, txtFldInputDriverPassword;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton2.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleBtnGoDriverLogin(ActionEvent event) throws IOException {
        int userDepartment;

        // getText from txtFldInputDriverID
        String inputDriverIDString = txtFldInputDriverID.getText();
        int inputDriverIDInt = Integer.parseInt(inputDriverIDString);

        // getText from text field txtFieldInputCanteenPassword
        String inPassword = txtFldInputDriverPassword.getText();
        System.out.println("input: " + inPassword); // test -gets the right input? yes

        // get password from DB (string)
        String properPassword = Adapter.DBInstance().getPasswordSystemUser(inputDriverIDInt); // takes in systemUserID as variable from the textfield, see above.

        // compare password, check if it is a driver and change scene if both true
        if (properPassword.equals(inPassword)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DriverMenu.fxml"));
            Parent menuScreen = loader.load();
            ControllerDriverMenu controller = (ControllerDriverMenu) loader.getController();
            controller.su = Adapter.cleaningCentralInstance().getSystemUserFromID(inputDriverIDInt);
            controller.ordersOnTruck = Adapter.cleaningCentralInstance().getOrderObjectsOnTruck(inputDriverIDInt);

            //make an arraylist of orderIDs
            ArrayList<Integer> orderIDsToDisplay = new ArrayList<>();
            for(int i = controller.ordersOnTruck.size() - 1; i >= 0; i--) {
                Order j = controller.ordersOnTruck.get(i);
                orderIDsToDisplay.add(j.orderID);
            }
            //make an observable list to be inserted to the listview below
            ObservableList observableOderIDs = FXCollections.observableArrayList(orderIDsToDisplay);

            // check if it is a driver acting as system user
            if (controller.su.departmentID != 2) { // 2 is the departmentID of the driver department.
                txtFldInputDriverID.setText("This user is not a driver.");
            } else {
                controller.listViewOrdersCurrentlyLoaded.setItems(observableOderIDs);
                controller.LabelCurrentLoadedOrders.setText("Hello " + controller.su.systemUserFirstName + ", you have currently loaded these orders:");
                controller.labelTotalOrdersLoaded.setText("Total: " + controller.ordersOnTruck.size());
                Scene scene = new Scene(menuScreen);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } else {
            txtFldInputDriverPassword.setText("wrong password");
        }
    }
}
