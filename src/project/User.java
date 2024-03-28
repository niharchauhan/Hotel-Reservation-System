package project;

import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import redis.clients.jedis.Jedis;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_name")
    private String username;
    @Column(name = "user_pass")
    private String password;
    @Column(name = "is_admin")
    private boolean is_admin;
    
    // Redis Cloud URI
    private static String redisUri = "redis://:YESAw1tH0qbEoJhDzNCzO1e84d0w6iAW@redis-12931.c52.us-east-1-4.ec2.cloud.redislabs.com:12931/0";

    // Connect to Redis server using URI
    private static Jedis jedis;

    public User() {
    }

    public User(String username, String password, boolean is_admin) {
        this.username = username;
        this.password = password;
        this.is_admin = is_admin;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static boolean isUserValid(User user) {
        jedis = new Jedis(redisUri);
        Map<String, String> userDetails = jedis.hgetAll("user:" + user.getUsername());
        
        if (userDetails.isEmpty()) {
            return false;
        }
        
        return true;
        
//        List<User> users = DataBase.getUsers();
//        int index = 0;
//        while (index < users.size()) {
//            if (users.get(index).getUsername().equals(user.getUsername())) {
//                if (users.get(index).getPassword().equals(user.getPassword())) {
//                    return true;
//                }
//            }
//            index++;
//        }
//        return false;
    }

    public static boolean isUserAdmin(User user) {
        jedis = new Jedis(redisUri);
        Map<String, String> userDetails = jedis.hgetAll("user:" + user.getUsername());
        
        if (userDetails.isEmpty()) {
            return false;
        }
        
        for (Map.Entry<String, String> entry : userDetails.entrySet()) {
            if ("is_admin".equals(entry.getKey()) && ("true".equals(entry.getValue()) || "1".equals(entry.getValue()))) {
                return true;
            }
        }
        
        return false;
//        List<User> users = DataBase.getUsers();
//        int index = 0;
//        while (index < users.size()) {
//            if (users.get(index).getUsername().equals(user.getUsername())) {
//                if (users.get(index).getPassword().equals(user.getPassword())) {
//                    if (users.get(index).isIs_admin()) {
//                        return true;
//                    }
//                }
//            }
//            index++;
//        }
//        return false;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", is_admin=" + is_admin + '}';
    }
}
