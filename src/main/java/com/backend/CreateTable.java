package com.backend;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * First Creates Database HY360
 *
 * Creates Tables
 *  1) Employee
 *  2) Children
 *  3) EmployClasses
 *  4) symbasy
 *  5) Salary
 * */
@WebServlet(name = "CreateTables", value = "/CreateTables")
public class CreateTable extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        try {
            Connection con = Connector.connect();
            try{
                Connector.createDatabase(con);
            }catch (Exception e){
                writer.println("<br>hy360 database already exists\n");
            }
            con = Connector.connectDatabase("hy360");
            boolean rs = Employee.CheckForEmployeeTable(con);

            if(rs == false){
                Employee.CreateEmployeeTable(con);
                writer.println("<br>Table Employee Created!\n");
            }else{
                writer.println("<br>Table Employee Already Exists\n");
            }


            rs = Children.checkForChildrenTable(con);
            if(rs == false){
               Children.createChildrenTable(con);
               writer.println("<br>Table Children Created!\n");
            }else {
                writer.println("<br>Children Table Already Exists\n");
            }

            rs = EmployClasses.checkForEmployClassesTable(con);
            if(rs == false){
                EmployClasses.createEmployClassesTable(con);
                writer.println("<br>EmployClasses Table Created!\n");
            }else{
                writer.println("<br>EmployClasses Table Already Exists \n");
            }

            rs = symbash.checkForSymbasyTable(con);

            if(rs == false){
                symbash.createSymbasyTable(con);
                writer.println("<br> Symbash Table Created \n");
            }else{
                writer.println("<br> Symbash Table Already Exists");
            }

            rs = Salary.CheckForSalaryTable(con);
            if(rs == false){
                Salary.createSalaryTable(con);
                writer.println("<br> Salary Table Created !");
            }else{
                writer.println("<br> Salary table already exists!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
