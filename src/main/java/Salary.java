import java.util.Date;

public class Salary {
    private float Summary;
    private Date paymentDate;
    private int employeeId;

    public Salary(float summary, Date paymentDate, int employeeId) {
        Summary = summary;
        this.paymentDate = paymentDate;
        this.employeeId = employeeId;
    }

    public float getSummary() {
        return Summary;
    }

    public void setSummary(float summary) {
        Summary = summary;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
