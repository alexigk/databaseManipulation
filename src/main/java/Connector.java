import java.sql.Connection;
import java.sql.*;
public class Connector {
    static final String DB_URL = "jdbc:mysql://localhost";
    static final String USER = "root";
    static final String PASS = " ";
    static final String databaseName = "testing";
    static final String QUERY = "SELECT  * FROM RT;";
    static final int port = 3306;
//    public static void main(String[] args) {
//        // Open a connection
////        Class<?> Class = Class.forName("com.mysql.cj.jdbc.Driver");
//        try{
//                    Connection con = DriverManager.getConnection(
//                            DB_URL + ":" + port + "/" + databaseName + "?characterEncoding=UTF-8", USER, null);
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(QUERY);
//            // Extract data from result set
//            while (rs.next()) {
//                // Retrieve by column name
//                System.out.print("ID: " + rs.getInt("id"));
//                System.out.print(", Age: " + rs.getString("name"));
//                System.out.print(", First: " + rs.getString("last_name"));
//                System.out.println(", Last: " + rs.getString("country"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public static Connection connect() throws SQLException {
        Connection con = DriverManager.getConnection(
                DB_URL + ":" + port + "/" + databaseName + "?characterEncoding=UTF-8", USER, null);
        return con;
    }


}
