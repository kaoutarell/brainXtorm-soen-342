package SystemLogic;
import java.util.Date;
import java.util.UUID;

public class Service {
    public enum ServiceType {
        VISIT_ASSISTANCE, AUCTION_ASSISTANCE, CONSULTATION
    }

    public enum ServiceStatus {
        PENDING, APPROVED, COMPLETED
    }

    private ServiceType serviceType;
    private Date serviceDate;
    private ServiceStatus serviceStatus;
    private String addDetails;
    private String id;

    public Service(ServiceType serviceType, Date serviceDate, ServiceStatus serviceStatus, String addDetails) {
        this.id = UUID.randomUUID().toString(); //generated id added for uniqueness and to find it easily
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.serviceStatus = serviceStatus;
        this.addDetails = addDetails;
    }

    public void displayServiceDetails() {
        System.out.println("Service Type: " + serviceType);
        System.out.println("Service Date: " + serviceDate);
        System.out.println("Service Status: " + serviceStatus);
        System.out.println("Additional Details: " + addDetails);
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

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
