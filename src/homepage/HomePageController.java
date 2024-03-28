package homepage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import hotel.Room;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static login.LoginController.decorator;
import static login.LoginController.window;
import static project.Constants.ADMIN;
import static project.Constants.BASE_URL;
import static project.Constants.LOGIN_ICON_PATH;
import project.DataBase;
import project.Paths;
import project.switchScreen;

public class HomePageController implements Initializable {

    @FXML
    private JFXButton check_out_Buttton;
    @FXML
    private JFXButton room_booking_Buttton;
    @FXML
    private JFXButton cancel_booking_Buttton;
    @FXML
    private Label usernameLabel11;
    @FXML
    private Label usernameLabel1;
    @FXML
    private Label usernameLabel111;
    @FXML
    private Label rank_Label;
    @FXML
    private ProgressIndicator available_par;
    @FXML
    private ProgressIndicator reserved_par;
    @FXML
    private JFXButton roomBTN;
    @FXML
    private JFXButton guestsBTN;

    public static JFXDecorator decorator1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        usernameLabel1.setText(login.LoginController.user.getUsername());

        if (login.LoginController.user.isIs_admin()) {
            rank_Label.setText(ADMIN);
        }

        List<Room> availableRooms = DataBase.getAvailableRooms();
        System.out.println("Available Rooms = " + availableRooms.size());
        double available_Percentage = (availableRooms.size());
        available_par.setProgress(available_Percentage / 90);
        double reserved_Percentage = (90 - availableRooms.size());
        reserved_par.setProgress(reserved_Percentage / 90);

    }

    @FXML
    public void logout(Event event) {
        try {
            
            String apiUrl = BASE_URL + "/logout";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            System.out.println("API Called for logging out user having URL: " + apiUrl);
            
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code for logging out user: " + responseCode);
            
            Parent root = FXMLLoader.load(getClass().getResource(Paths.LOGINVIEW));

            Stage window2 = new Stage();

            JFXDecorator decorator = new JFXDecorator(window2, root, false, false, true);

            Scene scene = new Scene(decorator);

            String uri = getClass().getResource("dectaorStyle.css").toExternalForm();
            scene.getStylesheets().add(uri);

            int width = 690, height = 620;
            window2.setScene(scene);
            window2.setMaxHeight(height);
            window2.setMinHeight(height);
            window2.setMaxWidth(width);
            window2.setMinWidth(width);
            Image icon = new Image(getClass().getResourceAsStream(LOGIN_ICON_PATH));
            window2.getIcons().add(icon);
            window2.show();

            root.requestFocus();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

            // Close the connection
            connection.disconnect();
        } catch (Exception ex) {
            System.out.println("Error getting Login FXML!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    public void bookRoom(Event event) {

        try {
            Parent root = null;
            if (login.LoginController.user.isIs_admin()) {
                root = FXMLLoader.load(getClass().getResource(Paths.ROOMBOOKINGVIEW));
            } else {
                root = FXMLLoader.load(getClass().getResource(Paths.ROOMBOOKINGRECEPTIONISTVIEW));
            }

            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Room Booking");

            decorator.setContent(root);
            decorator.setTitle("Room Booking");
            root.requestFocus();
        } catch (Exception ex) {
            System.out.println("Error getting RoomBooking FXML!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    public void cancelBooking(Event event) {

        try {
            Parent root = null;
            if (login.LoginController.user.isIs_admin()) {
                root = FXMLLoader.load(getClass().getResource(Paths.CANCELBOOKINGVIEW));
            } else {
                root = FXMLLoader.load(getClass().getResource(Paths.CANCELBOOKINGRECEPTIONISTVIEW));
            }

            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Cancel Booking");

            decorator.setContent(root);
            decorator.setTitle("Cancel Booking");
            root.requestFocus();
        } catch (Exception ex) {
            System.out.println("Error getting CancelBooking FXML!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    public void viewRooms(Event event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource(Paths.ROOMSVIEW));

            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Rooms");

            decorator.setContent(root);
            decorator.setTitle("Rooms");
            root.requestFocus();
        } catch (Exception ex) {
            System.out.println("Error getting Rooms FXML!");
            switchScreen.getAlertError("Available Rooms",ex.getMessage());
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    @FXML
    public void checkoutBookings(Event event) {
        System.out.println("Check out button clicked");

        try {
            Parent root = null;
            if (login.LoginController.user.isIs_admin()) {
                root = FXMLLoader.load(getClass().getResource(Paths.CHECKOUTVIEW));
            } else {
                root = FXMLLoader.load(getClass().getResource(Paths.CHECKOUTRECEPTIONISTVIEW));
            }
            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Check out");
            decorator.setContent(root);
            decorator.setTitle("Check out");
            root.requestFocus();
        } catch (Exception ex) {
            System.out.println("Error load Checkout FXML !");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    public void goTo_Users(Event event) {
        if (login.LoginController.user.isIs_admin()) {
            System.out.println("Users label clicked");
            try {
                Parent root = FXMLLoader.load(getClass().getResource(Paths.USERSVIEW));
                Stage window2 = new Stage();
                decorator1 = new JFXDecorator(window2, root, false, false, true);
                Scene scene = new Scene(decorator1);
                String uri = getClass().getResource("dectaorStyle.css").toExternalForm();
                scene.getStylesheets().add(uri);

                int width = 690, height = 620;
                window2.setScene(scene);
                window2.setMaxHeight(height);
                window2.setMinHeight(height);
                window2.setMaxWidth(width);
                window2.setMinWidth(width);
                Image icon = new Image(getClass().getResourceAsStream("/img/login_icon.png"));
                window2.getIcons().add(icon);
                window2.show();

                root.requestFocus();
            } catch (Exception ex) {
                System.out.println("Error load Users FXML !");
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins Can Edit Users");
            alert.setTitle("Error");
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/Error01.png"));
            alert.showAndWait();
        }
    }
    
    @FXML
    public void viewGuests(Event event) { 
        System.out.println("Rooms button clicked");

        try {
            Parent root = null;
            if (login.LoginController.user.isIs_admin()) {
                root = FXMLLoader.load(getClass().getResource(Paths.GUESTSVIEW));
            } else {
                root = FXMLLoader.load(getClass().getResource(Paths.GUESTSRECEPTIONISTVIEW));
            }
            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Guests");
            decorator.setContent(root);
            decorator.setTitle("Guests");
            root.requestFocus();
        } catch (Exception ex) {
            System.out.println("Error load Guests FXML !");
            switchScreen.getAlertError("Guests",ex.getMessage());
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
