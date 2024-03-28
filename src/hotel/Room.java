package hotel;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static project.Constants.ROOM_CAPACITY_DOUBLE;
import static project.Constants.ROOM_CAPACITY_SINGLE;
import static project.Constants.ROOM_CAPACITY_TRIPLE;
import static project.Constants.ROOM_TYPE_ECONOMY;
import static project.Constants.ROOM_TYPE_NORMAL;
import static project.Constants.ROOM_TYPE_VIP;
import project.DataBase;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomID")
    private int roomID;
    @Column(name = "room_Type")
    private String room_Type;
    @Column(name = "room_capacity")
        private String room_capacity;
    @Column(name = "Check_In_Date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date Check_In_Date;
    @Column(name = "Check_Out_Date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date Check_Out_Date;
    @Column(name = "isEmpty")
    private boolean isEmpty;

    public Room(String room_Type, String room_capacity, Date Check_In_Date, Date Check_Out_Date, boolean isEmpty) {
        this.room_Type = room_Type;
        this.room_capacity = room_capacity;
        this.Check_In_Date = Check_In_Date;
        this.Check_Out_Date = Check_Out_Date;
        this.isEmpty = isEmpty;
    }

    public Room() {
    }

    public boolean isIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoom_Type() {
        return room_Type;
    }

    public void setRoom_Type(String room_Type) {
        this.room_Type = room_Type;
    }

    public String getRoom_capacity() {
        return room_capacity;
    }

    public Date getCheck_In_Date() {
        return Check_In_Date;
    }

    public void setCheck_In_Date(Date Check_In_Date) {
        this.Check_In_Date = Check_In_Date;
    }

    public Date getCheck_Out_Date() {
        return Check_Out_Date;
    }

    public void setCheck_Out_Date(Date Check_Out_Date) {
        this.Check_Out_Date = Check_Out_Date;
    }

    public void setRoom_capacity(String room_capacity) {
        this.room_capacity = room_capacity;
    }

    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", room_Type=" + room_Type + ", room_capacity=" + room_capacity + ", Check_In_Date=" + Check_In_Date + ", Check_Out_Date=" + Check_Out_Date + ", isEmpty=" + isEmpty + '}';
    }

    public static void RoomBooking(Guest guest, Room room, int RoomId) {
        CheckIn(guest, room, RoomId);
        System.out.println("Guest room is booked");
    }

    public static void CheckIn(Guest guest, Room guestRoom, int RoomId) {

        System.out.println("Saving guest details");
        Guest.saveGuestDetails(guest);

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Room.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            session = factory.getCurrentSession();
            session.beginTransaction();

            System.out.println("\nFetching room having id: " + RoomId);

            Room room = (Room) session.get(Room.class, RoomId);

            room.setIsEmpty(guestRoom.isIsEmpty());
            room.setCheck_In_Date(guestRoom.getCheck_In_Date());
            room.setCheck_Out_Date(guestRoom.getCheck_Out_Date());

            session.getTransaction().commit();

            System.out.println("CheckIn Completed!");
        } finally {
            factory.close();
        }
    }

    public static int CancelBooking(int RoomId) {
        System.out.println("in Cancel Booking() ...");
        
        int flag = 1;
        
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Room.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        
        try {
        
            session.beginTransaction();

            System.out.println("\nFetching room having id: " + RoomId + " from cancel booking call");

            Room room = (Room) session.get(Room.class, RoomId);
        
            Date currentDate = new Date();
            if (room.getCheck_In_Date().before(currentDate)) {
                return 2;
            }
        
            if (Objects.isNull(room)) {
                return -1;
            } else {
                if (room.isIsEmpty()) {
                    flag = 0;
                }

                room.setIsEmpty(true);
                room.setCheck_Out_Date(new Date());
            }
            Guest.deleteGuest(RoomId);

            session.getTransaction().commit();

            System.out.println("Booking Canceled!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            factory.close();
        }
        return flag;
    }

    public static int checkOutGuest(int RoomId) {
        
        int flag = 1;

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Room.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            session = factory.getCurrentSession();
            session.beginTransaction();

            System.out.println("\nFetching room having id: " + RoomId);

            Room room = (Room) session.get(Room.class, RoomId);
            
            Date currentDate = new Date();
            if (!room.getCheck_In_Date().before(currentDate)) {
                return 2;
            }

            if (Objects.isNull(room)) {
                return -1;
            } else {
                if (room.isIsEmpty()) {
                    flag = 0;
                }

                room.setIsEmpty(true);
                room.setCheck_Out_Date(new Date());
            }
            Guest.deleteGuest(RoomId);

            session.getTransaction().commit();

            System.out.println("Check Out Completed!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            factory.close();
        }
        return flag;
    }

    public static Room getRoomAvailability(String RoomType, String RoomCapacity) {
        List<Room> AvailableRooms = DataBase.getAvailableRooms();
        int index = 0;
        while (index < AvailableRooms.size()) {
            if (AvailableRooms.get(index).getRoom_Type().equals(RoomType)
                    && AvailableRooms.get(index).getRoom_capacity().equals(RoomCapacity)) {
                return AvailableRooms.get(index);
            }
            index++;
        }
        return null;
    }

    public double nightCost(Room room) {
        double fees = 0.0;
        if (ROOM_TYPE_ECONOMY.equals(room.getRoom_Type()) && ROOM_CAPACITY_SINGLE.equals(room.getRoom_capacity())) {
            fees += 200;
        } else if (ROOM_TYPE_ECONOMY.equals(room.getRoom_Type()) && ROOM_CAPACITY_DOUBLE.equals(room.getRoom_capacity())) {
            fees += 150;
        } else if (ROOM_TYPE_ECONOMY.equals(room.getRoom_Type()) && ROOM_CAPACITY_TRIPLE.equals(room.getRoom_capacity())) {
            fees += 100;
        } else if (ROOM_TYPE_NORMAL.equals(room.getRoom_Type()) && ROOM_CAPACITY_SINGLE.equals(room.getRoom_capacity())) {
            fees += 250;
        } else if (ROOM_TYPE_NORMAL.equals(room.getRoom_Type()) && ROOM_CAPACITY_DOUBLE.equals(room.getRoom_capacity())) {
            fees += 200;
        } else if (ROOM_TYPE_NORMAL.equals(room.getRoom_Type()) && ROOM_CAPACITY_TRIPLE.equals(room.getRoom_capacity())) {
            fees += 150;
        } else if (ROOM_TYPE_VIP.equals(room.getRoom_Type()) && ROOM_CAPACITY_SINGLE.equals(room.getRoom_capacity())) {
            fees += 300;
        } else if (ROOM_TYPE_VIP.equals(room.getRoom_Type()) && ROOM_CAPACITY_DOUBLE.equals(room.getRoom_capacity())) {
            fees += 250;
        } else if (ROOM_TYPE_VIP.equals(room.getRoom_Type()) && ROOM_CAPACITY_TRIPLE.equals(room.getRoom_capacity())) {
            fees += 200;
        }
        return fees;
    }

}
