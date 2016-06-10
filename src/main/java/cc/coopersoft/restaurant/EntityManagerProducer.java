package cc.coopersoft.restaurant;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by cooper on 6/5/16.
 */
@ApplicationScoped
public class EntityManagerProducer {

    @Inject
    private Logger logger;

    @PersistenceUnit(unitName = "restaurantEntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Produces
    @ErpEM
    public EntityManager create()
    {
        logger.log(Level.CONFIG,"create Erp EntityManager");
        return this.entityManagerFactory.createEntityManager();
    }

    public void dispose(@Disposes @ErpEM EntityManager entityManager)
    {
        logger.log(Level.CONFIG,"Disposes Erp EntityManager");
        if (entityManager.isOpen())
        {
            entityManager.close();
        }
    }

}
