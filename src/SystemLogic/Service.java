package SystemLogic;
import java.util.Date;
import java.util.UUID;

public class Service {
    public enum ServiceType {
        AUCTION_ASSISTANCE, CONSULTATION
    }

    public enum ServiceStatus {
        PENDING, APPROVED, COMPLETED
    }

    private ServiceType serviceType;
    private Date startTime;
    private Date endTime;
    private String addDetails;
    private String id;
    private UUID clientId;
    private UUID expertId;

    public Service(ServiceType serviceType, Date startTime, Date endTime, String addDetails, UUID clientId, UUID expertId) {
        this.id = UUID.randomUUID().toString(); //generated id added for uniqueness and to find it easily
        this.serviceType = serviceType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.addDetails = addDetails;
        this.clientId = clientId;
        this.expertId = expertId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public void setExpertId(UUID expertId) {
        this.expertId = expertId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getExpertId() {
        return expertId;
    }

    public void displayServiceDetails() {
        System.out.println("Service Type: " + serviceType);
        System.out.println("Service Start: " + startTime);
        System.out.println("Service End: "+ endTime);
        System.out.println("Additional Details: " + addDetails);
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime(){return endTime;}

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(Date endTime){this.endTime = endTime;}
    public String getAddDetails() {
        return addDetails;
    }

    public void setAddDetails(String addDetails) {
        this.addDetails = addDetails;
    }

    //ID getter
    public String getId() {
        return id;
    }

    public void setId(String id){this.id=id;}
}
