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

@WebServlet(name = "Permanent", value = "/Permanent")
public class CreateNewPermanent extends HttpServlet {
    /**
     * If no id is Seted upon request all Permanent Employees are returned
     * If id is seted a specific employee is returned with eid = id
     * \**/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestID = request.getParameter("id");
        PrintWriter responseWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONArray permanentEmployeeArray = new JSONArray();
        if(requestID == null) {
            try {
                List<Employee> permanentEmployee = Employee.returnAllPermanentEmployee();
                for(Employee employeee : permanentEmployee) {
                    JSONObject employeeObject  = new JSONObject(employeee.toString());
                    permanentEmployeeArray.put(employeeObject);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {

                JSONObject employeeObject  = Employee.fetchEmployeeWholeData(Integer.parseInt(requestID));
                permanentEmployeeArray.put(employeeObject);
                System.out.println(permanentEmployeeArray);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        responseWriter.println(permanentEmployeeArray);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuffer  jsonObjectString  = new StringBuffer();
        PrintWriter responseWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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

            //Setting Start Date accordingly to next day of first Month if is not today
            LocalDate startDate = LocalDate.now();
            if(startDate.getDayOfMonth() > 1){
                startDate = LocalDate.of(startDate.getYear(),startDate.getMonthValue()+1,1);

            }

            EmployClasses employClass = new EmployClasses(currentEmployeeId,true,jsonObject.getBoolean("isAdministritative"),startDate);
            EmployClasses.saveEmployClassToDatabase(employClass);


            JSONArray childrenArray = jsonObject.getJSONArray("Children");
            if(childrenArray.length() > 0) {
                for (int i = 0; i < childrenArray.length(); i++) {
                    JSONObject child = childrenArray.getJSONObject(i);
                    Children child1 = new Children(currentEmployeeId,child.getInt("age"));
                    child1.saveChildToDatabase();
                }
            }





            symbash newSimbasi = new symbash(currentEmployeeId,null,750);
            newSimbasi.saveSimbasiToDatabase(newSimbasi);


            Salary EmployeeSalary = Salary.CreateSalaryByEid(currentEmployeeId);
            Salary.saveSalaryToDatabase(EmployeeSalary);
            responseWriter.print( EmployeeSalary.toString());
        }catch (JSONException e){
            responseWriter.println(e.toString());
            throw  e;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
