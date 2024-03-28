package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static project.Constants.BASE_URL;
import project.DataBase;
import project.Paths;
import project.User;
import project.switchScreen;

public class LoginController implements Initializable {

    public static Stage window;
    public static JFXDecorator decorator;
    public static User user;

    @FXML
    private JFXButton ButtonLogin;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXTextField UserNameField;
    @FXML
    private JFXPasswordField PasswordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void loginEvent(Event event) {
        try {
            DataBase.CheckConnection();
            System.out.println("Connection established");
        } catch (Exception e) {
            System.out.println("Error in checking connection");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Connection Error");
            alert.setContentText("Error while connecting to db");
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/WARNING_PNG.png"));
            alert.show();
            return;
        }

        try {
            String apiUrl = BASE_URL + "/login";
            URL uri = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            System.out.println("API Called for logging user having URL: " + apiUrl);
            
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code for login user: " + responseCode);
            
            user = new User(UserNameField.getText(), PasswordField.getText(), false);
            boolean userIsAdmin = User.isUserAdmin(user);
            user.setIs_admin(userIsAdmin);

            if (User.isUserValid(user) && userIsAdmin) {
                mainPage(event);
            } else if (User.isUserValid(user) && !userIsAdmin) {
                mainPageReceptionist(event);
            } else {
                try {
                    new switchScreen().popUp(event, "/login/ErrorPopUp.fxml", 370, 250, "", "/img/Error01.png");
                } catch (Exception ex) {
                    System.out.println("error on popUp window");
                    System.out.println(ex);
                }
            }
            
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            System.out.println("User Invalid Exception");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Connection Error");
            alert.setContentText("Error while connecting to db");
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/Error01.png"));
            alert.show();
            return;
        }

    }

    public void mainPage(Event event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource(Paths.HOMEPAGEVIEW));
    
            window = new Stage();

            decorator = new JFXDecorator(window, root, false, false, true);

            int width = 1400, height = 1000;
            Scene scene = new Scene(decorator, width, height);

            decorator.setTitle("Hotel Reservation System");

            String uri = getClass().getResource("dectaorStyle.css").toExternalForm();
            scene.getStylesheets().add(uri);
            window.setScene(scene);
            window.setMaxHeight(height);
            window.setMinHeight(height);
            window.setMaxWidth(width);
            window.setMinWidth(width);
            window.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.jpg")));
            window.setTitle("Hotel System");

            window.show();

            root.requestFocus();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException ex) {
            System.out.println("Error loading HomePage FXML!");
            System.out.println(ex);

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
    public void mainPageReceptionist(Event event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource(Paths.HOMEPAGERECEPTIONISTVIEW));
    
            window = new Stage();

            decorator = new JFXDecorator(window, root, false, false, true);

            int width = 1400, height = 1000;
            Scene scene = new Scene(decorator, width, height);

            decorator.setTitle("Hotel Reservation System");

            String uri = getClass().getResource("dectaorStyle.css").toExternalForm();
            scene.getStylesheets().add(uri);
            window.setScene(scene);
            window.setMaxHeight(height);
            window.setMinHeight(height);
            window.setMaxWidth(width);
            window.setMinWidth(width);
            window.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.jpg")));
            window.setTitle("Hotel System");

            window.show();

            root.requestFocus();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException ex) {
            System.out.println("Error loading HomePage FXML!");
            System.out.println(ex);

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

}
