package checkout;

import com.jfoenix.controls.JFXButton;
import hotel.Room;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import login.LoginController;
import static project.Constants.BASE_URL;

public class CheckOutController implements Initializable {

    @FXML
    private JFXButton Logobtn;
    @FXML
    private Label usernameLabel;
    @FXML
    private Hyperlink logoutLink;
    @FXML
    private TextField roomNoField;
    @FXML
    private JFXButton CheckOutBTN;
    @FXML
    private JFXButton clearbtn;
    @FXML
    private JFXButton checkinBTN1;
    @FXML
    private JFXButton roombookingBTN1;
    @FXML
    private JFXButton cancelbookingBTN1;
    @FXML
    private JFXButton roomBTN;
    @FXML
    private JFXButton guestsBTN;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameLabel.setText(login.LoginController.user.getUsername());
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
    public void checkoutAction(ActionEvent event) {
        try {
            String apiUrl = BASE_URL + "/checkout" + "?room_no=" + roomNoField.getText();
            URL uri = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("GET");
            
            System.out.println("API Called for checking out booking having URL: " + apiUrl);
            
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code for checking out booking: " + responseCode);
            
            if ("".equals(roomNoField.getText())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setContentText("Please fill all the fields to go to Confirmation window !");
                alert.setHeaderText("Please fill all fields!!");
                alert.setTitle("Error");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/Error01.png"));
                alert.showAndWait();
            } else {

                int flag = -1;
                flag = Room.checkOutGuest(Integer.parseInt(roomNoField.getText()));
              
                switch (flag) {
                    case 1: {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, null, ButtonType.OK);
                        alert.setHeaderText("Check-out Completed");
                        alert.setTitle("info");
                        alert.show();
                        new homepage.HomePageController().checkoutBookings(event);
                        break;
                    }
                    case 0: {
                        Alert alert = new Alert(Alert.AlertType.WARNING, null, ButtonType.OK);
                        alert.setHeaderText("Room was already Empty !");
                        alert.setTitle("info");
                        alert.show();
                        break;
                    }
                    case 2: {
                        Alert alert = new Alert(Alert.AlertType.WARNING, null, ButtonType.OK);
                        alert.setHeaderText("Cannot checkout room before checkin date");
                        alert.setTitle("info");
                        alert.show();
                        break;
                    }
                    default: {
                        Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.OK);
                        alert.setHeaderText("Room number not valid ...");
                        alert.setContentText("Error , room: " + roomNoField.getText() + " no not valid !");
                        alert.setTitle("Error");
                        alert.show();
                        break;
                    }
                }
            }
            
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.OK);
            alert.setHeaderText("Error , room number invalid!!");
            alert.setContentText(e.getMessage());
            alert.setTitle("Error");
            alert.show();
            return;
        }
    }

    @FXML
    private void clearAction(ActionEvent event) {
        roomNoField.setText("");
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
