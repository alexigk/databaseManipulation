package com.backend;

import org.json.JSONArray;
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

@WebServlet(name = "SalaryReport", value = "/SalaryReport")
public class SalaryReport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuffer  jsonObjectString  = new StringBuffer();
        PrintWriter responseWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonLine = null;
        JSONArray salaryArray = new JSONArray();
        try {
            while ((jsonLine = reader.readLine()) != null){
                jsonObjectString.append(jsonLine);
//                responseWriter.println(jsonLine.toString());
            }

        }catch (Error e){
            responseWriter.println("Reading body failed \n");
        }
        JSONObject newCategory = new JSONObject(jsonObjectString.toString());
        System.out.println(newCategory.get("Category"));
        String category = newCategory.getString("Category");
        try {
            Connection con = Connector.connectDatabase("hy360");
            if(category.equals("Temporary")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Employee.LastName, Employee.FirstName, Salary.misthos, Salary.epidoma, Salary.finalSummary, EmployClasses.permanent FROM Employee\n" +
                        "LEFT JOIN Salary on Salary.EID = Employee.EID\n" +
                        "RIGHT JOIN EmployClasses on EmployClasses.EID = Employee.EID\n" +
                        "WHERE EmployClasses.permanent = false ORDER BY Salary.finalSummary DESC");
                salaryArray = returnJSONForPermanent(rs);
            }else if(category.equals("Permanent") ) {
                Statement stmt = con.createStatement();
                System.out.println(1);
                ResultSet rs = stmt.executeQuery("SELECT Employee.LastName, Employee.FirstName, Salary.misthos, Salary.epidoma, Salary.finalSummary, EmployClasses.permanent FROM Employee\n" +
                        "LEFT JOIN Salary on Salary.EID = Employee.EID\n" +
                        "RIGHT JOIN EmployClasses on EmployClasses.EID = Employee.EID\n" +
                        "WHERE EmployClasses.permanent = true ORDER BY Salary.finalSummary DESC; ");
                salaryArray = returnJSONForPermanent(rs);
            }else if(category.equals("Administration")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Employee.LastName, Employee.FirstName, Salary.misthos, Salary.epidoma, Salary.finalSummary, EmployClasses.administration FROM Employee\n" +
                        "LEFT JOIN Salary on Salary.EID = Employee.EID\n" +
                        "RIGHT JOIN EmployClasses on EmployClasses.EID = Employee.EID\n" +
                        "WHERE EmployClasses.administration = true ORDER BY Salary.finalSummary DESC;");
                salaryArray = returnJSONForAdministration(rs);
            }else if(category.equals("Instructional")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Employee.LastName, Employee.FirstName, Salary.misthos, Salary.epidoma, Salary.finalSummary, EmployClasses.administration FROM Employee\n" +
                        "LEFT JOIN Salary on Salary.EID = Employee.EID\n" +
                        "RIGHT JOIN EmployClasses on EmployClasses.EID = Employee.EID\n" +
                        "WHERE EmployClasses.administration = false ORDER BY Salary.finalSummary DESC;");
                salaryArray = returnJSONForAdministration(rs);

            }else if(category.equals("All")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Employee.LastName, Employee.FirstName, Salary.misthos, Salary.epidoma, Salary.finalSummary, EmployClasses.administration, EmployClasses.permanent FROM Employee\n" +
                        "LEFT JOIN Salary on Salary.EID = Employee.EID\n" +
                        "RIGHT JOIN EmployClasses on EmployClasses.EID = Employee.EID\n" +
                        " ORDER BY Salary.finalSummary DESC;");
                salaryArray = returnJSONF(rs);
            }else if(category.equals("min/max")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(Salary.finalSummary),\n" +
                        "Employee.FirstName, Employee.LastName FROM Salary\n" +
                        "LEFT JOIN Employee on Salary.EID = Employee.EID\n" +
                        "WHERE Salary.EID = Employee.EID;");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(salaryArray);
        responseWriter.println(salaryArray);
    }
    public static JSONArray returnJSONForPermanent(ResultSet rs) throws SQLException {
        JSONArray newArray = new JSONArray();
        while(rs.next()) {
            JSONObject newObject = new JSONObject();
            newObject.put("firstName",rs.getString("FirstName"));
            newObject.put("lastName",rs.getString("LastName"));
            newObject.put("epidoma",rs.getFloat("epidoma"));
            newObject.put("misthos",rs.getInt("misthos"));
            newObject.put("finalSummary",rs.getFloat("finalSummary"));
            newObject.put("permanent",rs.getBoolean("permanent"));
            newArray.put(newObject);
        }
        System.out.println(newArray);
        return newArray;
    }
    public static JSONArray returnJSONForAdministration(ResultSet rs) throws SQLException {
        JSONArray newArray = new JSONArray();
        while(rs.next()) {
            JSONObject newObject = new JSONObject();
            newObject.put("firstName",rs.getString("FirstName"));
            newObject.put("lastName",rs.getString("LastName"));
            newObject.put("epidoma",rs.getFloat("epidoma"));
            newObject.put("misthos",rs.getInt("misthos"));
            newObject.put("finalSummary",rs.getFloat("finalSummary"));
            newObject.put("administration",rs.getBoolean("administration"));
            newArray.put(newObject);
        }
        return newArray;
    }

    public static JSONArray returnJSONF(ResultSet rs) throws SQLException {
        JSONArray newArray = new JSONArray();
        while(rs.next()) {
            JSONObject newObject = new JSONObject();
            newObject.put("firstName",rs.getString("FirstName"));
            newObject.put("lastName",rs.getString("LastName"));
            newObject.put("epidoma",rs.getFloat("epidoma"));
            newObject.put("misthos",rs.getInt("misthos"));
            newObject.put("finalSummary",rs.getFloat("finalSummary"));
            newObject.put("administration",rs.getBoolean("administration"));
            newObject.put("permanent",rs.getBoolean("permanent"));

            newArray.put(newObject);
        }
        return newArray;
    }
}
