package cc.coopersoft.restaurant.operation;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.model.Job;
import cc.coopersoft.restaurant.model.OfficeType;
import cc.coopersoft.restaurant.model.Res;
import cc.coopersoft.restaurant.operation.repository.OfficeTypeRepository;
import cc.coopersoft.restaurant.res.ResHome;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.omnifaces.cdi.Param;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by cooper on 6/24/16.
 */
@Named
@RequestScoped
public class OfficeTypeHome extends EntityHome<OfficeType,String> {

    @Inject
    private OfficeTypeRepository officeTypeRepository;

    @Inject
    private FacesContext facesContext;

    @PostConstruct
    public void initParam(){
        setId(facesContext.getExternalContext().getRequestParameterMap().get("officeTypeId"));
    }


    @Inject @Default
    private EntityManager entityManager;

    protected OfficeType createInstance() {
        return new OfficeType(UUID.randomUUID().toString().replace("-",""),new Date(),true);
    }

    protected EntityRepository<OfficeType, String> getEntityRepository() {
        return officeTypeRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }



    @Override
    @Transactional
    public void saveOrUpdate(){
        super.saveOrUpdate();
        setId(getInstance().getId());
    }


}
