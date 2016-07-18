package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.BusinessHelper;
import cc.coopersoft.restaurant.Messages;
import cc.coopersoft.restaurant.model.Business;
import cc.coopersoft.restaurant.model.EmployeeAction;
import cc.coopersoft.restaurant.model.JobInfo;
import cc.coopersoft.restaurant.model.PaidBalance;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Date;
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
        return employeeAction.getJobInfo();
    }

    public void clearJob(){
        employeeAction.getJobInfo().setJob(null);
    }

    public EmployeeAction getEmployeeAction() {
        return employeeAction;
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
        employeeAction = new EmployeeAction(UUID.randomUUID().toString().replace("-",""),new Date(),employeeHome.getInstance())
        employeeAction.setJobInfo(new JobInfo(employeeHome.getInstance().getJob(),employeeHome.getInstance().getLevel(),employeeHome.getInstance().getWorkCode(),employeeHome.getInstance().getOffice(),employeeAction));
        employeeAction.setPaidBalance(new PaidBalance(employeeAction));

    }

    public String beginWorkContent(){

        if (getChangeTime().before(lastBalanceTime)){
            messages.addError().employeeOperTimeBeforOfBalance();
            return null;
        }

        return "/erp/hr/jobChangeBalance.xhtml";
    }



    @Transactional
    public String jobChange(){
        paidCalc.calcBlance(employeeHome.getInstance(),employeeAction.getPaidBalance());

        Business business = businessHelper.createEmployeeBusiness(Business.Type.EMP_JOB_CHANGE);

        employeeHome.getInstance().setOffice(getJobInfo().getOffice());
        employeeHome.getInstance().setJob(getJobInfo().getJob());
        employeeHome.getInstance().setWorkCode(getJobInfo().getWorkCode());
        employeeHome.getInstance().setLevel(getJobInfo().getLevel());
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