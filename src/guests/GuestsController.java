package guests;

import com.jfoenix.controls.JFXButton;
import hotel.Guest;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import login.LoginController;
import static project.Constants.BASE_URL;
import project.DataBase;

public class GuestsController implements Initializable {

    @FXML
    private Label backLabel;
    @FXML
    private JFXButton Logobtn;
    @FXML
    private Label usernameLabel;
    @FXML
    private Hyperlink logoutLink;
    @FXML
    private TableView<Guest> tabel2;
    @FXML
    private TableColumn<Guest, String> c1;
    @FXML
    private TableColumn<Guest, String> c2;
    @FXML
    private TableColumn<Guest, String> c3;
    @FXML
    private TableColumn<Guest, String> c4;
    @FXML
    private TableColumn<Guest, String> c5;
    @FXML
    private TableColumn<Guest, String> c6;
    @FXML
    private TableColumn<Guest, String> c7;
    @FXML
    private TableColumn<Guest, String> c8;
    @FXML
    private TableColumn<Guest, String> c9;
    @FXML
    private TableColumn<Guest, String> c10;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String apiUrl = BASE_URL + "/view-guests";
            URL uri = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("GET");
            
            System.out.println("API Called for viewing all guests having URL: " + apiUrl);
            
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code for viewing guests: " + responseCode);
            
            List<Guest> guests = DataBase.getGuests();

            c1.setCellValueFactory(new PropertyValueFactory("roomID"));
            c2.setCellValueFactory(new PropertyValueFactory("Name"));
            c3.setCellValueFactory(new PropertyValueFactory("Email"));
            c4.setCellValueFactory(new PropertyValueFactory("Address"));
            c5.setCellValueFactory(new PropertyValueFactory("city"));
            c6.setCellValueFactory(new PropertyValueFactory("Nationality"));
            c7.setCellValueFactory(new PropertyValueFactory("passportNumber"));
            c8.setCellValueFactory(new PropertyValueFactory("phoneNo"));
            c9.setCellValueFactory(new PropertyValueFactory("numberOfDaysStayed"));
            c10.setCellValueFactory(new PropertyValueFactory("Fees"));
    
            tabel2.getItems().addAll(guests);
            usernameLabel.setText(login.LoginController.user.getUsername());
            
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout(Event event) {
        new homepage.HomePageController().logout(event);
    }

    @FXML
    public void mainPage(Event event) {
        if(login.LoginController.user.isIs_admin()) {
            new LoginController().mainPage(event);
        } else {
            new LoginController().mainPageReceptionist(event);
        }
    }

    @FXML
    private void bookRoom(Event event) {
        new homepage.HomePageController().bookRoom(event);
    }

    @FXML
    private void cancelBooking(Event event) {
        new homepage.HomePageController().cancelBooking(event);
    }

    @FXML
    private void checkoutBookings(Event event) {
        new homepage.HomePageController().checkoutBookings(event);
    }

    @FXML
    private void viewRooms(ActionEvent event) {
        new homepage.HomePageController().viewRooms(event);
    }
    
    @FXML
    private void viewGuests(ActionEvent event) {
        new homepage.HomePageController().viewGuests(event);
    }
}
