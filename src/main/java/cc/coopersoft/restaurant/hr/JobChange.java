package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.BusinessHelper;
import cc.coopersoft.restaurant.Messages;
import cc.coopersoft.restaurant.model.*;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Inject
    private PaidCalc paidCalc;

    //private Date changeTime;

    //private JobInfo jobInfo;
    private EmployeeAction employeeAction;

    private Date lastBalanceTime;

    @Inject
    @Default
    private EntityManager entityManager;

    @Inject
    private JsfMessage<Messages> messages;

    public Date getChangeTime() {
        return employeeAction.getValidTime();
    }

    public void setChangeTime(Date changeTime) {
        this.employeeAction.setValidTime(changeTime);
    }

    public JobInfo getJobInfo() {
        return employeeAction.getNewJob();
    }

    public void clearJob(){
        employeeAction.getNewJob().setJob(null);
    }

    public EmployeeAction getEmployeeAction() {
        return employeeAction;
    }

    public Date getLastBalanceTime() {
        return lastBalanceTime;
    }

    @Transactional
    public void beginChangeJob(){
        //logger.config("call begin change job:" + employeeHome.getInstance().getOffice().getName());
        if ( conversation.isTransient() )
        {
            conversation.begin();
            conversation.setTimeout(1200000);
        }
        lastBalanceTime = paidCalc.getLastBalanceTime(employeeHome.getInstance().getId());
        employeeAction = new EmployeeAction(UUID.randomUUID().toString().replace("-",""),new Date(),employeeHome.getInstance());
        employeeAction.setNewJob(new JobInfo(UUID.randomUUID().toString().replace("-",""),employeeHome.getInstance().getJobInfo()));
        employeeAction.setPaidBalance(new PaidBalance(employeeAction));

    }

    @Transactional
    public String beginWorkContent(){

        if (getChangeTime().before(lastBalanceTime)){
            messages.addError().employeeOperTimeBeforOfBalance();
            return null;
        }

        if(employeeHome.getInstance().getJobInfo().getWorkCode() == null || employeeHome.getInstance().getJobInfo().getWorkCode().trim().equals("")){
            return jobChange();
        }

        return "/erp/hr/jobChangeBalance.xhtml";
    }


    public List<WorkContentMoney> getWorkContentMoneys(){
        if (paidCalc.getWorkContentData() == null){
            return new ArrayList<WorkContentMoney>(0);
        }
        return paidCalc.getWorkContentData().get(employeeHome.getInstance().getJobInfo().getWorkCode());
    }

    @Transactional
    public String jobChange(){
        paidCalc.calcBalance(employeeHome.getInstance(),employeeAction.getPaidBalance());

        Business business = businessHelper.createEmployeeBusiness(Business.Type.EMP_JOB_CHANGE);

        employeeAction.setBusiness(business);
        employeeHome.getInstance().setJobInfo(employeeAction.getNewJob());
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
