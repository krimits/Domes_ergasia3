public class LargeDepositor {
    private int AFM;
    private String firstName;
    private String lastName;
    private double savings;
    private double taxedIncomes;


    public LargeDepositor(int afm, String firstName, String lastName, double savings, double taxedIncomes) {
        AFM = afm;
        this.firstName = firstName;
        this.lastName = lastName;
        this.savings = savings;
        this.taxedIncomes = taxedIncomes;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public double getTaxedIncomes() {
        return taxedIncomes;
    }

    public void setTaxedIncomes(double taxedIncomes) {
        this.taxedIncomes = taxedIncomes;
    }

    public int key(){
        return AFM;
    }

    public int getAFM() {
        return AFM;
    }

    void setAFM(int AFM){
        this.AFM = AFM;
    }

    public String toString(){
        return  "AFM: " + this.getAFM() +
                "First Name: " + this.getFirstName() +
                "Last Name: " + this.getLastName() +
                "Savings: " + this.getSavings() +
                "Taxed Incomes: " + this.getTaxedIncomes();

    }


}
