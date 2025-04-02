package SystemLogic;

import java.sql.Date;

public class Auction {
    private String name;
    private Date startTime;
    private Date endTime;
    private String specialty;
    private String type;
    private AuctionHouse house;
    private Viewing viewing;

    public String getName(){
        return this.name;
    }

    public Date getStartTime(){
        return this.startTime;
    }

    public Date getEndTime(){
        return this.endTime;
    }

    public String getSpecialty(){
        return this.specialty;
    }

    public String getType(){
        return this.type;
    }

    public AuctionHouse getHouse(){
        return this.house;
    }

    public Viewing getViewing(){
        if (this.type=="online"){
            return null;
        }else{
            return this.viewing;
        }
    }

    public Auction(String name, Date startTime, Date endTime, String specialty, String type, AuctionHouse house){
        this.name=name;
        this.startTime=startTime;
        this.endTime=endTime;
        this.specialty=specialty;
        this.type=type;
        this.house=house;

        if (this.type!="online"){
            viewing = new Viewing(name+" viewing", startTime);
        }
    }

    @Override
    public String toString(){
        return ("Auction Info\n" +
                "Name: "+this.name+"\n" +
                "Starting Time: "+this.startTime+"\n" +
                "End Time: "+this.endTime+"\n" +
                "Specialty: "+this.specialty+"\n" +
                "Type: "+this.type+"\n");
    }

}
