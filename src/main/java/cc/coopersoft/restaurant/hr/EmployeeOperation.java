package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.model.Business;
import cc.coopersoft.restaurant.model.EmployeeAction;
import cc.coopersoft.restaurant.model.Operation;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.picketlink.Identity;
import org.picketlink.idm.model.basic.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cooper on 6/19/16.
 */
@Named
@RequestScoped
public class EmployeeOperation {

    @Inject
    private EmployeeHome employeeHome;

    @Inject
    private Identity identity;

    @Inject
    private EntityManager entityManager;

    @Transactional
    public void join(){
        String id = UUID.randomUUID().toString().replace("-","");

        Business business = new Business(id,Business.Type.EMP_JOIN, Business.Status.COMPLETE,new Date());

        EmployeeAction employeeAction = new EmployeeAction(id,employeeHome.getInstance().getJoinDate(),employeeHome.getInstance(),business);
        business.getEmployeeActions().add(employeeAction);
        String userName = ((User)identity.getAccount()).getFirstName() + ((User)identity.getAccount()).getLastName();
        business.getOperations().add(new Operation(id,identity.getAccount().getId(),userName,"入职操作",new Date(), Operation.Type.APPLY,business));

        entityManager.persist(business);
        entityManager.flush();

    }

}
