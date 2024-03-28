package users;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import project.Paths;
import static homepage.HomePageController.decorator1;

public class UsersController implements Initializable {

    @FXML
    private ImageView key_pic_Login_Btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void NewUserAction(MouseEvent event) {
    }

    @FXML
    private void AddUser(ActionEvent event) {

        System.out.println("Add-User label clicked");
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Paths.ADDUSERVIEW));
            decorator1.setContent(root);
            decorator1.setTitle("Add User");
            root.requestFocus();
        } catch (Exception ex) {
            System.out.println("Error load Add-User FXML !");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    @FXML
    private void DeleteUser(ActionEvent event) {

        System.out.println("DeleteUser label clicked");
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Paths.DELETEUSERVIEW));

            decorator1.setContent(root);
            decorator1.setTitle("Delete User");

            root.requestFocus();

        } catch (Exception ex) {
            System.out.println("Error load DeleteUser FXML !");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    @FXML
    private void ViewUsers(ActionEvent event) {

        System.out.println("ViewUsers label clicked");
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Paths.VIEWUSERSVIEW));

            decorator1.setContent(root);
            decorator1.setTitle("View Users");

            root.requestFocus();

        } catch (Exception ex) {
            System.out.println("Error load ViewUsers FXML !");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
