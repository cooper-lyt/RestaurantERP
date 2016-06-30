package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.model.Business;
import cc.coopersoft.restaurant.model.EmployeeAction;
import cc.coopersoft.restaurant.model.JobInfo;
import cc.coopersoft.restaurant.model.Operation;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.picketlink.Identity;
import org.picketlink.idm.model.basic.User;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
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
@ConversationScoped
public class EmployeeOperation implements java.io.Serializable{

    @Inject
    private EmployeeHome employeeHome;

    @Inject
    private Identity identity;

    @Inject
    @Default
    private EntityManager entityManager;

    @Inject
    private Logger logger;


    private Date validTime;

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    private JobInfo jobInfo;

    public JobInfo getJobInfo() {
        return jobInfo;
    }

    public void clearJob(){
        jobInfo.setJob(null);
    }

    @Inject @Default
    private Conversation conversation;

    public void beginConversation(){
        if ( conversation.isTransient() )
        {
            conversation.begin();
            conversation.setTimeout(600000);
        }
    }

    public void beginJoin() {
        beginConversation();
        employeeHome.clearInstance();
    }

    public void beginChangeJob(){
        beginConversation();
        jobInfo = new JobInfo(employeeHome.getInstance().getWorkCode(),employeeHome.getInstance().getOffice(),employeeHome.getInstance().getJob(),employeeHome.getInstance().getLevel());
    }



    @PreDestroy
    public void endConversation() {
        if ( !conversation.isTransient() )
        {
            conversation.end();
        }
    }

    @Transactional
    public void join(){
        String id = UUID.randomUUID().toString().replace("-","");
        logger.config("new id is:" + identity.getAccount().getId() );

        Business business = new Business(id,Business.Type.EMP_JOIN, Business.Status.COMPLETE,new Date());


        EmployeeAction employeeAction = new EmployeeAction(id,employeeHome.getInstance().getJoinDate(),employeeHome.getInstance(),business);

        employeeAction.setJobInfo(new JobInfo(employeeHome.getInstance().getJob(),employeeHome.getInstance().getLevel(),employeeHome.getInstance().getWorkCode(),employeeHome.getInstance().getOffice(),employeeAction));
        business.getEmployeeActions().add(employeeAction);

        User user = (User)identity.getAccount();

        logger.config("new id is:" + user.getLoginName());
        business.getOperations().add(new Operation(id,user.getLoginName(),user.getFirstName() + user.getLastName(),"入职操作",new Date(), Operation.Type.APPLY,business));

        entityManager.persist(business);
        entityManager.flush();
        endConversation();

    }

    @Transactional
    public void jobChange(){
        String id = UUID.randomUUID().toString().replace("-","");
        Business business = new Business(id,Business.Type.EMP_JOB_CHANGE,Business.Status.COMPLETE,new Date());
        EmployeeAction employeeAction = new EmployeeAction(id,validTime,employeeHome.getInstance(),business);
        jobInfo.setEmployeeAction(employeeAction);
        employeeHome.getInstance().setOffice(jobInfo.getOffice());
        employeeHome.getInstance().setJob(jobInfo.getJob());
        employeeHome.getInstance().setWorkCode(jobInfo.getWorkCode());
        employeeHome.getInstance().setLevel(jobInfo.getLevel());
        employeeAction.setJobInfo(jobInfo);
        business.getEmployeeActions().add(employeeAction);
        User user = (User)identity.getAccount();
        business.getOperations().add(new Operation(id,user.getLoginName(),user.getFirstName() + user.getLastName(),"入职操作",new Date(), Operation.Type.APPLY,business));
        entityManager.persist(business);
        entityManager.flush();
        endConversation();
    }

}
