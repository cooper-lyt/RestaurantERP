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
import java.util.logging.Logger;

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

    @Inject
    private Logger logger;

    @Transactional
    public void join(){
        String id = UUID.randomUUID().toString().replace("-","");
        logger.config("new id is:" + identity.getAccount().getId() );

        Business business = new Business(id,Business.Type.EMP_JOIN, Business.Status.COMPLETE,new Date());


        employeeHome.getInstance().setMoneyBeginDay(employeeHome.getInstance().getJoinDate());
        EmployeeAction employeeAction = new EmployeeAction(id,employeeHome.getInstance().getJoinDate(),employeeHome.getInstance(),business);
        business.getEmployeeActions().add(employeeAction);
        User user = (User)identity.getAccount();

        logger.config("new id is:" + user.getLoginName());
        business.getOperations().add(new Operation(id,user.getLoginName(),user.getFirstName() + user.getLastName(),"入职操作",new Date(), Operation.Type.APPLY,business));

        entityManager.persist(business);
        entityManager.flush();

    }

}
