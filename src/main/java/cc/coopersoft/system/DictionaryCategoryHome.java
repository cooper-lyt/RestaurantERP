package cc.coopersoft.system;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.Messages;
import cc.coopersoft.system.model.DictionaryCategory;
import cc.coopersoft.system.repository.DictionaryCategoryRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cooper on 6/17/16.
 */
@Named
@ConversationScoped
public class DictionaryCategoryHome extends EntityHome<DictionaryCategory,String> {

    @Inject
    @SystemEM
    private EntityManager entityManager;

    @Inject
    private FacesContext facesContext;

    @Inject
    private JsfMessage<Messages> messages;

    @Inject
    private DictionaryCategoryRepository dictionaryCategoryRepository;

    public void saveOrUpdate(){
        if (isIdDefined()){
            save();
        }else if(dictionaryCategoryRepository.findBy(getInstance().getId()) == null){
            save();
        }else{
            messages.addError().primaryKeyConflict();
        }

    }

    protected DictionaryCategory createInstance() {
        return new DictionaryCategory(UUID.randomUUID().toString().replace("-",""),true,false,new Date());
    }

    protected EntityRepository<DictionaryCategory, String> getEntityRepository() {
        return dictionaryCategoryRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @PostConstruct
    public void initParam(){
        setId(facesContext.getExternalContext().getRequestParameterMap().get("dictionaryCategoryId"));
    }

}
