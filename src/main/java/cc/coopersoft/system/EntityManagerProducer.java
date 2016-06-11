package cc.coopersoft.system;

import org.picketlink.annotations.PicketLink;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by cooper on 6/10/16.
 */
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit(unitName = "systemEntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Inject
    private Logger logger;

    @Produces
    @PicketLink
    public EntityManager createPicketLinkEM()
    {
        logger.log(Level.CONFIG,"create picketLink EntityManager");
        return this.entityManagerFactory.createEntityManager();
    }

    public void disposePicketLinkEM(@Disposes @PicketLink EntityManager entityManager)
    {
        logger.log(Level.CONFIG,"disposes picketLink EntityManager: open" + entityManager.isOpen());
        if (entityManager.isOpen())
        {
            entityManager.close();
        }
    }

    @Produces
    @SystemEM
    @ConversationScoped
    public EntityManager createSystemEM()
    {

        logger.log(Level.CONFIG,"create system EntityManager");
        return this.entityManagerFactory.createEntityManager();
    }

    public void disposeSystemEM(@Disposes @SystemEM EntityManager entityManager)
    {
        logger.log(Level.CONFIG,"disposes system EntityManager");
        if (entityManager.isOpen())
        {
            entityManager.close();
        }
    }


}
