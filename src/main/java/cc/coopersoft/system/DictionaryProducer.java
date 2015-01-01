package cc.coopersoft.system;

import cc.coopersoft.system.model.Dictionary;
import cc.coopersoft.system.repository.DictionaryRepository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.*;

import static javax.enterprise.event.Reception.IF_EXISTS;
import static javax.enterprise.event.TransactionPhase.AFTER_SUCCESS;

/**
 * Created by cooper on 6/19/16.
 */
@Named
@ApplicationScoped
public class DictionaryProducer {

    private Map<String,List<Dictionary>> cache = new HashMap<String, List<Dictionary>>();

    private Map<String,Dictionary> dictionaryMap = new HashMap<String, Dictionary>();


    @Inject
    private DictionaryRepository dictionaryRepository;

    @Transactional(qualifier = SystemEM.class)
    public List<Dictionary> getDictionaries(String categoryId){
        List<Dictionary> result = cache.get(categoryId);
        if (result == null){
            result = dictionaryRepository.getValidDictionaries(categoryId);
            cache.put(categoryId,result);
        }

        return result;
    }

    @Transactional(qualifier = SystemEM.class)
    public Dictionary getDictionary(String dictionaryId){
        Dictionary result = dictionaryMap.get(dictionaryId);
        if (result == null){
            result = dictionaryRepository.getDictionary(dictionaryId);
            if (result != null){
                dictionaryMap.put(result.getId(),result);
            }

        }
        return result;

    }

    @Transactional(qualifier = SystemEM.class)
    public String getDictionaryValue(String dictionaryId){
        Dictionary result = getDictionary(dictionaryId);
        if (result == null){
            return dictionaryId;
        }else{
            return result.getName();
        }
    }

    public void refresh(@Observes(during = AFTER_SUCCESS,notifyObserver = IF_EXISTS) @UpdateDictionary String categoryId){
        cache.remove(categoryId);
        List<Dictionary> dictionaries = new ArrayList<Dictionary>(dictionaryMap.values());
        for(Dictionary dictionary: dictionaries){
            if(dictionary.getCategory().getId().equals(categoryId)){
                dictionaryMap.remove(dictionary.getId());
            }
        }

    }

}
