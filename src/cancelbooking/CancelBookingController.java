package cancelbooking;

import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.StringUtils;
import hotel.Room;
import java.io.OutputStream;
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
import static project.Constants.ALREADY_EMPTY_ROOM;
import static project.Constants.BASE_URL;
import static project.Constants.CANCEL_BOOKING_CONFIRMATION_ERROR;
import static project.Constants.CANCEL_BOOKING_DONE;
import static project.Constants.CANCEL_BOOKING_FILL_ALL_FIELDS;
import static project.Constants.EMPTY_TEXT;
import static project.Constants.ERROR;
import static project.Constants.ERROR01_PATH;
import static project.Constants.INFO;
import static project.Constants.INVALID_ROOM_NUMBER;
import static project.Constants.ROOM_NUMBER_INVALID;

public class CancelBookingController implements Initializable {

    @FXML
    private Label back;
    @FXML
    private JFXButton Logobtn;
    @FXML
    private Label usernameLabel;
    @FXML
    private Hyperlink logoutLink;

    @FXML
    private TextField roomNoField;
    @FXML
    private JFXButton clearbtn;
    @FXML
    private JFXButton cancelBookingBTN;
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
    private void cancelBookingAction(ActionEvent event) {
        try {
            String apiUrl = BASE_URL + "/cancel-booking" + "?room_no=" + roomNoField.getText();
            URL uri = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("GET");
            
            System.out.println("API Called for canceling booking having URL: " + apiUrl);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code for canceling booking: " + responseCode);
            
            if (StringUtils.isEmptyOrWhitespaceOnly(roomNoField.getText())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, EMPTY_TEXT, ButtonType.OK);
                alert.setContentText(CANCEL_BOOKING_CONFIRMATION_ERROR);
                alert.setHeaderText(CANCEL_BOOKING_FILL_ALL_FIELDS);
                alert.setTitle(ERROR);
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(ERROR01_PATH));
                alert.showAndWait();
            } else {
                int flag;
                try {
                    flag = Room.CancelBooking(Integer.parseInt(roomNoField.getText()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.OK);
                    alert.setHeaderText(ROOM_NUMBER_INVALID);
                    alert.setContentText(e.getMessage());
                    alert.setTitle(ERROR);
                    alert.show();
                    return;
                }
                switch (flag) {
                    case 1: {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, null, ButtonType.OK);
                        alert.setHeaderText(CANCEL_BOOKING_DONE);
                        alert.setTitle(INFO);
                        alert.show();
                        new homepage.HomePageController().cancelBooking(event);
                        break;
                    }
                    case 0: {
                        Alert alert = new Alert(Alert.AlertType.WARNING, null, ButtonType.OK);
                        alert.setHeaderText(ALREADY_EMPTY_ROOM);
                        alert.setTitle(INFO);
                        alert.show();
                        break;
                    }
                    case 2: {
                        Alert alert = new Alert(Alert.AlertType.WARNING, null, ButtonType.OK);
                        alert.setHeaderText("Cannot cancel booking after checkin date. Please proceed for checkout");
                        alert.setTitle("info");
                        alert.show();
                        break;
                    }
                    default: {
                        Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.OK);
                        alert.setHeaderText(INVALID_ROOM_NUMBER);
                        alert.setContentText("Room: " + roomNoField.getText() + " is invalid!");
                        alert.setTitle(ERROR);
                        alert.show();
                        break;
                    }
                }
            }
            
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearAction(ActionEvent event) {
        roomNoField.setText(EMPTY_TEXT);
    }

    @FXML
    private void viewRooms(ActionEvent event) {
        new homepage.HomePageController().viewRooms(event);
    }
    
    @FXML
    private void viewGuests(ActionEvent event) {
        new homepage.HomePageController().viewGuests(event);
    }
    
    @FXML
    private void checkoutBookings(Event event) {
        new homepage.HomePageController().checkoutBookings(event);
    }
}
