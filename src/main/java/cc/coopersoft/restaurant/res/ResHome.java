package cc.coopersoft.restaurant.res;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.model.Res;
import cc.coopersoft.restaurant.res.repository.ResRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * Created by cooper on 7/17/16.
 */
@Named
@ConversationScoped
public class ResHome extends EntityHome<Res,String> implements java.io.Serializable {

    @Inject
    private ResRepository resRepository;

    @Inject
    @Default
    private EntityManager entityManager;

    protected Res createInstance() {
        return new Res();
    }

    protected EntityRepository<Res, String> getEntityRepository() {
        return resRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
