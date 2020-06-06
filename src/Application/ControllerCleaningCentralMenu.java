package Application;

import Domain.SystemUser.SystemUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerCleaningCentralMenu {

    public SystemUser su;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGenerateLabels(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/CleaningCentralWashLabels.fxml"));
        Parent menuScreen = loader.load();
        ControllerCleaningCentralWashLabels controller = (ControllerCleaningCentralWashLabels) loader.getController();
        controller.su = su;
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonProcessOrder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/CleaningCentralItemStatus.fxml"));
        Parent menuScreen = loader.load();
        ControllerCleaningCentralItemStatus controller = (ControllerCleaningCentralItemStatus) loader.getController();
        controller.su = su;
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonStatistics(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/CleaningCentralStatistics.fxml"));
        Parent menuScreen = loader.load();
        //ControllerCleaningCentralStatistics controller = (ControllerCleaningCentralStatistics) loader.getController();
        //controller.su = su;
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }
}
