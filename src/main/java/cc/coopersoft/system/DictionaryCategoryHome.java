package cc.coopersoft.system;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.system.model.DictionaryCategaory;
import cc.coopersoft.system.repository.DictionaryCategoryRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Date;

/**
 * Created by cooper on 6/17/16.
 */
@Named
@ConversationScoped
public class DictionaryCategoryHome extends EntityHome<DictionaryCategaory,String> {

    @Inject
    @SystemEM
    private EntityManager entityManager;

    @Inject
    private DictionaryCategoryRepository dictionaryCategoryRepository;

    protected DictionaryCategaory createInstance() {
        return new DictionaryCategaory(true,false,new Date());
    }

    protected EntityRepository<DictionaryCategaory, String> getEntityRepository() {
        return dictionaryCategoryRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
