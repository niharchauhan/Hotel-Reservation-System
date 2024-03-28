package roombooking;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import hotel.Guest;
import hotel.Reservation;
import hotel.Room;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.time.ZoneId;
import java.util.Objects;
import login.LoginController;
import static project.Constants.EMPTY_TEXT;
import static project.Constants.ERROR;
import static project.Constants.ROOM_CAPACITY_DOUBLE;
import static project.Constants.ROOM_CAPACITY_SINGLE;
import static project.Constants.ROOM_CAPACITY_TRIPLE;
import static project.Constants.ROOM_TYPE_ECONOMY;
import static project.Constants.ROOM_TYPE_NORMAL;
import static project.Constants.ROOM_TYPE_VIP;

public class RoomBookingController implements Initializable {

    @FXML
    private Label backLabel;
    @FXML
    private JFXButton Logobtn;
    @FXML
    private Label usernameLabel;
    @FXML
    private Hyperlink logoutLink;
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
    private JFXButton submitButton;
    @FXML
    private JFXButton clearButton;
    @FXML
    private RadioButton economyType;
    @FXML
    private ToggleGroup roomtype;
    @FXML
    private RadioButton normalType;
    @FXML
    private RadioButton vipType;
    @FXML
    private RadioButton singleCapacity;
    @FXML
    private ToggleGroup roomCapacity;
    @FXML
    private RadioButton doubleCapacity;
    @FXML
    private RadioButton tripleCapacity;
    @FXML
    private JFXDatePicker CheckInDatePicker;
    @FXML
    private JFXDatePicker CheckoutDatePicker;
    @FXML
    private JFXButton searchRoomButtton;
    @FXML
    private JFXTextField roomIDField;

    @FXML
    private JFXButton roomBTN;
    @FXML
    private JFXButton guestsBTN;
    
    public static Reservation reservation;

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

    public boolean isFieldsEmpty() {
        if (EMPTY_TEXT.equals(NameField.getText()) || EMPTY_TEXT.equals(PhoneField.getText())
                || (EMPTY_TEXT.equals(EmailField.getText())) || (EMPTY_TEXT.equals(AddressField.getText()))
                || (EMPTY_TEXT.equals(CityField.getText())) || (EMPTY_TEXT.equals(NationalityField.getText()))
                || (EMPTY_TEXT.equals(PassportField.getText())) || (EMPTY_TEXT.equals(CardNumField.getText()))
                || (EMPTY_TEXT.equals(CVCcodeField.getText())) || (EMPTY_TEXT.equals(roomIDField.getText()))) {
            
            return true;
        } else {
            return false;
        }
    }

    public String getRoomTypeValue() {
        String RoomType = null;
        if (economyType.isSelected()) {
            RoomType = ROOM_TYPE_ECONOMY;
        } else if (normalType.isSelected()) {
            RoomType = ROOM_TYPE_NORMAL;
        } else if (vipType.isSelected()) {
            RoomType = ROOM_TYPE_VIP;
        }
        return RoomType;
    }

    public String getRoomCapacityValue() {
        String RoomCapacity = null;
        if (singleCapacity.isSelected()) {
            RoomCapacity = ROOM_CAPACITY_SINGLE;
        } else if (doubleCapacity.isSelected()) {
            RoomCapacity = ROOM_CAPACITY_DOUBLE;
        } else if (tripleCapacity.isSelected()) {
            RoomCapacity = ROOM_CAPACITY_TRIPLE;
        }
        return RoomCapacity;
    }

    @FXML
    private void submitButtonAction(ActionEvent event) {

        try {

            if (!isFieldsEmpty()) {
                if (getDaysDifference(localDateToDate(CheckInDatePicker.getValue()),
                        localDateToDate(CheckoutDatePicker.getValue())) < 0) {
                    return;
                }
                
                Room room = new Room(getRoomTypeValue(), getRoomCapacityValue(),
                        localDateToDate(CheckInDatePicker.getValue()),
                        localDateToDate(CheckoutDatePicker.getValue()), false);

                Guest guest = new Guest(Integer.parseInt(roomIDField.getText()),
                        getDaysDifference(localDateToDate(CheckInDatePicker.getValue()),
                                localDateToDate(CheckoutDatePicker.getValue())),
                        NameField.getText(), EmailField.getText(),
                        AddressField.getText(), CityField.getText(), NationalityField.getText(),
                        PassportField.getText(), PhoneField.getText(), CardNumField.getText(),
                        CVCcodeField.getText(), 0);
                guest.setFees(guest.CustomerRoomFees(room));
                reservation = new Reservation(room, guest);
                new project.switchScreen().popUp(event, "/roombooking/ConfirmData.fxml", 1014, 1010, "Confirmation Data", "/img/icon.jpg");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, EMPTY_TEXT, ButtonType.OK);
                alert.setContentText("you must Fill all fields to go to Confirmation window !");
                alert.setHeaderText("Fill all fields !");
                alert.setTitle(ERROR);
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/Error01.png"));
                alert.showAndWait();

            }

        } catch (Exception e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill all fields !");
            alert.setHeaderText("Fill all fields !");
            alert.setTitle("Error !");
            alert.showAndWait();
        }

    }

    @FXML
    private void searchRoom(ActionEvent event) {

        try {
            if (!isFieldsEmpty()) {
                if (getDaysDifference(localDateToDate(CheckInDatePicker.getValue()),
                        localDateToDate(CheckoutDatePicker.getValue())) < 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                    alert.setContentText("not valid value for Check-in or Check-out Date \n Try Again !");
                    alert.setHeaderText("Choose Correct Date !");
                    alert.setTitle(ERROR);
                    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/Error01.png"));
                    alert.showAndWait();
                    return;
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill all fields !");
            alert.setHeaderText("Fill all fields !");
            alert.setTitle("Error !");
            alert.showAndWait();
        }

        Room room = Room.getRoomAvailability(getRoomTypeValue(), getRoomCapacityValue());
        if (Objects.isNull(room)) {
            roomIDField.setText("no match !");
        } else {
            roomIDField.setText(room.getRoomID() + EMPTY_TEXT);
        }
    }

    @FXML
    private void viewRooms(ActionEvent event) {
        new homepage.HomePageController().viewRooms(event);
    }
    
    public Date localDateToDate(LocalDate ld) {
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date res = Date.from(instant);
        return res;
    }

    public static int getDaysDifference(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        LocalDate res = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return res;
    }
    
    @FXML
    private void viewGuests(ActionEvent event) {
        new homepage.HomePageController().viewGuests(event);
    }

}
