package cc.coopersoft.system;

import cc.coopersoft.restaurant.model.Business;

/**
 * Created by cooper on 7/25/16.
 */
public class BusinessOperationEvent {

    private Business business;

    public BusinessOperationEvent() {
    }

    public BusinessOperationEvent(Business business) {
        this.business = business;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}
