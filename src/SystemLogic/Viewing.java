package SystemLogic;

import java.sql.Date;
import java.util.ArrayList;

public class Viewing {
    private String viewingName;
    private Date viewingDate;
    private ArrayList<ViewingAttendance> attendanceList = new ArrayList<>();

    public String getVName(){
        return this.viewingName;
    }

    public Date getVDate(){
        return this.viewingDate;
    }

    public ArrayList<ViewingAttendance> getAttendances(){
        return this.attendanceList;
    }

    public Viewing(String name, Date date){
        this.viewingDate=date;
        this.viewingName=name;
    }
}
