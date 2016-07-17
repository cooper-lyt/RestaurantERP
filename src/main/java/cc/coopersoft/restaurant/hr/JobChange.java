package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.BusinessHelper;
import cc.coopersoft.restaurant.model.Business;
import cc.coopersoft.restaurant.model.EmployeeAction;
import cc.coopersoft.restaurant.model.JobInfo;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Date;

/**
 * Created by cooper on 7/17/16.
 */
@Named
@ConversationScoped
public class JobChange implements java.io.Serializable{

    @Inject
    @Default
    private Conversation conversation;

    @Inject
    private EmployeeHome employeeHome;

    @Inject
    private BusinessHelper businessHelper;

    private Date changeTime;

    private JobInfo jobInfo;

    @Inject
    @Default
    private EntityManager entityManager;

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public JobInfo getJobInfo() {
        return jobInfo;
    }

    public void clearJob(){
        jobInfo.setJob(null);
    }

    @Transactional
    public void beginChangeJob(){
        //logger.config("call begin change job:" + employeeHome.getInstance().getOffice().getName());
        if ( conversation.isTransient() )
        {
            conversation.begin();
            conversation.setTimeout(1200000);
        }
        changeTime = new Date();
        jobInfo = new JobInfo(employeeHome.getInstance().getWorkCode(),employeeHome.getInstance().getOffice(),employeeHome.getInstance().getJob(),employeeHome.getInstance().getLevel());
    }


    @Transactional
    public String jobChange(){
        Business business = businessHelper.createEmployeeBusiness(Business.Type.EMP_JOB_CHANGE);
        EmployeeAction employeeAction = new EmployeeAction(business.getId(),changeTime,employeeHome.getInstance(),business);
        jobInfo.setEmployeeAction(employeeAction);
        employeeHome.getInstance().setOffice(jobInfo.getOffice());
        employeeHome.getInstance().setJob(jobInfo.getJob());
        employeeHome.getInstance().setWorkCode(jobInfo.getWorkCode());
        employeeHome.getInstance().setLevel(jobInfo.getLevel());
        employeeAction.setJobInfo(jobInfo);
        business.getEmployeeActions().add(employeeAction);
        entityManager.persist(business);
        entityManager.flush();
        endConversation();

        return "/erp/hr/JobChangeWell.xhtml";
    }

    @PreDestroy
    public void endConversation() {
        if ( !conversation.isTransient() )
        {
            conversation.end();
        }
    }
}
