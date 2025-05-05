// NAME: BEH QIAN RU & BBHAVANPREET KAUR SOHI A/P JASBIR SINGH
// TPNUMBER: TP076900 & TP076493

package apuhostelmanagementfeessystem;

public class HostelManagementFees {
    public static LoginRegister welcome;
    public static LoginPage login;
    public static RegisterPage register;
    public static User loginUser = null;
    public static ResidentLandingPage residentDashboard;
    public static StaffLandingPage staffDashboard;
    public static ManagerLandingPage managerDashboard;
    
    public static void main(String[] args) {
        DataIO.read();
        welcome = new LoginRegister();
        
       
    }
    
}
