package Domain.SystemUser;

public class SystemUser {

    public int systemUserID;
    public String systemUserFirstName;
    public String systemUserLastName;
    public int departmentID;

    public SystemUser(int systemUserID, String systemUserFirstName, String systemUserLastName, int departmentID){
        this.systemUserID = systemUserID;
        this.systemUserFirstName = systemUserFirstName;
        this.systemUserLastName = systemUserLastName;
        this.departmentID = departmentID;
    }
}
