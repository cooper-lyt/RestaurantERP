package cc.coopersoft.restaurant.operation;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.model.OfficeType;
import cc.coopersoft.restaurant.operation.repository.OfficeTypeRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cooper on 6/24/16.
 */
@Named
@RequestScoped
public class OfficeTypeHome extends EntityHome<OfficeType,String> {

    @Inject
    private OfficeTypeRepository officeTypeRepository;

    @Inject @Default
    private EntityManager entityManager;

    protected OfficeType createInstance() {
        return new OfficeType(UUID.randomUUID().toString().replace("-",""),new Date(),true);
    }

    protected EntityRepository<OfficeType, String> getEntityRepository() {
        return officeTypeRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
