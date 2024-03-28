package project;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import hotel.Guest;
import hotel.Room;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static project.Constants.ROOM_CAPACITY_TRIPLE;
import static project.Constants.ROOM_TYPE_VIP;
import redis.clients.jedis.Jedis;

public class DataBase {
    
    // Redis Cloud URI
    private static String redisUri = "redis://:YESAw1tH0qbEoJhDzNCzO1e84d0w6iAW@redis-12931.c52.us-east-1-4.ec2.cloud.redislabs.com:12931/0";

    // Connect to Redis server using URI
    private static Jedis jedis;
    
    private static String mongoUri = "mongodb+srv://nihar1999:As9uyZ~k@cluster0.3kkupx9.mongodb.net/?retryWrites=true&w=majority";

    public static void CheckConnection() throws SQLException {
        String dataBaseName = "hotel";
        String url = "jdbc:mysql://localhost:3306/" + dataBaseName;
        Connection con = DriverManager.getConnection(url, "root", "");
        System.out.println("Connection established to database");
        con.close();
    }

    //  Rooms 
    public void Save10Room() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Room.class).buildSessionFactory();

        Session session = factory.getCurrentSession();

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        session.beginTransaction();

        try {
            int index = 0;
            while (index < 10) {

                System.out.println("Saving the room...");
                Room r = new Room(ROOM_TYPE_VIP, ROOM_CAPACITY_TRIPLE, date, date, true);
                session.save(r);

                System.out.println("Done!");
                index++;

            }
        } catch (Exception e) {
            System.out.println("SaveRoom Error");

        } finally {
            session.getTransaction().commit();
            factory.close();
        }
    }

    public void SaveRoom(Room room) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Room.class).buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            session.beginTransaction();

            session.save(room);

            // commit transaction
            session.getTransaction().commit();

            System.out.println("Room saving completed!");
        } catch (Exception e) {
            System.out.println("SaveRoom Error");
        } finally {
            factory.close();
        }
    }

    public void ReadRoom(Room room) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Room.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            session = factory.getCurrentSession();
            session.beginTransaction();

            Room r = (Room) session.get(Room.class, room.getRoomID());
            session.getTransaction().commit();

            System.out.println("View rooms completed");
        } finally {
            factory.close();
        }
    }

    public static List<Room> getrooms() {
        List<Room> rooms = null;
        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Room.class).buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            session.beginTransaction();

            rooms = session.createQuery("from Room").list();
            displayList(rooms);
            session.getTransaction().commit();

            System.out.println("View Rooms done");
        } finally {
            factory.close();
        }

        return rooms;
    }

    public static List<Room> getAvailableRooms() {
        List<Room> rooms = null;
        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Room.class).buildSessionFactory();

        // create session
        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();

            rooms = session.createQuery("from Room r where r.isEmpty=true").list();
            displayList(rooms);
            session.getTransaction().commit();

            System.out.println("getRooms() Done!");
        } finally {
            factory.close();
        }

        return rooms;
    }

    // Users 
    public static boolean SaveUser(User user) {
        jedis = new Jedis(redisUri);
        Map<String, String> userDetails = jedis.hgetAll("user:" + user.getUsername());
        
        if (!userDetails.isEmpty()) {
            return false;
        }
        
        jedis.hset("user:" + user.getUsername(), "user_pass", user.getPassword());
        jedis.hset("user:" + user.getUsername(), "is_admin", String.valueOf(user.isIs_admin()));
        System.out.println("Saving user completed!");
        return true;
    }

    public static boolean isUserNameValid(String username) {
        jedis = new Jedis(redisUri);
        Map<String, String> userDetails = jedis.hgetAll("user:" + username);
        
        
        if (!userDetails.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean DeleteUser(String username) {
       
        if (!isUserNameValid(username)) {
            return false;
        }

        try {
            jedis = new Jedis(redisUri);
            jedis.del("user:" + username);
            System.out.println("Guest deletion completed!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static List<User> getUsers() {
        jedis = new Jedis(redisUri);
        List<User> users = new ArrayList<>();
        Set<String> keys = jedis.keys("user:*");
        for (String key : keys) {
            User user = new User();
            String[] partition = key.split(":");
            String username = partition[1];

            user.setUsername(username);
            String password = jedis.hget("user:" + username, "user_pass");

            user.setPassword(password);
            String isAdmin = jedis.hget("user:" + username, "is_admin");
            if ("1".equals(isAdmin) || "true".equals(isAdmin)) {
                user.setIs_admin(true);
            }
          
            users.add(user);
        }

        displayList(users);

        System.out.println("Fetching users Done!");
        return users;
    }

    // guests
    public static List<Guest> getGuests() {
        
        MongoClientURI uri = new MongoClientURI(mongoUri);
        MongoClient mongoClient = MongoClients.create(String.valueOf(uri));

        // Connect to the database
        MongoDatabase database = mongoClient.getDatabase("tamudays");
        
        // Access a collection
        MongoCollection<Document> guestCollection = database.getCollection("guest");
        
        // Use find to get all documents in the collection
        MongoCursor<Document> cursor = guestCollection.find().iterator();
        
        List<Guest> guests = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Guest guest = new Guest(document);
                guests.add(guest);
            }
        } finally {
            cursor.close();
        }
        
        displayList(guests);
        
        System.out.println("getGuest() Done!");
        
        return guests;
    }

    // print Data 
    public static <T> void displayList(List<T> list) {
        for (T tempUser : list) {
            System.out.println(tempUser);
        }
    }

}
