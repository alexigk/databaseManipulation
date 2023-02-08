package com.backend;

import org.json.JSONArray;
import org.json.JSONException;
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

@WebServlet(name = "EmployeeCreation", value = "/EmployeeCreation")
public class EmployeeCreation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Creating an Employee using the According Json object in a POST Request
     * after creating the Employee the rest data is saved using the according eid
     * Creates associated EmployClass to EID
     * Creates children associated to EID
     * Creates symbasi associated to EID
     * Calculates Salary at the end receiving data from Above
     * */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        BufferedReader reader = request.getReader();
        StringBuffer  jsonObjectString  = new StringBuffer();
        PrintWriter responseWriter = response.getWriter();
        String jsonLine = null;
        try {
            while ((jsonLine = reader.readLine()) != null){
                jsonObjectString.append(jsonLine);
//                responseWriter.println(jsonLine.toString());
            }

        }catch (Error e){
            responseWriter.println("Reading body failed \n");
        }

        try{
            JSONObject jsonObject = new JSONArray(jsonObjectString.toString()).getJSONObject(0);
//            responseWriter.println(jsonObject.get("firstName"));



            Employee newEmployee = new Employee(jsonObject.getString("firstName"),jsonObject.getString("lastName"),jsonObject.getBoolean("isMarried"),jsonObject.getString("department"),jsonObject.getString("phone"),jsonObject.getString("IBAN"),jsonObject.getString("bankName"), jsonObject.getBoolean("isinactive"),jsonObject.getString("address"));
            int currentEmployeeId = Employee.saveEmployeeInDatabase(newEmployee);


            EmployClasses employClass = new EmployClasses(currentEmployeeId,jsonObject.getBoolean("permanent"),jsonObject.getBoolean("isAdministritative"), LocalDate.parse(jsonObject.getString("startDate")));
            EmployClasses.saveEmployClassToDatabase(employClass);


            JSONArray childrenArray = jsonObject.getJSONArray("Children");
            if(childrenArray.length() > 0) {
                responseWriter.println("Children created");
                for (int i = 0; i < childrenArray.length(); i++) {
                        JSONObject child = childrenArray.getJSONObject(i);
                        Children child1 = new Children(currentEmployeeId,child.getInt("age"));
                        child1.saveChildToDatabase();
                }
            }


            /***
             * if permanent == true Symbasi endDate is null
             *  Salary = 750
             * else is get from Object
             *  Salary = salarySymbash
             * */

            if(jsonObject.getBoolean("permanent") == false){
                symbash newSimbasi = new symbash(currentEmployeeId,LocalDate.parse(jsonObject.getString("endDate")),jsonObject.getFloat("salarySymbash"));
                    newSimbasi.saveSimbasiToDatabase(newSimbasi);
            }else{
                symbash newSimbasi = new symbash(currentEmployeeId,null,750);
                newSimbasi.saveSimbasiToDatabase(newSimbasi);
            }

            responseWriter.println("currentEmployeeId: " + currentEmployeeId);
            Salary EmployeeSalary = Salary.CreateSalaryByEid(currentEmployeeId);
            Salary.saveSalaryToDatabase(EmployeeSalary);
            responseWriter.println("Current Employee Salary:" + EmployeeSalary.toString());
        }catch (JSONException e){
            responseWriter.println(e.toString());
            throw  e;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
