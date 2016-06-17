package cc.coopersoft.system;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.Messages;
import cc.coopersoft.system.model.Dictionary;
import cc.coopersoft.system.repository.DictionaryRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by cooper on 6/17/16.
 */
@Named
@ConversationScoped
public class DictionaryHome extends EntityHome<Dictionary,String>{

    @Inject
    @SystemEM
    private EntityManager entityManager;

    @Inject
    private FacesContext facesContext;

    @Inject
    private DictionaryCategoryHome dictionaryCategoryHome;

    @Inject
    private JsfMessage<Messages> messages;

    @Inject
    private DictionaryRepository dictionaryRepository;

    protected Dictionary createInstance() {
        return new Dictionary(true);
    }

    protected EntityRepository<Dictionary, String> getEntityRepository() {
        return dictionaryRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    private void switchPri(List<Dictionary> others){
        int curPri = getInstance().getPri();
        //List<Dictionary> ups = dictionaryRepository.getUp(dictionaryCategoryHome.getId(),curPri);
        if (!others.isEmpty()){
            Dictionary up = others.get(0);
            getInstance().setPri(up.getPri());
            up.setPri(curPri);
            dictionaryRepository.flush();
        }
    }

    @Transactional
    public void up(){
        switchPri(dictionaryRepository.getUp(dictionaryCategoryHome.getId(),getInstance().getPri()));
    }

    @Transactional
    public void down(){
        switchPri(dictionaryRepository.getDown(dictionaryCategoryHome.getId(),getInstance().getPri()));
    }

    public void saveOrUpdate(){
        if (isIdDefined()){
            save();
        }else if(dictionaryRepository.findBy(getInstance().getId()) == null){
            getInstance().setCategory(dictionaryCategoryHome.getInstance());
            Integer maxPri = dictionaryRepository.getMaxPri(dictionaryCategoryHome.getId());
            getInstance().setPri(maxPri == null ? 1 : maxPri);
            save();
        }else{
            messages.addError().primaryKeyConflict();
        }

    }

    @PostConstruct
    public void initParam(){
        setId(facesContext.getExternalContext().getRequestParameterMap().get("dictionaryId"));
    }

}
