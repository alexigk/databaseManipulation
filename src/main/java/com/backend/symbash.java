package com.backend;

import java.sql.*;
import java.time.LocalDate;

public class symbash {
    private int EID;
    private LocalDate endDate;
    private float salarySymbash;

    public static void updateSymbasiByEid(int eid) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        EmployClasses updateClass = EmployClasses.fetchEmployeeClassByEID(eid);
        symbash newSymbasi = symbash.fetchSymbasiByEID(eid);
        PreparedStatement stmt = con.prepareStatement("UPDATE symbash SET endDate = ? , salarySymbasy =? WHERE EID = ?");
        if(updateClass.isPermanent() == true) {
            stmt.setDate(1, null );
            stmt.setInt(2,750);
        }else {
            stmt.setDate(1, Date.valueOf(newSymbasi.getEndDate()) );
            stmt.setInt(2,650);

        }
        stmt.setInt(3,eid);

        stmt.executeUpdate();
        stmt.close();
        con.close();

    }

    public int getEID() {
        return EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public float getSalarySymbash() {
        return salarySymbash;
    }

    public void setSalarySymbash(float salarySymbash) {
        this.salarySymbash = salarySymbash;
    }

    public symbash(int EID, LocalDate endDate, float salarySymbash) {
        if(endDate == null){
            this.endDate = null;
        }else{
            this.endDate = endDate;
        }
        this.EID = EID;
        this.salarySymbash = salarySymbash;
    }

    public static void createSymbasyTable(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE TABLE hy360.symbash(" +
                "EID int unique ," +
                "endDate date," +
                "salarySymbasy float);");
    }

    public static boolean checkForSymbasyTable(Connection con) throws SQLException{
        DatabaseMetaData dbMeta = con.getMetaData();
        ResultSet rs = dbMeta.getTables(null, null, "symbash", new String[] {"TABLE"});
        return  rs.next();
    }

    public void saveSimbasiToDatabase(symbash symbash) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        PreparedStatement stmt = con.prepareStatement("INSERT INTO symbash " +
                "(EID, endDate, salarySymbasy)  " +
                "VALUES (?, ?, ?)");
        stmt.setInt(1, symbash.getEID());
        if(symbash.getEndDate() != null){
            stmt.setDate(2, Date.valueOf(symbash.getEndDate()));
        }else{
            stmt.setDate(2, null);
        }

        stmt.setFloat(3, symbash.getSalarySymbash());
        stmt.executeUpdate();

        stmt.close();
        con.close();
    }
    public static  symbash fetchSymbasiByEID(int eid) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        PreparedStatement stmt = con.prepareStatement("SELECT  * FROM symbash WHERE EID = ?");
        stmt.setInt(1, eid);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        LocalDate endDate = null;
        if(rs.getDate("endDate") != null){
                endDate = rs.getDate("endDate").toLocalDate();
        }

        symbash eSymbash = new symbash(rs.getInt("EID"), endDate, rs.getFloat("salarySymbasy"));
        return  eSymbash;
    }

    @Override
    public String toString() {
        return "symbash{" +
                "EID=" + EID +
                ", endDate=" + endDate +
                ", salarySymbash=" + salarySymbash +
                '}';
    }
}
