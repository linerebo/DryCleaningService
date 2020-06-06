package Application;


import Domain.Adapter;
import Domain.LaundryItemTypes.LaundryItem;
import Domain.Order;
import Domain.SystemUser.SystemUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;

public class ControllerCleaningCentralItemStatus {

    public SystemUser su;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML TextField txtFieldEnterItemNo;
    @FXML Label labelItemDetails, labelItemStatus, labelOrderStatusFirstItem, labelOrderStatusAllCleaned;
    LaundryItem scannedItem;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo(){
        labelItemStatus.setText("");
        labelOrderStatusFirstItem.setText("");
        labelOrderStatusAllCleaned.setText("");
        labelOrderStatusAllCleaned.setText("");
        labelOrderStatusFirstItem.setText("");
        int scannedItemID = Integer.parseInt(txtFieldEnterItemNo.getText());
        scannedItem = Adapter.cleaningCentralInstance().getItemFromID(scannedItemID);
        labelItemDetails.setText(scannedItem.toWashableLabel());
    }

    public void handleButtonItemCleaned(){
        Order orderInProcess = scannedItem.getOrderFromItem();
        if(orderInProcess.statusOfOrderAllNoncleaned()){
            labelOrderStatusFirstItem.setText("First item in this order is cleaned. \nLabel for return bag is generated");
            System.out.println("\nPrint label for return bag: \n" +
                               "**************************************************\n" +
                               orderInProcess.toString() +
                                "\namount of items in order: " + orderInProcess.items.size() +
                                "\nRoute: " + orderInProcess.orderDeliveryPoint.route +
                                "\n**************************************************\n");
        }
        scannedItem.itemStatus = true;
        scannedItem.updateToDB();
        labelItemStatus.setText("The laundry item was cleaned\nPlease sort to order.\nOrder No: " + orderInProcess.orderID);
        if(orderInProcess.statusOfOrderAllCleaned()){
            labelOrderStatusAllCleaned.setText("All items in this order are cleaned. \nThe Order is now ready for pick-up");
            // update Events for the whole order to finished cleaning
            Adapter.DBInstance().insertNewEvent(orderInProcess.orderID, 18, su.systemUserID); // event type 18 for "cleaning process finished"
        }
    }
}
