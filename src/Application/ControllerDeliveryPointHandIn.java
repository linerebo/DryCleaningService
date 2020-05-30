package Application;

import Domain.Colors;
import Domain.Customer;
import Domain.DeliveryPoint;
import Domain.LaundryItemTypes.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML ListView listViewLaundryItem, listViewColor, listViewPrice;
    @FXML Label labelCustomerInfo;
    @FXML ComboBox comboBoxColors;
    @FXML ToggleGroup groupItems;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxColors.getItems().setAll(Colors.values());
        listViewLaundryItem.setItems(basket);

        groupItems.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (groupItems.getSelectedToggle() != null) {
                    removeAddedGUIElements();
                    String selectedButtonID = ((RadioButton) groupItems.getSelectedToggle()).getId();
                    switch (selectedButtonID){
                        case "radiobuttonCarpet":
                            addCarpetGUIElements();
                            break;
                    }
                }
            }
        });
    }

    public void addCarpetGUIElements() {
        System.out.println("zzzz");
    }

    public void removeAddedGUIElements() {
        System.out.println("puhhh");
    }


    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    public void handleButtonAddItem(ActionEvent event){
        String selectedButtonID = ((RadioButton) groupItems.getSelectedToggle()).getId();
        String selectedColor = comboBoxColors.getSelectionModel().getSelectedItem().toString();
        //System.out.println("hej "+comboBoxColors.getSelectionModel().getSelectedItem().toString());

        switch (selectedButtonID){
            case "radiobuttonShirt":
                Shirt newShirt = new Shirt(selectedColor, false);
                newShirt.storeToDB();
                basket.add(newShirt);
                //basket.add(new Shirt());
                System.out.println("We have a shirt");
                break;
            case "radiobuttonBlazer":
                Blazer newBlazer = new Blazer(selectedColor, false);
                newBlazer.storeToDB();
                basket.add(newBlazer);
                System.out.println("We have a blazer");
                break;
            case "radiobuttonCarpet":
                Carpet newCarpet = new Carpet(selectedColor, false, 1);
                newCarpet.storeToDB();
                basket.add(newCarpet);
                System.out.println("We have a carpet");
                break;
            case "radiobuttonCoat":
                Coat newCoat = new Coat(selectedColor, false);
                newCoat.storeToDB();
                basket.add(newCoat);
                System.out.println("We have a coat");
                break;
            case "radiobuttonDress":
                Dress newDress = new Dress(selectedColor, false);
                newDress.storeToDB();
                basket.add(newDress);
                System.out.println("We have a dress");
                break;
            case "radiobuttonTrousers":
                Trousers newTrousers = new Trousers(selectedColor, false);
                newTrousers.storeToDB();
                basket.add(newTrousers);
                System.out.println("We have trousers");
                break;
            case "radiobuttonTshirt":
                Tshirt newTshirt = new Tshirt(selectedColor, false);
                newTshirt.storeToDB();
                basket.add(newTshirt);
                System.out.println("We have a Tshrit");
                break;
        }

    }

    public void handleButtonDeleteItem(){
        basket.remove(listViewLaundryItem.getSelectionModel().getSelectedItem());
    }

}
