package cc.coopersoft.system;

import cc.coopersoft.system.model.Dictionary;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.enterprise.event.Reception.IF_EXISTS;
import static javax.enterprise.event.TransactionPhase.AFTER_SUCCESS;

/**
 * Created by cooper on 6/19/16.
 */
@Named
@ApplicationScoped
public class DictionaryProducer {

    private Map<String,List<Dictionary>> cache = new HashMap<String, List<Dictionary>>();


    @Inject @SystemEM
    EntityManager entityManager;

    @Transactional
    private List<Dictionary> queryDictionaries(String categoryId){

        List<Dictionary> result = entityManager.createQuery("select dic from Dictionary dic where dic.category.id =:categoryId and dic.enable = true order by dic.pri")
                .setParameter("categoryId",categoryId).getResultList();

        return result;
    }

    public List<Dictionary> getDictionaries(String categoryId){
        List<Dictionary> result = cache.get(categoryId);
        if (result == null){
            result = queryDictionaries(categoryId);
            cache.put(categoryId,result);
        }

        return result;
    }

    public void refresh(@Observes(during = AFTER_SUCCESS,notifyObserver = IF_EXISTS) @UpdateDictionary String categoryId){
        cache.remove(categoryId);
    }

}
