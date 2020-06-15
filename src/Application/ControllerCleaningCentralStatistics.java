package Application;

import Domain.Adapter;
import Domain.SystemUser.SystemUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class ControllerCleaningCentralStatistics {

    public SystemUser su;
    @FXML MenuButton menuButton1;
    @FXML MenuItem menuItem3;
    @FXML PieChart pieChart;
    @FXML private BarChart<?, ?> barChart;

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

    /**
     * The method will display a pieChart of the item types, sorted by the frequency of how often such type is being cleaned.
     */
    public void handleButtonPopularItems() {
        pieChart.setData(Adapter.DBInstance().getPopularItems());
    }

    /**
     * The method will display a barChart of the incoming number of orders, sorted after the different routes their
     * corresponding delivery point is on.
     */
    public void handleShowIncomingOrders() {
        XYChart.Series dataSet1 = new XYChart.Series<>();
        dataSet1.setName("EAST");
        dataSet1.getData().add(Adapter.DBInstance().getIncomingOrders().get(0));

        XYChart.Series dataSet2 = new XYChart.Series<>();
        dataSet2.setName("NORTH");
        dataSet2.getData().add(Adapter.DBInstance().getIncomingOrders().get(1));

        XYChart.Series dataSet3 = new XYChart.Series<>();
        dataSet3.setName("SOUTH");
        dataSet3.getData().add(Adapter.DBInstance().getIncomingOrders().get(2));

        barChart.getData().addAll(dataSet1, dataSet2, dataSet3);
    }
}
