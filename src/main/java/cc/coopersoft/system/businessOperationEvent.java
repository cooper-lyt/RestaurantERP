package cc.coopersoft.system;

/**
 * Created by cooper on 7/25/16.
 */
public class BusinessOperationEvent {

    private String businessId;

    public BusinessOperationEvent() {
    }

    public BusinessOperationEvent(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
