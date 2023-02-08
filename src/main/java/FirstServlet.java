import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/helloWorld")
public class FirstServlet extends HttpServlet{
    static final String DB_URL = "jdbc:mysql://localhost";
    static final String USER = "root";
    static final String PASS = " ";
    static final String databaseName = "testing";
    static final String QUERY = "SELECT  * FROM RT;";
    static final int port = 3307;
    @Override
    protected  void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        PrintWriter writer = res.getWriter();
        try {
//            Class.forName(dbDriver);
            Connection con = Connector.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Accounting;");
            while(rs.next()){
                writer.println(rs.getString("name"));
                writer.println(rs.getInt("id"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
//        String firstName = req.getParameter("name").toString();
        PrintWriter writer = res.getWriter();
//        writer.println(firstName);
        BufferedReader reader = req.getReader();
        String line = null;
        StringBuffer jb = new StringBuffer();
        while ((line = reader.readLine()) != null){
            jb.append(line);
        }
        JSONObject obj = new JSONObject(jb.toString());

        writer.println(obj.get("last_name"));
    }
}
