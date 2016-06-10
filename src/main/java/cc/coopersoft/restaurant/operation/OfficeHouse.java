package cc.coopersoft.restaurant.operation;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.operation.model.Office;
import cc.coopersoft.restaurant.operation.repository.OfficeRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

/**
 * Created by cooper on 6/10/16.
 */
@Named
@ConversationScoped
public class OfficeHouse extends EntityHome<Office,String> {

    @Inject
    private OfficeRepository officeRepository;

    protected Office createInstance() {
        return new Office(new Date());
    }

    protected EntityRepository<Office, String> getEntityRepository() {
        return officeRepository;
    }
}
