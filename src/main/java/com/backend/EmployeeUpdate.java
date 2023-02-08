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

@WebServlet(name = "EmployeeUpdate", value = "/EmployeeUpdate")
public class EmployeeUpdate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
    /**
     * Receives Body with EID and updated data of Employee
     * Updates Employee Class and Updates Employee with Current EiD
     *
     * */
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

        Employee updateEmployee = new Employee(bodyObject.getInt("EID"),bodyObject.getString("firstName"),bodyObject.getString("lastName"),bodyObject.getBoolean("isMarried"),bodyObject.getString("department"),bodyObject.getString("phone"),bodyObject.getString("IBAN"),bodyObject.getString("bankName"),bodyObject.getBoolean("isinactive"),bodyObject.getString("address"));
        try {
            Employee.UpdateEmployeeRow(updateEmployee);
            Salary newSalary = Salary.recalculateSalaryAndUpdate(updateEmployee.getEID());
            responseWriter.println(newSalary.toString());
        }catch (SQLException e){
            throw new RuntimeException(e);

        }


    }
}
