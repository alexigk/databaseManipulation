import java.util.Date;

/**
 * WhereEver There is an ID is a connected table
 */


public class Employee {
    private  int id;
    private String firstName;
    private String telephone;
    private String Address;
    private int departmentID;
    private Date recruitmentDate;
    private int Stability;
    private String IBAN;
    private String Bank;

    public Employee(String firstName, String telephone, String address, int departmentID, Date recruitmentDate, int stability, String IBAN, String bank) {
        this.firstName = firstName;
        this.telephone = telephone;
        this.Address = address;
        this.departmentID = departmentID;
        this.recruitmentDate = recruitmentDate;
        this.Stability = stability;
        this.IBAN = IBAN;
        this.Bank = bank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public Date getRecruitmentDate() {
        return recruitmentDate;
    }

    public void setRecruitmentDate(Date recruitmentDate) {
        this.recruitmentDate = recruitmentDate;
    }

    public int getStability() {
        return Stability;
    }

    public void setStability(int stability) {
        Stability = stability;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }
}
