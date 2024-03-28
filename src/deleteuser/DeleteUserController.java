package deleteuser;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static project.Constants.BASE_URL;
import project.DataBase;

public class DeleteUserController implements Initializable {

    @FXML
    private JFXTextField UserNameField;
    @FXML
    private JFXButton ButtonLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void DeleteAction(ActionEvent event) {
        try {
            String apiUrl = BASE_URL + "/delete-user";
            URL uri = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            System.out.println("API Called for deleting user having URL: " + apiUrl);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code for deleting user: " + responseCode);
            
            if ("".equals(UserNameField.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
                alert.setHeaderText("Please fill all fields ...");
                alert.setTitle("Error");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/WARNING_PNG.png"));
                alert.showAndWait();
                return;
            }
            if(UserNameField.getText().equals(login.LoginController.user.getUsername())){
                Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
                alert.setHeaderText("Deleting your own username is not permitted");
                alert.setTitle("Error");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/WARNING_PNG.png"));
                alert.showAndWait();
                return;
            }
            boolean DeleteUser = DataBase.DeleteUser(UserNameField.getText());
            if (DeleteUser) {
                UserNameField.setText("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setHeaderText("User Deletion Completed");
                alert.setTitle("Notification");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setHeaderText("User-Name not valid, Try Again");
                alert.setTitle("Error");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/Error01.png"));
                alert.showAndWait();
            }
            
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUsers(Event event) {
        new adduser.AddUserController().handleUsers(event);
    }

}
