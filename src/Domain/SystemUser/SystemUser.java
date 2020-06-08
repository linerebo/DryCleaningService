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

    // functionality for all different system users here in superclass, only specific stuff in the subclass
    // do we have specific stuff?

}
