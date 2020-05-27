package Application;

import Domain.Adapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class ControllerWelcome {

    public void handleButtonDeliveryPoint(ActionEvent event) throws IOException {
        Adapter.DBInstance().tabledataToFetch(); // test
        System.out.println("ArrayList of order data objects from cleaningCentral class: " + Adapter.cleaningCentralInstance().orderDataObjects); // test print
        //System.out.println("arraylist of orders from the cleaning central class: " + Adapter.cleaningCentralInstance().orders); //test print

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
