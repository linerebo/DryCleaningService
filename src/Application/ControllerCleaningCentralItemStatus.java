package Application;


import Domain.Adapter;
import Domain.LaundryItemTypes.LaundryItem;
import Domain.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;

public class ControllerCleaningCentralItemStatus {

    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldEnterItemNo;
    @FXML ListView listViewItemEventDetails;
    @FXML Label labelItemDetails;
    LaundryItem scannedItem;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo(){
        int scannedItemID = Integer.parseInt(txtFieldEnterItemNo.getText());
        scannedItem = Adapter.cleaningCentralInstance().getItemFromID(scannedItemID);
        labelItemDetails.setText(scannedItem.toWashableLabel());
    }

    public void handleButtonItemCleaned(){
        scannedItem.itemStatus = true;
    }
}
