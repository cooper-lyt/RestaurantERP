package cc.coopersoft.restaurant;

import org.picketlink.annotations.PicketLink;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Created by cooper on 6/5/16.
 */
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit(unitName = "systemEntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Produces
    @ErpEM
    @PicketLink
    public EntityManager create()
    {
        return this.entityManagerFactory.createEntityManager();
    }

    public void dispose(@Disposes @ErpEM EntityManager entityManager)
    {
        if (entityManager.isOpen())
        {
            entityManager.close();
        }
    }

}
