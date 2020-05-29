package Domain.SystemUser;

import Domain.SystemUser.SystemUser;

public class Manager extends SystemUser {

    //constructor
    public Manager(int systemUserID, String systemUserFirstName, String systemUserLastName, int departmentID) {
        super(systemUserID, systemUserFirstName, systemUserLastName, departmentID);
    }
}
