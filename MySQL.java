// COMPILE:
// javac -cp mysql.jar MySQL.java 

// RUN:
// java -cp mysql.jar:. MySQL

import java.sql.*; // STEP 1: Import required packages

public class MySQL {
    //  Database credentials
    static final String MYDB = "cinema_gruppeac";
    static final String USER = "grproac";
    static final String PASS = "grp2017";

    // JDBC driver name and database URL
    static final String DB_URL = "jdbc:mysql://mydb.itu.dk/" + MYDB;
        
    public static void main(String[] args) {
    Connection connection = null;
    Statement statement = null;
    String sql = null;
    ResultSet rs = null;
    
    try {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver()); // STEP 2: Register JDBC driver
        connection = DriverManager.getConnection(DB_URL, USER, PASS); // STEP 3: Open a connection
        statement = connection.createStatement(); // STEP 4: Execute a query
        
        sql = "SELECT seat_row_id, seat_row_name, seat_id, seat_name " +
              "FROM  `seat_row_seats` " +
              "LEFT JOIN seats ON  `fk_seat_id` =  `seat_id` " +
              "LEFT JOIN seat_rows ON  `fk_seat_row_id` =  `seat_row_id` " + 
              "LEFT JOIN hall_seat_rows ON hall_seat_rows.`fk_seat_row_id` =  `seat_row_id` " + 
              "WHERE fk_cinema_hall_id =1"; // implicit semi-colon!
        rs = statement.executeQuery(sql);
        //STEP 5: Extract data from result set
        while (rs.next()) { //Retrieve by column name
        int seat_row_id  = rs.getInt("seat_row_id");
        String seat_row_name = rs.getString("seat_row_name");
        
        int seat_id  = rs.getInt("seat_id");
        String seat_name = rs.getString("seat_name");
        
        //Display values
        System.out.println("seat_row_id: '" + seat_row_id + "', seat_row_name: '" + seat_row_name + "'");
        System.out.println("seat_id: '" + seat_row_id + "', seat_name: '" + seat_row_name + "'");
        }
    } catch(Exception e) { // handle errors:
        e.printStackTrace();
    } finally {
        try {
        rs.close();
        connection.close();
        } catch(Exception e) {
        e.printStackTrace();
        }
    }
    }
}