package Application;

import Domain.Customer;
import Domain.DeliveryPoint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;

public class ControllerDeliveryPointHandIn {

    DeliveryPoint dp;
    Customer selectedCustomer;

    @FXML
    MenuButton menuButton1;
    @FXML
    MenuItem menuItem3;
    @FXML
    RadioButton radiobuttonShirt, radiobuttonJacket, radiobuttonTrousers, radiobuttonTshirt, radiobuttonCoat, radiobuttonDress, radiobuttonCarpet;
    @FXML
    ListView listViewType, listViewColor, listViewPrice;
    @FXML
    Label labelCustomerInfo;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonEdit(){
    }


}
