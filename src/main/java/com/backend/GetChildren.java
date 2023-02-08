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
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GetChildren", value = "/GetChildren")
public class GetChildren extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeEID = request.getParameter("id");

        PrintWriter responseWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONArray childrenArray = new JSONArray();
        if(employeeEID == null) {
            throw new RuntimeException("NO ID PARSED");
        }else{
            try {

                List<Children> children = Children.fetchChildrenByEID(Integer.parseInt(employeeEID));
                for(Children child : children) {
//                    System.out.println(child.toString());
                    childrenArray.put(new JSONObject(child.toString()));
                }
                responseWriter.println(childrenArray);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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
        JSONObject newChild = new JSONObject(jsonObjectString.toString());
        Children child = new Children(newChild.getInt("EID"),newChild.getInt("age"));
        try {
            int returnedStatus =  child.saveChildToDatabase();
            System.out.println(returnedStatus);
                Salary.recalculateSalaryAndUpdate(newChild.getInt("EID"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
