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

    public void handleButtonLogout(ActionEvent event) throws IOException{
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/CleaningCentralLogin.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonMenu(ActionEvent event) throws IOException{
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/CleaningCentralMenu.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonGo(){
        labelItemStatus.setText("");
        labelOrderStatusFirstItem.setText("");
        labelOrderStatusAllCleaned.setText("");
        //get Item ID from textFieldEnterItemNo
        int scannedItemID = Integer.parseInt(txtFieldEnterItemNo.getText());
        //get the Item object
        scannedItem = Adapter.cleaningCentralInstance().getItemFromID(scannedItemID);
        labelItemDetails.setText(scannedItem.toWashableLabel());
    }

    public void handleButtonItemCleaned(){
        //get the Order where scannedItem belongs
        Order orderInProcess = scannedItem.getOrderFromItem();
        //checks if it is the first Item in the Order
        if(orderInProcess.statusOfOrderAllNoncleaned()){
            labelOrderStatusFirstItem.setText("First item in this order is cleaned. \nLabel for return bag is generated");
            System.out.println("\nPrint label for return bag: \n" +
                               "**************************************************\n" +
                               orderInProcess.toString() +
                                "\namount of items in order: " + orderInProcess.items.size() +
                                "\nRoute: " + orderInProcess.orderDeliveryPoint.route +
                                "\n**************************************************\n");
        }
        //set Item status to true
        scannedItem.itemStatus = true;
        scannedItem.updateToDB();
        labelItemStatus.setText("The laundry item was cleaned\nPlease sort to order.\nOrder No: " + orderInProcess.orderID);
        //check if it is the last Item in the the Order
        if(orderInProcess.statusOfOrderAllCleaned()){
            labelOrderStatusAllCleaned.setText("All items in this order are cleaned. \nThe Order is now ready for pick-up");
            // update EventHistories
            Adapter.DBInstance().getEventHistoriesFromDB();
            //insert new event
            Adapter.DBInstance().insertNewEvent(orderInProcess.orderID, 19, su.systemUserID); // event type 19 for "cleaning process finished"
        }
    }
}
