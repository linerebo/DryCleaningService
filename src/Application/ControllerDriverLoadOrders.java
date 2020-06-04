package Application;

import Domain.Adapter;
import Domain.DeliveryPoint;
import Domain.SystemUser.SystemUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerDriverLoadOrders {

    public SystemUser su;

    @FXML
    Label labelShowDriverNameAndID, labelShowDPInfo;
    @FXML
    MenuButton menuButton1;
    @FXML
    TextField textFieldInputDpID;

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
           // TODO show all orders on the listview, which have the matching deliverypoint ID AND have a eventhistory with eventtype 15 and true status
       }
    }

}
