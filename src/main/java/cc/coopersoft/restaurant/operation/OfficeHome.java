package cc.coopersoft.restaurant.operation;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.operation.model.Office;
import cc.coopersoft.restaurant.operation.repository.OfficeRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.omnifaces.cdi.Param;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
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
    private FacesContext facesContext;

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
