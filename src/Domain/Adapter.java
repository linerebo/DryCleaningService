package Domain;

import Domain.CleaningCentral;

public class Adapter {
    private static CleaningCentral cleaningCentralSingleton;

    //returns the instance. If there is no instance, it makes a new one. Using the Singleton pattern.
    public static CleaningCentral cleaningCentralInstance(){
        if(cleaningCentralSingleton == null){
            cleaningCentralSingleton = new CleaningCentral();
        }
        return cleaningCentralSingleton;
    }
}
