package cc.coopersoft.restaurant;

import cc.coopersoft.common.EntityConverter;

import javax.enterprise.inject.Default;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by cooper on 6/20/16.
 */
@FacesConverter("erpEntityConvert")
public class ErpEntityConverter extends EntityConverter {

    @Inject
    @Default
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
