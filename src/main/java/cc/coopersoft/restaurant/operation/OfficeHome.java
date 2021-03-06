package cc.coopersoft.restaurant.operation;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.Messages;
import cc.coopersoft.restaurant.model.Office;
import cc.coopersoft.restaurant.operation.repository.OfficeRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
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
    @Default
    private EntityManager entityManager;

    @Inject
    private FacesContext facesContext;
    

    @PostConstruct
    public void initParam(){
        setId(facesContext.getExternalContext().getRequestParameterMap().get("officeId"));
    }


    protected Office createInstance() {
        return new Office(new Date(), Office.OfficeStatus.OPEN);
    }

    protected EntityRepository<Office, String> getEntityRepository() {
        return officeRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
