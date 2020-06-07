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

public class ControllerCleaningCentralLogin {

    @FXML MenuButton menuButton3;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldCleaningEmployeeID, txtFieldCleaningCentralPassword;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton3.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGoCentralLogin(ActionEvent event) throws IOException {
        // "security" step check password first

        // getText from text field txtFieldCleaningEmployeeID
        String inputUserIDString = txtFieldCleaningEmployeeID.getText();
        int inputUserIDInt = Integer.parseInt(inputUserIDString);

        // getText from text field txtFieldInputCanteenPassword
        String inPassword = txtFieldCleaningCentralPassword.getText();
        System.out.println("input: " + inPassword); // test -gets the right input? yes

        // get Password from DB (string)
        //String properPassword = "ab";    //.getPasswordDeliveryPoint();
        String properPassword = Adapter.DBInstance().getPasswordSystemUser(inputUserIDInt); // takes in systemUserID as variable from the textfield, see above.
        System.out.println("password from DB: " + properPassword); // test - gets the right password from DB? yes

        // compare password, change scene if true
        if (properPassword.equals(inPassword)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/CleaningCentralMenu.fxml"));
            Parent menuScreen = loader.load();
            ControllerCleaningCentralMenu controller = (ControllerCleaningCentralMenu) loader.getController();
            controller.su = Adapter.cleaningCentralInstance().getSystemUserFromID(inputUserIDInt);
            if(controller.su.departmentID != 13 && controller.su.departmentID != 14){
                txtFieldCleaningEmployeeID.setText("This is whether a Desk Assistant nor a Manager.");
            } else {
                Scene scene = new Scene(menuScreen);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        else{
            txtFieldCleaningCentralPassword.setText("wrong password");
        }
    }
}
