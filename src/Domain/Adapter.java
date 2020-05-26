package Domain;

import Domain.CleaningCentral;
import Persistance.DB;

public class Adapter {
    private static CleaningCentral cleaningCentralSingleton;

    //returns the instance. If there is no instance, it makes a new one. Using the Singleton pattern.
    public static CleaningCentral cleaningCentralInstance(){
        if(cleaningCentralSingleton == null){
            cleaningCentralSingleton = new CleaningCentral();
        }
        return cleaningCentralSingleton;
    }

    private static DB DBSingleton;

    public static DB DBInstance(){
        if(DBSingleton == null) {
            DBSingleton = new DB();
        }
        return DBSingleton;
    }
}
