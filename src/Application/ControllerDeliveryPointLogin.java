package Application;

import Domain.Adapter;
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

    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldInputDeliveryPointPassword, txtFieldInputDeliveryPointUserID, txtFieldInputDeliveryPointID;
    @FXML Button buttonGoDeliveryPointLogin;


    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleButtonGoDeliveryPointLogin(ActionEvent event) throws IOException {

        // "security" step check password first

        // getText from text field txtFieldInputDeliveryPointUserID
        String inputUserIDString = txtFieldInputDeliveryPointUserID.getText();
        int inputUserIDInt = Integer.parseInt(inputUserIDString);

        // getText from text field txtFieldInputDeliveryPointPassword
        String inPassword = txtFieldInputDeliveryPointPassword.getText();
        System.out.println("input: " + inPassword); // test -gets the right input? yes

        // get Password from DB (string)
        String properPassword = Adapter.DBInstance().getPasswordSystemUser(inputUserIDInt); // takes in systemUserID as variable from the textfield, see above.
        System.out.println("password from DB: " + properPassword); // test - gets the right password from DB? yes

        // compare password, change scene if true
        if (properPassword.equals(inPassword)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointMenu.fxml"));
            Parent menuScreen = loader.load();
            ControllerDeliveryPointMenu controller = (ControllerDeliveryPointMenu) loader.getController();
            controller.dp = Adapter.cleaningCentralInstance().getDeliveryPointFromID(Integer.parseInt(txtFieldInputDeliveryPointID.getText()));
            if (controller.dp.deliveryPointID == 0) {
                txtFieldInputDeliveryPointID.setText("wrong Delivery Point ID");
            } else {
                controller.labelDeliveryPoint.setText(controller.dp.toString());
                Scene scene = new Scene(menuScreen);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        else{
            txtFieldInputDeliveryPointPassword.setText("wrong password");
        }
    }
}
