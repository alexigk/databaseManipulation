package com.backend;

import java.sql.*;
import java.time.LocalDate;
public class EmployClasses {
    private int EID;
    private boolean permanent;
    private boolean administration;
    private LocalDate startDate;

    public static EmployClasses fetchEmployeeClassByEID(int eid) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM EmployClasses WHERE EID = ?;");
        stmt.setInt(1, eid);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        EmployClasses returnClass = new EmployClasses(rs.getInt("eid"), rs.getBoolean("permanent"),rs.getBoolean("administration"),rs.getDate("startDate").toLocalDate());
        stmt.close();
        con.close();
        return  returnClass;
    }

    public static void updateEmployeeClassRow(EmployClasses updateClass)  throws SQLException {
        Connection con = Connector.connectDatabase("hy360");
        PreparedStatement stmt = con.prepareStatement("UPDATE `EmployClasses` SET `permanent`= ?,`administration`= ?,`startDate`= ? WHERE EID = ?");
        stmt.setBoolean(1,updateClass.isPermanent());
        stmt.setBoolean(2, updateClass.isAdministration());
        stmt.setDate(3, Date.valueOf(updateClass.getStartDate()));
        stmt.setInt(4, updateClass.getEID());

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

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public boolean isAdministration() {
        return administration;
    }

    public void setAdministration(boolean administration) {
        this.administration = administration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public EmployClasses(int EID, boolean permanent, boolean administration, LocalDate startDate) {
        this.EID = EID;
        this.permanent = permanent;
        this.administration = administration;
        this.startDate = startDate;
    }

    public static void createEmployClassesTable(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE  TABLE hy360.EmployClasses(" +
                "EID int unique ," +
                "permanent boolean," +
                "administration boolean," +
                "startDate DATE);");
    }

    public static boolean checkForEmployClassesTable(Connection con) throws SQLException{
        DatabaseMetaData dbMeta = con.getMetaData();
        ResultSet rs = dbMeta.getTables(null,null,"EmployClasses", new String[] {"TABLE"});
        return  rs.next();
    }

    public static void saveEmployClassToDatabase( EmployClasses eClass) throws SQLException{
        Connection con = Connector.connectDatabase("hy360");
        PreparedStatement stmt = con.prepareStatement("INSERT INTO EmployClasses " +
                "(EID, permanent, administration, startDate) " +
                "VALUES ( ?, ?, ?, ?)");
        stmt.setInt(1, eClass.getEID());
        stmt.setBoolean(2, eClass.isPermanent());
        stmt.setBoolean(3, eClass.isAdministration());
        stmt.setDate(4, Date.valueOf(eClass.getStartDate()));

        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    @Override
    public String toString() {
        return "EmployClasses{" +
                "EID=" + EID +
                ", permanent=" + permanent +
                ", administration=" + administration +
                ", startDate=" + startDate +
                '}';
    }
}
