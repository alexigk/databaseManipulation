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
import java.util.List;

@WebServlet(name = "Temporary", value = "/Temporary")
public class Temporary extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String temporaryEID = request.getParameter("id");

//        System.out.println(temporaryEID);
        PrintWriter responseWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONArray temporaryArray = new JSONArray();

        if(temporaryEID != null){

            try {

                JSONObject employeeObject= Employee.fetchEmployeeWholeData(Integer.parseInt(temporaryEID));
                temporaryArray.put(employeeObject);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                List<Employee> temporaryEmployees = Employee.returnAllTemporaryEmployee();
                for(Employee employee: temporaryEmployees) {
                    JSONObject object = new JSONObject(employee.toString());
                    temporaryArray.put(object);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
        responseWriter.println(temporaryArray);

    }

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
            LocalDate startDate = LocalDate.now();
            if(startDate.getDayOfMonth() > 1){
                startDate = LocalDate.of(startDate.getYear(),startDate.getMonthValue()+1,1);
            }

            EmployClasses employClass = new EmployClasses(currentEmployeeId,false,jsonObject.getBoolean("isAdministritative"), startDate);
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

                symbash newSimbasi = new symbash(currentEmployeeId,LocalDate.parse(jsonObject.getString("endDate")),650);
                newSimbasi.saveSimbasiToDatabase(newSimbasi);

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
