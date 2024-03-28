package roombooking;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import hotel.Reservation;
import hotel.Room;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import static project.Constants.BASE_URL;
import static project.Constants.EMPTY_TEXT;

public class ConfirmDataController implements Initializable {

    @FXML
    private TextField NameField;
    @FXML
    private TextField PhoneField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField CityField;
    @FXML
    private TextField NationalityField;
    @FXML
    private TextField PassportField;
    @FXML
    private TextArea AddressField;
    @FXML
    private TextField CardNumField;
    @FXML
    private TextField CVCcodeField;
    @FXML
    private JFXButton Save_Button;
    @FXML
    private JFXButton CancelButton;
    @FXML
    private Label Room_Type;
    @FXML
    private Label CheckOutLabel;
    @FXML
    private Label Room_Capacity;
    @FXML
    private JFXTextField roomIDField;
    @FXML
    private Label Number_of_Nights;
    @FXML
    private Label Night_Cost;
    private Label Total_Fees;
    @FXML
    private ImageView LogoImg;
    @FXML
    private Label emptyLabel;
    @FXML
    private Label CheckInLabel;
    @FXML
    private Label Total_fees;
    
    public static Reservation reservation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Reservation reservation = RoomBookingController.reservation;

        NameField.setText(reservation.getGuest().getName());
        PhoneField.setText(reservation.getGuest().getPhoneNo());
        EmailField.setText(reservation.getGuest().getEmail());
        AddressField.setText(reservation.getGuest().getAddress());
        CityField.setText(reservation.getGuest().getCity());
        NationalityField.setText(reservation.getGuest().getNationality());
        PassportField.setText(reservation.getGuest().getPassportNumber());
        CardNumField.setText(reservation.getGuest().getCardNumber());
        CVCcodeField.setText(reservation.getGuest().getCardPass());
        Room_Type.setText(reservation.getRoom().getRoom_Type());
        Room_Capacity.setText(reservation.getRoom().getRoom_capacity());
        roomIDField.setText(reservation.getGuest().getRoomID() + EMPTY_TEXT);
        CheckOutLabel.setText(RoomBookingController.dateToLocalDate(reservation.getRoom().getCheck_Out_Date()) + EMPTY_TEXT);
        CheckInLabel.setText(RoomBookingController.dateToLocalDate(reservation.getRoom().getCheck_In_Date()) + EMPTY_TEXT);
        Number_of_Nights.setText(reservation.getGuest().getNumberOfDaysStayed() + EMPTY_TEXT);
        Night_Cost.setText(reservation.getRoom().nightCost(reservation.getRoom()) + EMPTY_TEXT);
        Total_fees.setText(reservation.getGuest().CustomerRoomFees(reservation.getRoom()) + " $");

    }

    @FXML
    private void BookReservation(ActionEvent event) {
        try {
            String apiUrl = BASE_URL + "/confirm-booking";
            URL uri = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            System.out.println("API Called for confirming reservation having URL: " + apiUrl);
            Reservation reservation = RoomBookingController.reservation;
            
            String requestBody = reservation.toString();

            // Write the JSON data to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code for confirming reservation: " + responseCode);

            Room.RoomBooking(reservation.getGuest(), reservation.getRoom(),
                reservation.getGuest().getRoomID());

            new homepage.HomePageController().bookRoom(event);
            ((Stage) NameField.getScene().getWindow()).close();
            
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelBookingAction(ActionEvent event) {
        ((Stage) NameField.getScene().getWindow()).close();
    }

}
