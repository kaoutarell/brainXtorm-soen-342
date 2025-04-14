package SystemLogic;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Availability {
    private Date start;
    private Date end;

    public Availability(Date start, Date end){
        this.start=start;
        this.end=end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getEnd() {
        return end;
    }
    public static Availability fromString(String str) {
        String[] parts = str.split("/");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return new Availability(formatter.parse(parts[0]), formatter.parse(parts[1]));
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }

    @Override
    public String toString(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (formatter.format(start)+"/"+formatter.format(end));
    }

}
