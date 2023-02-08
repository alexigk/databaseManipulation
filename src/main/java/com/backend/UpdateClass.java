package com.backend;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "UpdateClass", value = "/UpdateClass")
public class UpdateClass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuffer  jsonObjectString  = new StringBuffer();
        PrintWriter responseWriter = response.getWriter();
        String jsonLine = null;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject bodyObject = new JSONObject();
        try {
            while ((jsonLine = reader.readLine()) != null){
                jsonObjectString.append(jsonLine);
//                responseWriter.println(jsonLine.toString());
            }

        }catch (Error e){
            responseWriter.println("Reading body failed \n");
        }
        bodyObject = new JSONObject(jsonObjectString.toString());
//        responseWriter.println(bodyObject);
        try {
            EmployClasses updateClass = new EmployClasses(bodyObject.getInt("EID"),bodyObject.getBoolean("permanent"),bodyObject.getBoolean("administration"), LocalDate.parse(bodyObject.getString("startDate")));

            EmployClasses.updateEmployeeClassRow(updateClass);
            symbash.updateSymbasiByEid(updateClass.getEID());
            Salary updatedSalary = Salary.recalculateSalaryAndUpdate(updateClass.getEID());
            responseWriter.println(updatedSalary.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
