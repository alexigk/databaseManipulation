package com.backend;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

/**
 * Created Dummy Employes initializing all tables
 * */

@WebServlet(name = "createDummy", value = "/createDummy")
public class createDummy extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            Connection con = Connector.connectDatabase("hy360");
            Statement stmt = con.createStatement();
            int rs  = 0;
                 rs =  stmt.executeUpdate("INSERT INTO Employee (FirstName, LastName, department, IBAN, BANK_NAME, PHONE, isMarried, isinactive,Address)\n"+
                    "  VALUES('Aris', 'Manoslis', 'CSD', \"thenewIBan\", \"CentralBank\",\"6945878952\",true,false, \"The Typical Address\");\n" );
               rs = stmt.executeUpdate(     "INSERT INTO EmployClasses (EID, administration, permanent, startDate) \n"+
                    "  VALUES(LAST_INSERT_ID(),true, false, '2020-11-01');\n" );
                rs = stmt.executeUpdate(    "INSERT INTO symbash (EID, endDate, salarySymbasy) \n" +
                    "  VALUES(LAST_INSERT_ID(),'2025-10-20', 650.00);\n");
                ResultSet set = stmt.executeQuery("Select EID FROM Employee WHERE IBAN = 'thenewIBan'");
            set.next();
            int EmployeeOneId = set.getInt("EID");
            Salary EmployeeOneSalary = Salary.CreateSalaryByEid(EmployeeOneId);
            Salary.saveSalaryToDatabase(EmployeeOneSalary);
             rs =  stmt.executeUpdate("INSERT INTO Employee (FirstName, LastName, department, IBAN, BANK_NAME, PHONE, isMarried, isinactive,Address)\n"+
                    "  VALUES('Neos', 'Ipallilos', 'Mathematics', 'SecondIBAN', 'CentralBank','6945878935', true, false, 'The Second Address');\n" );
               rs = stmt.executeUpdate(     "INSERT INTO EmployClasses (EID, administration, permanent, startDate) \n"+
                    "  VALUES(LAST_INSERT_ID(),false, true, '2020-11-01');\n" );
                rs = stmt.executeUpdate(    "INSERT INTO symbash (EID, endDate, salarySymbasy) \n" +
                    "  VALUES(LAST_INSERT_ID(),null, 750.00);\n");
                 set = stmt.executeQuery("Select EID FROM Employee WHERE IBAN = 'SecondIBAN'");
            set.next();
            int EmployeeTwoId = set.getInt("EID");
            Salary EmployeeTwoSalary = Salary.CreateSalaryByEid(EmployeeTwoId);
            Salary.saveSalaryToDatabase(EmployeeTwoSalary);
            rs =  stmt.executeUpdate("INSERT INTO Employee (FirstName, LastName, department, IBAN, BANK_NAME, PHONE, isMarried, isinactive,Address)\n"+
                    "  VALUES('Tritos', 'Ipallilos', 'Biology', 'ThirdIBAN', 'Peraus Bank','6945810935', true, false, 'The Second Address');\n" );
            rs = stmt.executeUpdate(     "INSERT INTO EmployClasses (EID, administration, permanent, startDate) \n"+
                    "  VALUES(LAST_INSERT_ID(),true, true, '2020-11-01');\n" );
            rs = stmt.executeUpdate(    "INSERT INTO symbash (EID, endDate, salarySymbasy) \n" +
                    "  VALUES(LAST_INSERT_ID(),null, 750.00);\n");
            rs = stmt.executeUpdate(    "INSERT INTO Children (EID, childrenAge) \n" +
                    "  VALUES(LAST_INSERT_ID(),10);\n");
            rs = stmt.executeUpdate(    "INSERT INTO Children (EID, childrenAge) \n" +
                    "  VALUES(LAST_INSERT_ID(),8);\n");
            set = stmt.executeQuery("Select EID FROM Employee WHERE IBAN = 'ThirdIBAN'");
            set.next();
            int EmployeeThreeId = set.getInt("EID");
            Salary EmployeeThreeSalary = Salary.CreateSalaryByEid(EmployeeThreeId);
            Salary.saveSalaryToDatabase(EmployeeThreeSalary);
            rs =  stmt.executeUpdate("INSERT INTO Employee (FirstName, LastName, department, IBAN, BANK_NAME, PHONE, isMarried, isinactive,Address)\n"+
                    "  VALUES('Tetartos', 'Ipallilos', 'CSD', 'FourthIBAN', 'Pancretan Bank','6945810931', false, false, 'The Third Address');\n" );
            rs = stmt.executeUpdate(     "INSERT INTO EmployClasses (EID, administration, permanent, startDate) \n"+
                    "  VALUES(LAST_INSERT_ID(),false, false, '2020-11-01');\n" );
            rs = stmt.executeUpdate(    "INSERT INTO symbash (EID, endDate, salarySymbasy) \n" +
                    "  VALUES(LAST_INSERT_ID(),null, 650.00);\n");
            rs = stmt.executeUpdate(    "INSERT INTO Children (EID, childrenAge) \n" +
                    "  VALUES(LAST_INSERT_ID(),9);\n");
            rs = stmt.executeUpdate(    "INSERT INTO Children (EID, childrenAge) \n" +
                    "  VALUES(LAST_INSERT_ID(),7);\n");
            set = stmt.executeQuery("Select EID FROM Employee WHERE IBAN = 'FourthIBAN'");
            set.next();
            int EmployeeFourthId = set.getInt("EID");
            Salary EmployeeFourthSalary = Salary.CreateSalaryByEid(EmployeeFourthId);
            Salary.saveSalaryToDatabase(EmployeeFourthSalary);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
