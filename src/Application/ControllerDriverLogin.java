package Application;

import Domain.Adapter;
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

public class ControllerDriverLogin {

    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFldInputDriverID, txtFldInputDriverPassword;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
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
            controller.ordersOnTruck = Adapter.cleaningCentralInstance().getOrderObjectsOnTruck(controller.su.systemUserID);
            controller.observableOrderIDsOnTruck = Adapter.cleaningCentralInstance().getOrderIDsOnTruck(controller.su.systemUserID);

            // check if it is a driver acting as system user
            if (controller.su.departmentID != 12) { // 12 is the departmentID of the driver department.
                txtFldInputDriverID.setText("This user is not a driver.");
            } else {
                // go to next page with these parameters:

                controller.listViewOrdersCurrentlyLoaded.setItems(controller.observableOrderIDsOnTruck);
                controller.LabelCurrentLoadedOrders.setText("Hello " + controller.su.systemUserFirstName + ", you have currently loaded these orders:");
                controller.labelTotalOrdersLoaded.setText("Total: " + controller.ordersOnTruck.size());
                Scene scene = new Scene(menuScreen);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } else {
            txtFldInputDriverPassword.setText("wrong password");
        }
    }
}
