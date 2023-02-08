package com.backend;

import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WhereEver There is an ID is a connected table
 */


public class Employee {
   private int EID;
   private String firstName;
   private String lastName;
   private boolean isMarried;
   private String department;
   private String phone;
   private String IBAN;
   private String bankName;
   private boolean isInactive;
   private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEID() {
        return EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public boolean isInactive() {
        return isInactive;
    }

    public void setInactive(boolean inactive) {
        isInactive = inactive;
    }

    public static void CreateEmployeeTable(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
         stmt.executeUpdate("CREATE TABLE    Employee (\n" +
                "    EID int PRIMARY KEY AUTO_INCREMENT,\n" +
                "    LastName varchar(255),\n" +
                "    FirstName varchar(255),\n" +
                "    Address varchar(255),\n" +
                "    PHONE varchar(10),\n" +
                "    IBAN varchar(40) UNIQUE,\n" +
                "    BANK_NAME varchar(225),\n" +
                "    isinactive boolean,\n" +
                "    department varchar(255)\n," +
                 "   isMarried boolean" +
                ");");
    }

    public static boolean CheckForEmployeeTable(Connection con) throws SQLException{
        DatabaseMetaData databaseMetaData = con.getMetaData();
        ResultSet rs = databaseMetaData.getTables(null,null,"Employee", new String[] {"TABLE"});
        return  rs.next();
    }


    public static int saveEmployeeInDatabase(Employee employee) throws SQLException {

        Connection con = Connector.connectDatabase("hy360");
        PreparedStatement preStmt = con.prepareStatement("insert into Employee( LastName, FirstName, Address, PHONE, IBAN, BANK_NAME, isinactive, department,isMarried) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");


        preStmt.setString(1, employee.getLastName());
        preStmt.setString(2, employee.getFirstName());
        preStmt.setString(3,employee.getAddress());
        preStmt.setString(4,employee.getPhone());
        preStmt.setString(5, employee.getIBAN());
        preStmt.setString(6, employee.getBankName());
        preStmt.setBoolean(7, employee.isInactive());
        preStmt.setString(8, employee.getDepartment());
        preStmt.setBoolean(9, employee.isMarried());

        preStmt.executeUpdate();
        preStmt.close();
        PreparedStatement stmt = con.prepareStatement("SELECT EID FROM Employee WHERE IBAN = ?");
        stmt.setString(1,employee.getIBAN());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1);

    }

    public Employee(String firstName, String lastName, boolean isMarried, String department, String phone, String IBAN, String bankName, boolean isInactive, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isMarried = isMarried;
        this.department = department;
        this.phone = phone;
        this.IBAN = IBAN;
        this.bankName = bankName;
        this.isInactive = isInactive;
        this.address = address;
    }


    public static Employee fetchEmployeeFromDatabase(int eid) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Employee WHERE EID = " + eid + ";");
        rs.next();
        Employee empl = new Employee(rs.getInt("EID"),rs.getString("FirstName"),rs.getString("LastName"),rs.getBoolean("isMarried"),rs.getString("department"),rs.getString("PHONE"),rs.getString("IBAN"),rs.getString("BANK_NAME"),rs.getBoolean("isinactive"),rs.getString("address"));
        return empl;
    }

    public static JSONObject fetchEmployeeWholeData(int eid) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        JSONObject employeeObject = new JSONObject();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Employee\n" +
                "LEFT  JOIN  EmployClasses ON Employee.EID = EmployClasses.EID\n" +
                "LEFT  JOIN  symbash ON Employee.EID = symbash.EID\n" +
                "LEFT  JOIN  Salary ON Employee.EID = Salary.EID\n" +
                "WHERE Employee.EID = " + eid + ";");
        rs.next();
        employeeObject.put("firstName",rs.getString("FirstName"));
        employeeObject.put("lastName",rs.getString("LastName"));
        employeeObject.put("address",rs.getString("Address"));
        employeeObject.put("phone",rs.getString("PHONE"));
        employeeObject.put("IBAN",rs.getString("IBAN"));
        employeeObject.put("bankName",rs.getString("BANK_NAME"));
        employeeObject.put("isInactive",rs.getBoolean("isinactive"));
        employeeObject.put("department",rs.getString("department"));
        employeeObject.put("isMarried",rs.getBoolean("isMarried"));
        employeeObject.put("permanent",rs.getBoolean("permanent"));
        employeeObject.put("isAdministration",rs.getBoolean("administration"));
        employeeObject.put("startDate",rs.getDate("startDate").toString());
        if(rs.getBoolean("permanent") == false) {
            employeeObject.put("endDate",rs.getDate("endDate").toString());

        }
        employeeObject.put("SalaryContract",rs.getInt("salarySymbasy"));
        employeeObject.put("misthos",rs.getInt("misthos"));
        employeeObject.put("epidoma",rs.getInt("epidoma"));
        employeeObject.put("finalSummary",rs.getFloat("finalSummary"));
        employeeObject.put("EID",rs.getInt("EID"));

        System.out.println(employeeObject);
        return employeeObject;
    }

    @Override
    public String toString() {
        return "{" +
                "EID:" + EID +
                ", firstName:'" + firstName + '\'' +
                ", lastName:'" + lastName + '\'' +
                ", isMarried:" + isMarried +
                ", department:'" + department + '\'' +
                ", phone:'" + phone + '\'' +
                ", IBAN:'" + IBAN + '\'' +
                ", bankName:'" + bankName + '\'' +
                ", isInactive:" + isInactive +
                ", address:'" + address + '\'' +
                "}";
    }

    public static List<Employee> returnAllPermanentEmployee() throws SQLException {
        List<Employee> permenntEmployees = new ArrayList<Employee>();
        Connection con = Connector.connectDatabase("hy360");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Employee LEFT JOIN EmployClasses ON Employee.EID = EmployClasses.EID WHERE permanent = true;");
        while(rs.next()){
            Employee empl = new Employee(rs.getInt("EID"),rs.getString("FirstName"),rs.getString("LastName"),rs.getBoolean("isMarried"),rs.getString("department"),rs.getString("PHONE"),rs.getString("IBAN"),rs.getString("BANK_NAME"),rs.getBoolean("isinactive"),rs.getString("address"));
            permenntEmployees.add(empl);
        }


        return  permenntEmployees;
    }

    public static List<Employee> returnAllTemporaryEmployee() throws SQLException {
        List<Employee> temporaryEmployees = new ArrayList<Employee>();
        Connection con = Connector.connectDatabase("hy360");
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM Employee LEFT JOIN EmployClasses ON Employee.EID = EmployClasses.EID WHERE permanent = false;");
        while(result.next()){
            Employee empl = new Employee(result.getInt("EID"),result.getString("FirstName"),result.getString("LastName"),result.getBoolean("isMarried"),result.getString("department"),result.getString("PHONE"),result.getString("IBAN"),result.getString("BANK_NAME"),result.getBoolean("isinactive"),result.getString("address"));
            temporaryEmployees.add(empl);
        }


        return  temporaryEmployees;
    }
    public Employee(int EID, String firstName, String lastName, boolean isMarried, String department, String phone, String IBAN, String bankName, boolean isInactive, String address) {
        this.EID = EID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isMarried = isMarried;
        this.department = department;
        this.phone = phone;
        this.IBAN = IBAN;
        this.bankName = bankName;
        this.isInactive = isInactive;
        this.address = address;
    }

    public static void UpdateEmployeeRow(Employee updatedEmployeeData) throws SQLException {
        Connection con = Connector.connectDatabase("hy360");
        PreparedStatement stmt = con.prepareStatement("UPDATE `Employee` SET `LastName`= ?,`FirstName`= ?,`Address`= ?,`PHONE`= ?,`IBAN`= ?,`BANK_NAME`= ?,`isinactive`= ?,`department`= ?,`isMarried`= ?  WHERE EID = ?");
        stmt.setString(1, updatedEmployeeData.getLastName());
        stmt.setString(2, updatedEmployeeData.getFirstName());
        stmt.setString(3, updatedEmployeeData.getAddress());
        stmt.setString(4, updatedEmployeeData.getPhone());
        stmt.setString(5, updatedEmployeeData.getIBAN());
        stmt.setString(6, updatedEmployeeData.getBankName());
        stmt.setBoolean(7, updatedEmployeeData.isInactive());
        stmt.setString(8, updatedEmployeeData.getDepartment());
        stmt.setBoolean(9, updatedEmployeeData.isMarried());
        stmt.setInt(10, updatedEmployeeData.getEID());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
}
