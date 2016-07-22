package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.I18n;
import cc.coopersoft.restaurant.BusinessHelper;
import cc.coopersoft.restaurant.model.Business;
import cc.coopersoft.restaurant.model.EmployeeAction;
import cc.coopersoft.restaurant.model.JobInfo;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

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

/**
 * Created by cooper on 7/22/16.
 */
@Named
@ConversationScoped
public class EmployeeJoin implements java.io.Serializable{

    @Inject
    private EmployeeHome employeeHome;

    @Inject
    private I18n i18n;

    @Inject
    private BusinessHelper businessHelper;

    @Inject
    @Default
    private EntityManager entityManager;

    @Inject @Default
    private Conversation conversation;

    public void beginConversation(){
        if ( conversation.isTransient() )
        {
            conversation.begin();
            conversation.setTimeout(600000);
        }
    }

    private Date joinTime;

    private JobInfo jobInfo = new JobInfo(UUID.randomUUID().toString().replace("-",""));

    public JobInfo getJobInfo() {
        return jobInfo;
    }

    public void clearJob(){
        jobInfo.setJob(null);
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = i18n.getDayBeginTime(joinTime);
    }


    public void beginJoin() {
        beginConversation();
        employeeHome.clearInstance();
    }

    @Transactional
    public String join(){

        Business business = businessHelper.createEmployeeBusiness(Business.Type.EMP_JOIN);
        EmployeeAction employeeAction = new EmployeeAction(business.getId(), joinTime,employeeHome.getInstance(),business);
        employeeAction.setJobInfo(jobInfo);
        employeeHome.getInstance().setJobInfo(jobInfo);
        employeeHome.getInstance().setJoinDate(joinTime);

        business.getEmployeeActions().add(employeeAction);
        entityManager.persist(business);
        entityManager.flush();
        endConversation();
        return "/erp/hr/EmployeeJoinWell.xhtml";
    }

    @PreDestroy
    public void endConversation() {
        if ( !conversation.isTransient() )
        {
            conversation.end();
        }
    }
}
