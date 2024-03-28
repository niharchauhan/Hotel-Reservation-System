
package rooms;

import com.jfoenix.controls.JFXButton;
import hotel.Room;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import login.LoginController;
import static project.Constants.BASE_URL;
import project.DataBase;
import project.User;

public class RoomsController implements Initializable {

    @FXML
    private Label backLabel;
    @FXML
    private JFXButton Logobtn;
    @FXML
    private Label usernameLabel;
    @FXML
    private Hyperlink logoutLink;
    @FXML
    private TableView<Room> tabel2;
    @FXML
    private TableColumn<Room, String> c1;
    @FXML
    private TableColumn<Room, String> c2;
    @FXML
    private TableColumn<Room, String> c3;
    @FXML
    private TableColumn<Room, String> c4;
    @FXML
    private TableColumn<Room, String> c5;
    @FXML
    private TableColumn<Room, String> c6;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            String apiUrl = BASE_URL + "/get-all-rooms";
            URL uri = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            
            System.out.println("API Called for getting rooms having URL: " + apiUrl);
            
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code for getting all rooms: " + responseCode);

            List<Room> Rooms = DataBase.getrooms();

            int index = 0;
            while (index < Rooms.size()) {
                System.out.println("List of rooms: " + Rooms.get(index));
                index++;
            }
            c1.setCellValueFactory(new PropertyValueFactory("roomID"));
            c2.setCellValueFactory(new PropertyValueFactory("room_Type"));
            c3.setCellValueFactory(new PropertyValueFactory("room_capacity"));
            c4.setCellValueFactory(new PropertyValueFactory("Check_In_Date"));
            c5.setCellValueFactory(new PropertyValueFactory("Check_Out_Date"));
            c6.setCellValueFactory(new PropertyValueFactory("isEmpty"));
            try {
                tabel2.getItems().addAll(Rooms);
            } catch (Exception e) {
                e.printStackTrace();
            }
            usernameLabel.setText(login.LoginController.user.getUsername());
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception according to your needs
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
    private void bookRoom(ActionEvent event) {
        new homepage.HomePageController().bookRoom(event);
    }

    @FXML
    private void cancelBooking(ActionEvent event) {
        new homepage.HomePageController().cancelBooking(event);
    }

    @FXML
    private void viewRooms(ActionEvent event) {
        new homepage.HomePageController().viewRooms(event);    
    }
    
    @FXML
    private void checkoutBookings(Event event) {
        new homepage.HomePageController().checkoutBookings(event);
    }
    
    @FXML
    private void viewGuests(ActionEvent event) {
         new homepage.HomePageController().viewGuests(event);
    }

}
