package cc.coopersoft.system.util;

import cc.coopersoft.restaurant.ErpEM;
import org.picketlink.annotations.PicketLink;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 * Created by cooper on 6/5/16.
 */
public class Resources {



    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Named
    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
