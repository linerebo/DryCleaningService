package Application;

import Domain.*;
import Domain.LaundryItemTypes.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDeliveryPointHandIn implements Initializable {

    DeliveryPoint dp;
    Customer selectedCustomer;
    public ObservableList<LaundryItem> basket = FXCollections.observableArrayList();

    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML RadioButton radiobuttonShirt, radiobuttonBlazer, radiobuttonTrousers, radiobuttonTshirt, radiobuttonCoat, radiobuttonDress, radiobuttonCarpet;
    @FXML ListView listViewLaundryItem;
    @FXML Label labelCustomerInfo, labelInputValidation;
    @FXML ComboBox comboBoxColors;
    @FXML ToggleGroup groupItems;
    @FXML AnchorPane anchorPaneHandIn;
    ObservableList<Integer> carpetSizes = FXCollections.observableArrayList();
    ComboBox carpetSize = new ComboBox(carpetSizes);



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxColors.getItems().setAll(Colors.values());
        listViewLaundryItem.setItems(basket);

        groupItems.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                labelInputValidation.setText("");
                if (groupItems.getSelectedToggle() != null) {
                    removeAddedGUIElements();
                        String selectedButtonID = ((RadioButton) groupItems.getSelectedToggle()).getId();
                        switch (selectedButtonID) {
                            case "radiobuttonCarpet":
                                addCarpetGUIElements();
                                break;
                        }
                }
            }
        });
    }

    public void addCarpetGUIElements() {
        carpetSizes.addAll(5,10,15,20,25,30,35,40,45,50);
        carpetSize.setLayoutX(229);
        carpetSize.setLayoutY(500);
        carpetSize.setPromptText("Carpet Size m2");
        carpetSize.setStyle("-fx-background-color: #9dbfff; -fx-font-size: 23");
        anchorPaneHandIn.getChildren().add(carpetSize);
    }

    public void removeAddedGUIElements() {
        anchorPaneHandIn.getChildren().remove(carpetSize);
    }


    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonLogout(ActionEvent event) throws IOException{
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/DeliveryPointLogin.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointMenu.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointMenu controller = (ControllerDeliveryPointMenu) loader.getController();
        controller.dp = dp;
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonAddItem(ActionEvent event){
        labelInputValidation.setText("");
        if(groupItems.getSelectedToggle() == null){
            labelInputValidation.setText("Please select laundry type");
        }
        else if (comboBoxColors.getSelectionModel().isEmpty()) {
            labelInputValidation.setText("Please select color");
        }
        else {
            String selectedButtonID = ((RadioButton) groupItems.getSelectedToggle()).getId();
            String selectedColor = comboBoxColors.getSelectionModel().getSelectedItem().toString();
            switch (selectedButtonID) {
                case "radiobuttonShirt":
                    Shirt newShirt = new Shirt(selectedColor, false);
                    basket.add(newShirt);
                    break;
                case "radiobuttonBlazer":
                    Blazer newBlazer = new Blazer(selectedColor, false);
                    basket.add(newBlazer);
                    break;
                case "radiobuttonCarpet":
                    if (carpetSize.getSelectionModel().isEmpty()) {
                        labelInputValidation.setText("Please select carpet size");
                        break;
                    }
                    else {
                        Carpet newCarpet = new Carpet(selectedColor, false, (Integer) carpetSize.getSelectionModel().getSelectedItem());
                        basket.add(newCarpet);
                        break;
                    }
                case "radiobuttonCoat":
                    Coat newCoat = new Coat(selectedColor, false);
                    basket.add(newCoat);
                    System.out.println("We have a coat");
                    break;
                case "radiobuttonDress":
                    Dress newDress = new Dress(selectedColor, false);
                    basket.add(newDress);
                    break;
                case "radiobuttonTrousers":
                    Trousers newTrousers = new Trousers(selectedColor, false);
                    basket.add(newTrousers);
                    break;
                case "radiobuttonTshirt":
                    Tshirt newTshirt = new Tshirt(selectedColor, false);
                    basket.add(newTshirt);
                    break;
            }
        }
    }

    public void handleButtonDeleteItem(){
        basket.remove(listViewLaundryItem.getSelectionModel().getSelectedItem());
    }

    public void handleButtonCancel(){
        basket.clear();
    }

    /**
     *
     */
    public void handleButtonSaveAndPrint(ActionEvent event) throws IOException{
        for(int i=0; i<basket.size(); i++) {
            basket.get(i).storeToDB();
        }
        Order newOrder = new Order(0, selectedCustomer, dp);
        newOrder.items.addAll(basket);
        newOrder.storeToDB();           //newOrder is stored in DB
        Adapter.cleaningCentralInstance().orders.add(newOrder);     //newOrder is stored in orders
        System.out.println("Your Order was placed");
        System.out.println("Print Orderslip: \n" +
                "++++++++++++++++++++++++++++++++++++++++++++\n" +
                "Order Number: " + newOrder.orderID + "\n" +
                dp + "  \n" + "Customer: " + selectedCustomer + " customerID: " +
                selectedCustomer.customerID + "\n" + basket + "\nTotal Price: " + newOrder.totalPriceOfOrder() + " Kroner\n" +
                "++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Send Email to customer");
        basket.clear();
        //go back to delivery point menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Presentation/DeliveryPointMenu.fxml"));
        Parent menuScreen = loader.load();
        ControllerDeliveryPointMenu controller = (ControllerDeliveryPointMenu) loader.getController();
        controller.dp = dp;
        controller.labelDeliveryPoint.setText(controller.dp.toString());
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }
}
