# TAMU DAYS - Hotel Reservation System

You may also refer this video for setup:
https://www.youtube.com/watch?v=8Q6tTWjVCbo

OR

To run this project, you need following softwares:
1. Apache Netbeans IDE 12.2 - It is an open-source integrated development environment (IDE) primarily focused on Java.          
2. XAMPP - It is a widely-used open-source software package that facilitates the setup and management of a local web development environment.
3. RedisInsight - It is a graphical user interface (GUI) and management tool for Redis, an open-source, in-memory data structure store.
4. MongoDB Atlas - It is designed to provide a simplified and efficient way to work with MongoDB databases in a cloud environment.
5. JDK 1.8 - It is a version of the Java Development Kit, which is a set of software development tools for developing Java applications.
6. jedis-5.1.0.jar - If you are working with Redis in a Java application, you can use the Jedis library to connect to your Redis server, send commands, and process the results.
7. mongo-java-driver-3.11.2.jar - If you are developing a Java application that needs to interact with a MongoDB database, you would use the MongoDB Java Driver. This includes tasks such as connecting to a MongoDB server, querying and updating data, and handling BSON documents.
8. charm-glisten-4.4.1-javadoc.jar - This JAR file typically contains the JavaDoc documentation for the Charm Glisten library version 4.4.1. JavaDoc provides information about classes, methods, and other aspects of the library to help developers understand how to use it.
9. charm-glisten-4.4.1.jar - This JAR file contains the compiled code for the Charm Glisten library version 4.4.1. Charm Glisten is a JavaFX library that provides additional UI components and styles for JavaFX applications.
10. jfoenix-8.0.8.jar - JFoenix is a JavaFX material design library. It includes UI controls and styles that follow the material design guidelines. Developers can use JFoenix to enhance the visual appearance of their JavaFX applications.
11. mysql-connector-java-5.1.23-bin.jar - This JAR file contains the MySQL Connector/J library version 5.1.23. Connector/J is a JDBC driver for MySQL databases, allowing Java applications to connect to and interact with MySQL databases.
12. mysql-connector-java-8.0.15.jar - Similar to the previous MySQL Connector/J library, this JAR file contains version 8.0.15. It's a newer version, and developers may choose between versions based on their application's compatibility and requirements.

## You can use this URI in your terminal to login to redis.

Cloud Redis URI: redis://:YESAw1tH0qbEoJhDzNCzO1e84d0w6iAW@redis-12931.c52.us-east-1-4.ec2.cl oud.redislabs.com:12931/0

Cloud Mongo URI: mongodb+srv://nihar1999:As9uyZ~k@cluster0.3kkupx9.mongodb.net/?retryWrites=true &w=majority

Mongo Database name: tamudays 

Mongo Collection name: guest

## After having all the necessary softwares and libraries:
1. Open Netbeans IDE, and open project folder submitted in this assignment in the IDE.
2. Download provided JARs and import one by one by going to the Libraries folder in the Projects section present at the left side of the IDE.
3. Right click on the Libraries folder, click Add Jar/Folder, and choose the relevant JAR file from your directory and finally click on Choose button.
4. Do the above step one by one for all the necessary JAR files.
5. Ensure that JDK 1.8 is selected by the Netbeans. This can be checked by going
to the Java Platforms in the Tools section present in the IDE’s menu bar.
6. AddplatformasJDK1.8andclickonClose.
7. Open XAMPP and click on StartAll to start all the servers.
8. Go to this link: http://localhost/phpmyadmin/index.php and create a new database named hotel.
9. Import the database hotel.sql attached in the zip for this assignment. This would import one table 'room' in the database.

After having the setup, it's time to run the application.

Click on the Run Project button (Green play button) located at the top of the IDE.
