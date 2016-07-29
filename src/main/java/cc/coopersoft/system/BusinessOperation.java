package cc.coopersoft.system;

import cc.coopersoft.restaurant.model.Business;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by cooper on 7/25/16.
 */
@Named
@RequestScoped
public class BusinessOperation {

    @Inject
    private Logger logger;

    @Inject @Any
    private Event<BusinessOperationEvent> businessOperationEvents;

    @Inject
    @Default
    private EntityManager entityManager;

    private abstract class BusinessRemoveBinding extends AnnotationLiteral<BusinessRemove> implements BusinessRemove {}

    private String businessId;


    private boolean canDelete = true;

    private String message;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }


    public boolean isCanDelete() {
        return canDelete;
    }


    public String getMessage() {
        return message;
    }

    @Transactional
    public void prepareDelete(){

        try {
            businessOperationEvents.select(new AnnotationLiteral<BusinessRemovePrepare>(){}).fire(new BusinessOperationEvent(businessId));
            canDelete = true;
        }catch (BusinessOperationPrepareException e){
            canDelete = false;
            message = e.getMessageKey();
        }

    }

    @Transactional
    public void delete(){
        logger.info("delete biz 1:" + businessId);
        final Business business = entityManager.find(Business.class,businessId);
        logger.info("delete biz 2:" + businessId);
        businessOperationEvents.select(
                new BusinessRemoveBinding() {public Business.Type value() { return business.getType(); }}
        ).fire(new BusinessOperationEvent(businessId));
        logger.info("delete biz 3:" + businessId);

        entityManager.remove(business);
        entityManager.flush();
    }
}
