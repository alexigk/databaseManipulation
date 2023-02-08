package com.backend;
import java.sql.*;
import java.util.*;

public class Children {
    private int EID;
    private int CID;
    private int childAge;

    public int getEID() {
        return EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public int getChildAge() {
        return childAge;
    }

    public void setChildAge(int childAge) {
        this.childAge = childAge;
    }

    public Children(int EID, int childAge) {
        this.EID = EID;
        this.childAge = childAge;
    }

    public static void createChildrenTable(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
         stmt.executeUpdate("CREATE TABLE hy360.Children (`EID` INT NOT NULL , `CID` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, `childrenAge` INT NOT NULL );");
    }

    public  static boolean checkForChildrenTable(Connection con) throws SQLException{
        DatabaseMetaData databaseMetaData = con.getMetaData();
        ResultSet rs = databaseMetaData.getTables(null,null,"Children", new String[] {"TABLE"});
        return  rs.next();
    }

    public int saveChildToDatabase() throws  SQLException{
        Connection con = Connector.connectDatabase("hy360");
        PreparedStatement stmt = con.prepareStatement("INSERT  INTO  Children (EID, childrenAge) VALUES ( ?, ?);");
        stmt.setInt(1, this.getEID());
        stmt.setInt(2, this.getChildAge());
        int rs = stmt.executeUpdate();
        return rs;
    }

    public static List<Children> fetchChildrenByEID(int eid) throws SQLException {
        Connection con = Connector.connectDatabase("hy360");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Children WHERE EID = " + eid + " ;");
        List<Children> listOfEmployees = new ArrayList<>();
        while (rs.next()){
            Children child  = new Children(rs.getInt("eid"),rs.getInt("childrenAge"));
            child.setCID(rs.getInt("CID"));
            listOfEmployees.add(child);

        }
        return listOfEmployees;
    }

    @Override
    public String toString() {
        return "{" +
                "EID:" + EID +
                ", CID:" + CID +
                ", childAge:" + childAge +
                '}';
    }
}
