package Application;

import Domain.Adapter;
import Domain.SystemUser.SystemUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControllerCleaningCentralStatistics {

    public SystemUser su;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML PieChart pieChart;
    //@FXML BarChart barChart;
    @FXML private BarChart<?, ?> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    public void handleButtonHome(ActionEvent event) throws IOException {
        Parent menuScreen = FXMLLoader.load(getClass().getResource("/Presentation/welcome.fxml"));
        Scene Scene = new Scene(menuScreen);
        Stage window = (Stage) menuButton1.getScene().getWindow();
        window.setScene(Scene);
        window.show();
    }

    /**
     * The method will display a pieChart of the item types, sorted by the frequency of how often such type is being cleaned.
     */
    public void handleButtonPopularItems() {
        pieChart.setData(Adapter.DBInstance().getPopularItems());
    }

    /**
     * The method will display a barChart of the incoming number of orders and items, which were handed-in at the delivery points.
     * TODO get the right values
     */
    public void handleShowIncomingOrders() {
        XYChart.Series set1 = new XYChart.Series<>();
        set1.setName("NORTH");

        set1.getData().add(new XYChart.Data("orders", 20));
        set1.getData().add(new XYChart.Data("items", 40));
        barChart.getData().addAll(set1);

        XYChart.Series set2 = new XYChart.Series<>();
        set2.setName("SOUTH");
        set2.getData().add(new XYChart.Data("orders", 10));
        set2.getData().add(new XYChart.Data("items", 30));
        barChart.getData().add(set2);

        XYChart.Series set3 = new XYChart.Series<>();
        set3.setName("EAST");
        set3.getData().add(new XYChart.Data("orders", 50));
        set3.getData().add(new XYChart.Data("items", 100));
        barChart.getData().add(set3);

        // "get orders from route" get Orders From DeliveryPoint where route = North
        // count these orders
        // get items.size() from each order and calculate

    }
}
