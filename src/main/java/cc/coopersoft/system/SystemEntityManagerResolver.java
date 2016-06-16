package cc.coopersoft.system;

import org.apache.deltaspike.data.api.EntityManagerResolver;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by cooper on 6/17/16.
 */
public class SystemEntityManagerResolver implements EntityManagerResolver {

    @Inject
    @SystemEM
    private EntityManager em;

    public EntityManager resolveEntityManager() {
        return em;
    }
}
