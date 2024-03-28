package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.*;
import java.io.*;

public class Project extends Application {

    @Override
    public void start(Stage stage) {
        try {
            
            int port = 8090;
        
        
            System.out.println("Server is listening on port " + port);
            
            
             ServerSocket serverSocket = new ServerSocket(port);
            new Thread(() -> {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        new Thread(new ClientHandler(clientSocket)).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        
            Parent root = FXMLLoader.load(getClass().getResource(Paths.LOGINVIEW));
            
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.DECORATED);
             
            stage.setScene(scene);
            stage.show();
                // Load Login.fxml
            
        } catch (Exception ex) {
            System.out.println("Error occurred in Login Screen");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
            response += "<html><body><h1>Hello from Server</h1></body></html>";

            out.println(response);
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
