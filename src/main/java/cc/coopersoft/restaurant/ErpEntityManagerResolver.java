package cc.coopersoft.restaurant;

import org.apache.deltaspike.data.api.EntityManagerResolver;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by cooper on 6/10/16.
 */
public class ErpEntityManagerResolver implements EntityManagerResolver {


    @Inject
    @ErpEM
    private EntityManager em;

    public EntityManager resolveEntityManager()
    {
        return em;
    }

}
