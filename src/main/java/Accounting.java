

/**
 * I dont know if Ages of children Should be an array or not
 * */
public class Accounting {
    private int basicSalaryId;
    private int yearsOfService;
    private int numberOfChildren;
    private int[] childrenAges;
    private int familyBonus;

    public Accounting(int basicSalaryId, int yearsOfService, int numberOfChildren, int[] childrenAges, int familyBonus) {
        this.basicSalaryId = basicSalaryId;
        this.yearsOfService = yearsOfService;
        this.numberOfChildren = numberOfChildren;
        this.childrenAges = childrenAges;
        this.familyBonus = familyBonus;
    }

    public int getBasicSalaryId() {
        return basicSalaryId;
    }

    public void setBasicSalaryId(int basicSalaryId) {
        this.basicSalaryId = basicSalaryId;
    }

    public int getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(int yearsOfService) {
        this.yearsOfService = yearsOfService;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public int[] getChildrenAges() {
        return childrenAges;
    }

    public void setChildrenAges(int[] childrenAges) {
        this.childrenAges = childrenAges;
    }

    public int getFamilyBonus() {
        return familyBonus;
    }

    public void setFamilyBonus(int familyBonus) {
        this.familyBonus = familyBonus;
    }
}
