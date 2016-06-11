package cc.coopersoft.restaurant.operation;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.ErpEM;
import cc.coopersoft.restaurant.operation.model.Office;
import cc.coopersoft.restaurant.operation.repository.OfficeRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Date;

/**
 * Created by cooper on 6/10/16.
 */
@Named
@ConversationScoped
public class OfficeHome extends EntityHome<Office,String> {

    @Inject
    private OfficeRepository officeRepository;

    @Inject @Param
    public String getOfficeId(){
        return getId();
    }

    public void setOfficeId(String id){
        setId(id);
    }

    protected Office createInstance() {
        return new Office(new Date());
    }

    protected EntityRepository<Office, String> getEntityRepository() {
        return officeRepository;
    }
}
