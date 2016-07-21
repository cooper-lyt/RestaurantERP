package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.I18n;
import cc.coopersoft.restaurant.BusinessHelper;
import cc.coopersoft.restaurant.Messages;
import cc.coopersoft.restaurant.model.*;
import cc.coopersoft.system.DictionaryProducer;
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
 * Created by cooper on 7/20/16.
 */
@Named
@ConversationScoped
public class EmployeeLeave implements java.io.Serializable{

    @Inject
    @Default
    private Conversation conversation;

    @Inject
    private EmployeeHome employeeHome;

    @Inject
    private BusinessHelper businessHelper;

    @Inject
    @Default
    private EntityManager entityManager;

    @Inject
    private PaidCalc paidCalc;

    @Inject
    private I18n i18n;

    @Inject
    private JsfMessage<Messages> messages;

    @Inject
    private PaidBusinessHome paidBusinessHome;

    private Date lastBalanceTime;

    private EmployeeAction employeeAction;

    private boolean fullWork;

    public EmployeeAction getEmployeeAction() {
        return employeeAction;
    }

    public Date getLeaveDate(){
        return employeeAction.getValidTime();
    }

    public void setLeaveDate(Date date){
        employeeAction.setValidTime(i18n.getDayEndTime(date));
    }

    public boolean isFullWork() {
        return fullWork;
    }

    public void setFullWork(boolean fullWork) {
        this.fullWork = fullWork;
    }

    public Date getLastBalanceTime() {
        return lastBalanceTime;
    }

    public List<WorkContentMoney> getWorkContentMoneys(){
        if (paidCalc.getWorkContentData() == null){
            return new ArrayList<WorkContentMoney>(0);
        }
        return paidCalc.getWorkContentData().get(employeeHome.getInstance().getWorkCode());
    }

    @Transactional
    public void begin(){
        //logger.config("call begin change job:" + employeeHome.getInstance().getOffice().getName());
        if ( conversation.isTransient() )
        {
            conversation.begin();
            conversation.setTimeout(1200000);
        }
        lastBalanceTime = paidCalc.getLastBalanceTime(employeeHome.getInstance().getId());
        employeeAction = new EmployeeAction(UUID.randomUUID().toString().replace("-",""),new Date(),employeeHome.getInstance());
        //employeeAction.setJobInfo(new JobInfo(employeeHome.getInstance().getJob(),employeeHome.getInstance().getLevel(),employeeHome.getInstance().getWorkCode(),employeeHome.getInstance().getOffice(),employeeAction));
        employeeAction.setPaidBalance(new PaidBalance(employeeAction));
    }

    @Transactional
    public String beginWorkContent(){

        if (getLeaveDate().before(lastBalanceTime)){
            messages.addError().employeeOperTimeBeforOfBalance();
            return null;
        }

        if(employeeHome.getInstance().getWorkCode() == null || employeeHome.getInstance().getWorkCode().trim().equals("")){
            return leave();
        }

        return "/erp/hr/LeaveWorkContent.xhtml";
    }




    @Transactional
    public String leave(){

        paidCalc.balanceAndPaid(employeeHome.getInstance(),employeeAction.getPaidBalance(),fullWork);
        Business business = businessHelper.createEmployeeBusiness(Business.Type.EMP_LEAVE);
        employeeAction.setBusiness(business);
        business.getEmployeeActions().add(employeeAction);
        employeeHome.getInstance().setStatus(Employee.Status.LEAVE);

        paidBusinessHome.setInstance(business);
        paidBusinessHome.saveOrUpdate();
//        entityManager.persist(business);
//        entityManager.flush();
//        paidBusinessHome.setId(business.getId());
        endConversation();
        return "/erp/hr/LeavePaid.xhtml";
    }

    @PreDestroy
    public void endConversation() {
        if ( !conversation.isTransient() )
        {
            conversation.end();
        }
    }


}
