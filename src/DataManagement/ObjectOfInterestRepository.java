package DataManagement;

import SystemLogic.ObjectOfInterest;

import java.util.ArrayList;

public class ObjectOfInterestRepository {

    public static ArrayList<ObjectOfInterest> getListOfObjects(){
        //TODO
        ArrayList<ObjectOfInterest> objects = new ArrayList<>();
        objects.add(new ObjectOfInterest(false, "really cool vase"));
        objects.add(new ObjectOfInterest(true, "an old yoyo"));
        objects.add(new ObjectOfInterest(true, "Toyota AE86"));
        objects.add(new ObjectOfInterest(false, "statue of a man"));
        return objects;
    }

    public static boolean addObject(ObjectOfInterest o){
        return true;
    }

    public static boolean updateObject(ObjectOfInterest o){
        return true;
    }

    public static boolean deleteObject(ObjectOfInterest o){
        return true;
    }

}
