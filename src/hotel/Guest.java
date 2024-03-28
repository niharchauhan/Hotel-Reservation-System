package hotel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@Entity
@Table(name = "guest")
public class Guest implements RoomFees {

    @Column(name = "roomID")
    private int roomID;
    @Column(name = "number_of_days")
    private int numberOfDaysStayed;
    @Column(name = "Name")
    private String Name;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "nationality")
    private String nationality;
    @Id
    @Column(name = "passport_number")
    private String passportNumber;
    @Column(name = "phoneNo")
    private String phoneNo;
    @Column(name = "card_number")
    private String CardNumber;
    @Column(name = "card_pass")
    private String cardPass;
    @Column(name = "fees")
    private double Fees;
    
    private static String mongoUri = "mongodb+srv://nihar1999:As9uyZ~k@cluster0.3kkupx9.mongodb.net/?retryWrites=true&w=majority";

    public Guest() {
    }

    public Guest(int roomID, int numberOfDaysStayed, String Name, String email, String address, String city, String nationality, String passportNumber, String phoneNo, String CardNumber, String cardPass, double Fees) {
        this.roomID = roomID;
        this.numberOfDaysStayed = numberOfDaysStayed;
        this.Name = Name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.nationality = nationality;
        this.passportNumber = passportNumber;
        this.phoneNo = phoneNo;
        this.CardNumber = CardNumber;
        this.cardPass = cardPass;
        this.Fees = Fees;
    }
    
    // Constructor to create a Guest object from a Document
    public Guest(Document document) {
        this.roomID = document.getInteger("roomID");
        this.numberOfDaysStayed = document.getInteger("number_of_days");
        this.Name = document.getString("Name");
        this.email = document.getString("email");
        this.address = document.getString("address");
        this.city = document.getString("city");
        this.nationality = document.getString("nationality");
        this.passportNumber = document.getString("passport_number");
        this.phoneNo = document.getString("phoneNo");
        this.CardNumber = document.getString("card_number");
        this.cardPass = document.getString("card_pass");
        this.Fees = document.getDouble("fees");
    }

    public double getFees() {
        return Fees;
    }

    public void setFees(double Fees) {
        this.Fees = Fees;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String CardNumber) {
        this.CardNumber = CardNumber;
    }

    public String getCardPass() {
        return cardPass;
    }

    public void setCardPass(String cardPass) {
        this.cardPass = cardPass;
    }

    public int getNumberOfDaysStayed() {
        return numberOfDaysStayed;
    }

    public void setNumberOfDaysStayed(int numberOfDaysStayed) {
        this.numberOfDaysStayed = numberOfDaysStayed;
    }

    @Override
    public String toString() {
        return "Guest{" + "roomID=" + roomID + ", numberOfDaysStayed=" + numberOfDaysStayed + ", Name=" + Name + ", email=" + email + ", address=" + address + ", city=" + city + ", nationality=" + nationality + ", passportNumber=" + passportNumber + ", phoneNo=" + phoneNo + ", CardNumber=" + CardNumber + ", cardPass=" + cardPass + '}';
    }

    @Override
    public double CustomerRoomFees(Room room) {
        double roomFees = 0;
        if (numberOfDaysStayed == 0) {
            roomFees += room.nightCost(room);
        }
        int index = 0;
        while (index < numberOfDaysStayed) {
            roomFees += room.nightCost(room);
            index++;
        }
        return roomFees;
    }

    public static void saveGuestDetails(Guest guest) {
        
        MongoClientURI uri = new MongoClientURI(mongoUri);
        MongoClient mongoClient = MongoClients.create(String.valueOf(uri));

        // Connect to the database
        MongoDatabase database = mongoClient.getDatabase("tamudays");
        
        // Access a collection
        MongoCollection<Document> guestCollection = database.getCollection("guest");
        
        Document guestDocument = new Document()
                .append("roomID", guest.roomID)
                .append("Name", guest.Name)
                .append("email", guest.email)
                .append("address", guest.address)
                .append("city", guest.city)
                .append("nationality", guest.nationality)
                .append("passport_number", guest.passportNumber)
                .append("phoneNo", guest.phoneNo)
                .append("card_number", guest.CardNumber)
                .append("card_pass", guest.cardPass)
                .append("number_of_days", guest.numberOfDaysStayed)
                .append("fees", guest.Fees);
        
        // Insert the document into the collection
        guestCollection.insertOne(guestDocument);
        
        System.out.println("Guest details are saved!");
    }

    public static void deleteGuest(int roomID) {
        
        MongoClientURI uri = new MongoClientURI(mongoUri);
        MongoClient mongoClient = MongoClients.create(String.valueOf(uri));

        // Connect to the database
        MongoDatabase database = mongoClient.getDatabase("tamudays");
        
        // Access a collection
        MongoCollection<Document> guestCollection = database.getCollection("guest");
        
        Document filter = new Document("roomID", roomID);
        
        // Delete a single document that matches the filter
        guestCollection.deleteOne(filter);
        
        System.out.println("Guest Deletion completed");

    }

}
