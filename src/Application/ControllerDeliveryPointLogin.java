package Application;

import Domain.Adapter;
import Domain.DeliveryPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.*;

import java.io.IOException;

public class ControllerDeliveryPointLogin {

    @FXML
    MenuButton menuButton1;
    @FXML
    MenuItem menuItem3;
    @FXML
    ComboBox deliveryPointCombobox;
    @FXML
    TextField txtFieldInputDeliveryPointPassword, txtFieldInputDeliveryPointID;
    @FXML
    Button buttonGoDeliveryPointLogin;

    public ObservableList<String> deliveryPoints = FXCollections.observableArrayList();

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleButtonGoDeliveryPointLogin(ActionEvent event) throws IOException {

        // "security" step check password first

        // getText from text field txtFieldInputCanteenPassword
        String inPassword = txtFieldInputDeliveryPointPassword.getText();
        System.out.println("input: " + inPassword); // test -gets the right input? yes

        // get Password from DB (string)
        String properPassword = "ab";    //.getPasswordDeliveryPoint();
        System.out.println("password from DB: " + properPassword); // test - gets the right password from DB? yes

        // compare password, change scene if true
        if (properPassword.equals(inPassword)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointMenu.fxml"));
            Parent menuScreen = loader.load();
            ControllerDeliveryPointMenu controller = (ControllerDeliveryPointMenu) loader.getController();
            controller.dp = Adapter.cleaningCentralInstance().getDeliveryPointFromID(Integer.parseInt(txtFieldInputDeliveryPointID.getText()));
            controller.labelDeliveryPointID.setText(txtFieldInputDeliveryPointID.getText());
            Scene scene = new Scene(menuScreen);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } else {
            txtFieldInputDeliveryPointPassword.setText("wrong password");
        }
    }
}
