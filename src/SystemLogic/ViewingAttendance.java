package SystemLogic;

import java.util.ArrayList;

public class ViewingAttendance {
    private Viewing viewing;
    private Client client;
    
    public Client getClient(){
        return this.client;
    }

    public Viewing getViewing(){
        return this.viewing;
    }

    public ArrayList<ViewingAttendance> getDetails(){
        return this.viewing.getAttendances();
    }
}
