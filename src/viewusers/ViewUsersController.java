package viewusers;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import static project.Constants.BASE_URL;
import project.DataBase;
import project.User;

public class ViewUsersController implements Initializable {

    @FXML
    private TableColumn<User, String> c1;
    @FXML
    private TableColumn<User, String> c2;
    @FXML
    private TableColumn<User, String> c3;
    @FXML
    private TableView<User> table;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String apiUrl = BASE_URL + "/view-user";
            URL uri = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("GET");
            
            System.out.println("API Called for viewing user having URL: " + apiUrl);
            
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code for viewing users: " + responseCode);
            
            c1.setCellValueFactory(new PropertyValueFactory("username"));
            c2.setCellValueFactory(new PropertyValueFactory("password"));
            c3.setCellValueFactory(new PropertyValueFactory("is_admin"));
            List<User> users = DataBase.getUsers();
            table.getItems().addAll(users);
            
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
