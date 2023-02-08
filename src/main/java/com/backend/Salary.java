package com.backend;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Salary {
    private int EID;
    private int misthos;
    private Date startDate;
    private float bonus; 
    private float epidoma;
    private float finalSummary;

    public int getEID() {
        return EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
    }

    public int getMisthos() {
        return misthos;
    }

    public void setMisthos(int misthos) {
        this.misthos = misthos;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    public float getEpidoma() {
        return epidoma;
    }

    public void setEpidoma(float epidoma) {
        this.epidoma = epidoma;
    }

    public float getFinalSummary() {
        return finalSummary;
    }

    public void setFinalSummary(float finalSummary) {
        this.finalSummary = finalSummary;
    }

    public Salary(int EID, int misthos, Date startDate, float bonus, float epidoma, float finalSummary) {
        this.EID = EID;
        this.misthos = misthos;
        this.startDate = startDate;
        this.bonus = bonus;
        this.epidoma = epidoma;
        this.finalSummary = finalSummary;
    }

    public static void createSalaryTable(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE TABLE Salary (" +
                "EID int," +
                "misthos int," +
                "startDate date," +
                "epidoma float," +
                "finalSummary float);");
    }

    public static boolean CheckForSalaryTable(Connection con) throws SQLException{
        DatabaseMetaData dbMeta = con.getMetaData();
        ResultSet rs = dbMeta.getTables(null,null,"Salary",  new String[] {"TABLE"});
        return rs.next();
    }

    /**
     * Make all the Data Come in one call
     * using left join
     * */

    public  static Salary CreateSalaryByEid(int eid) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        Employee employee = Employee.fetchEmployeeFromDatabase(eid);
        int underAgeChildren = 0;
        int epidomaBibliothikis = 100;
        int epidomaEreunas = 100;
        LocalDate now = LocalDate.now();
        System.out.println(employee.toString());
        if(employee.isMarried() == true){
            List<Children> listOfChildren = Children.fetchChildrenByEID(eid);
            System.out.println(listOfChildren.toString());
            for( Children child : listOfChildren){
                if(child.getChildAge() < 18){
                    underAgeChildren++;
                }
            }
        }
        System.out.println(underAgeChildren);



        EmployClasses employClass = EmployClasses.fetchEmployeeClassByEID(eid);
        symbash employeeSimbasi = symbash.fetchSymbasiByEID(eid);
        float familyBonus = ((underAgeChildren + 1) * 5) * employeeSimbasi.getSalarySymbash();
        float bonus = 0;
        float yearlyIncome = 0;
        float finalSalary = 0;
        if(employClass.isAdministration() == false){
            if(employClass.isPermanent() == true){
                bonus = epidomaEreunas;
            }else{
                bonus = epidomaBibliothikis;
            }
        }


        Period employeePeriod = Period.between(employClass.getStartDate(), now);

        if(employeePeriod.getYears() > 1){
            if(employClass.isPermanent() == true){
                yearlyIncome = ((15 * employeePeriod.getYears())/100) * employeeSimbasi.getSalarySymbash();
            }
        }
        familyBonus = familyBonus/100;
        finalSalary = employeeSimbasi.getSalarySymbash() + familyBonus + bonus + yearlyIncome;

        Salary employeeSalary = new Salary(eid,Math.round(employeeSimbasi.getSalarySymbash()),Date.valueOf(employClass.getStartDate()),familyBonus, bonus, finalSalary);

        return employeeSalary;
    }

    public  static  void saveSalaryToDatabase(Salary newSalary) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        PreparedStatement stmt = con.prepareStatement("INSERT INTO Salary " +
                "( EID, misthos, startDate, epidoma, finalSummary) " +
                "VALUES ( ?, ?, ?, ?, ?);");
        stmt.setInt(1, newSalary.getEID());
        stmt.setInt(2, newSalary.getMisthos());
        stmt.setDate(3, newSalary.getStartDate());
        stmt.setFloat(4, newSalary.getBonus() + newSalary.getEpidoma());
        stmt.setFloat(5, newSalary.getFinalSummary());

        stmt.executeUpdate();

        stmt.close();
        con.close();

    }

    public static  Salary fetchSalaryByEid(int eid) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM SALARY WHERE EID = ?;");

        stmt.setInt(1,eid);
        ResultSet rs  = stmt.executeQuery();
        rs.next();
        Salary newSalary = new Salary(rs.getInt("EID"),rs.getInt("misthos"),rs.getDate("startDate"),rs.getFloat("epidoma"),0,rs.getFloat("finalSummary"));
        return  newSalary;
    }

    @Override
    public String toString() {
        return "{" +
                "EID:" + EID +
                ", misthos:" + misthos +
                ", startDate:" + startDate +
                ", bonus:" + bonus +
                ", epidoma:" + epidoma +
                ", finalSummary:" + finalSummary +
                '}';
    }
    public static Salary recalculateSalaryAndUpdate(int eid) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        Salary recalculatedSalary = CreateSalaryByEid(eid);
        PreparedStatement stmt = con.prepareStatement("UPDATE Salary SET `misthos`= ?,`startDate`= ?,`epidoma`= ?,`finalSummary`= ? WHERE EID = ? ");

        stmt.setInt(1,recalculatedSalary.getMisthos());
        stmt.setDate(2, Date.valueOf(recalculatedSalary.getStartDate().toString()));
        stmt.setFloat(3, recalculatedSalary.getEpidoma());
        stmt.setFloat(4,recalculatedSalary.getFinalSummary());
        stmt.setInt(5, recalculatedSalary.getEID());

        stmt.executeUpdate();
        return recalculatedSalary;
    }
}
