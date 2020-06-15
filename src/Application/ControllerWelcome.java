package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;
import javafx.fxml.FXML;


public class ControllerWelcome {

    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;

    public void handleButtonDeliveryPoint(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/DeliveryPointLogin.fxml"));
        Scene balanceScene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(balanceScene);
        window.show();
    }
    public void handleButtonDriver(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/DriverLogin.fxml"));
        Scene balanceScene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(balanceScene);
        window.show();
    }
    public void handleButtonCentral(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/CleaningCentralLogin.fxml"));
        Scene balanceScene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(balanceScene);
        window.show();
    }
}
