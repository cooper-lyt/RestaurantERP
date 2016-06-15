package cc.coopersoft.restaurant.operation;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.Messages;
import cc.coopersoft.restaurant.operation.model.Office;
import cc.coopersoft.restaurant.operation.repository.OfficeRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.omnifaces.cdi.Param;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.Map;

/**
 * Created by cooper on 6/10/16.
 */
@Named
@ConversationScoped
public class OfficeHome extends EntityHome<Office,String> {

    @Inject
    private OfficeRepository officeRepository;

    @Inject
    private JsfMessage<Messages> messages;

    @Inject
    private FacesContext facesContext;

    public void saveOrUpdate(){
        if (isIdDefined()){
            save();
        }else if(officeRepository.findBy(getInstance().getId()) == null){
                save();
        }else{
            throw new ValidatorException(new FacesMessage(messages.addError().primaryKeyConflict()));
        }

    }

    @PostConstruct
    public void initParam(){
        setId(facesContext.getExternalContext().getRequestParameterMap().get("officeId"));
    }

    public void printParam(){

        for (Map.Entry<String,String> m: facesContext.getExternalContext().getRequestParameterMap().entrySet())
        logger.config("key:" + m.getKey() + ":" + m.getValue());
    }

    protected Office createInstance() {
        return new Office(new Date(), Office.OfficeStatus.OPEN);
    }

    protected EntityRepository<Office, String> getEntityRepository() {
        return officeRepository;
    }
}
