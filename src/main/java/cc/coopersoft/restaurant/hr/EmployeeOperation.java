package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.BatchData;
import cc.coopersoft.restaurant.hr.repository.EmployeeRepository;
import cc.coopersoft.restaurant.model.*;
import cc.coopersoft.restaurant.operation.OfficeHome;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.picketlink.Identity;
import org.picketlink.idm.model.basic.User;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
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

    private EmployeeGiftMoney employeeGiftMoney;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EmployeeGiftMoney getEmployeeGiftMoney() {
        if (employeeGiftMoney == null){
            employeeGiftMoney = new EmployeeGiftMoney();
        }
        return employeeGiftMoney;
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
        logger.config("call begin change job:" + employeeHome.getInstance().getOffice().getName());
        beginConversation();
        validTime = new Date();
        jobInfo = new JobInfo(employeeHome.getInstance().getWorkCode(),employeeHome.getInstance().getOffice(),employeeHome.getInstance().getJob(),employeeHome.getInstance().getLevel());
    }

    @Inject
    private OfficeHome officeHome;

    @Inject
    private EmployeeRepository employeeRepository;

    @Transactional
    public String beginGift(){
        if (officeHome.isIdDefined()){
            beginConversation();
            return "/erp/hr/giftEmployee.xhtml";
        }else{

            Business business = createEmployeeBusiness(Business.Type.EMP_GIFT);
            for(Employee emp: employeeRepository.findAllNormal()){
                createGift(business,emp);
            }
            entityManager.persist(business);
            entityManager.flush();
            endConversation();
            return "/erp/hr/giftWell.xhtml";
        }
    }

    private Business createEmployeeBusiness(Business.Type type){
        String id = UUID.randomUUID().toString().replace("-","");
        Business business = new Business(id,type, Business.Status.COMPLETE,new Date());
        business.setDescription(description);
        User user = (User)identity.getAccount();
        business.getOperations().add(new Operation(id,user.getLoginName(),user.getFirstName() + user.getLastName(),"建立",new Date(), Operation.Type.APPLY,business));
        return business;
    }

    @Inject
    private EmployeeChoice employeeChoice;

    @Transactional
    public String createGift(){
        Business business = createEmployeeBusiness(Business.Type.EMP_GIFT);
        for (BatchData<Employee> emp: employeeChoice.getDataList()){
            if (emp.isSelected()){
                createGift(business,emp.getData());
            }
        }
        entityManager.persist(business);
        entityManager.flush();
        endConversation();
        return "/erp/hr/giftWell.xhtml";

    }

    private void createGift(Business business, Employee employee){
        EmployeeAction employeeAction = new EmployeeAction(UUID.randomUUID().toString().replace("-",""),validTime,employee,business);

        getEmployeeGiftMoney().setId(employeeAction.getId());
        employeeAction.setEmployeeGiftMoney(getEmployeeGiftMoney());

        business.getEmployeeActions().add(employeeAction);
    }


    @PreDestroy
    public void endConversation() {
        if ( !conversation.isTransient() )
        {
            conversation.end();
        }
    }

    @Transactional
    public String join(){

        Business business = createEmployeeBusiness(Business.Type.EMP_JOIN);
        EmployeeAction employeeAction = new EmployeeAction(business.getId(),employeeHome.getInstance().getJoinDate(),employeeHome.getInstance(),business);
        employeeAction.setJobInfo(new JobInfo(employeeHome.getInstance().getJob(),employeeHome.getInstance().getLevel(),employeeHome.getInstance().getWorkCode(),employeeHome.getInstance().getOffice(),employeeAction));
        business.getEmployeeActions().add(employeeAction);
        entityManager.persist(business);
        entityManager.flush();
        endConversation();
        return "/erp/hr/EmployeeJoinWell.xhtml";

    }

    @Transactional
    public String jobChange(){
        Business business = createEmployeeBusiness(Business.Type.EMP_JOB_CHANGE);
        EmployeeAction employeeAction = new EmployeeAction(business.getId(),validTime,employeeHome.getInstance(),business);
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

    @Transactional
    public void leave(){
        Business business = createEmployeeBusiness(Business.Type.EMP_LEAVE);
        EmployeeAction employeeAction = new EmployeeAction(business.getId(),validTime,employeeHome.getInstance(),business);
        business.getEmployeeActions().add(employeeAction);
        employeeHome.getInstance().setStatus(Employee.Status.LEAVE);
        entityManager.persist(business);
        entityManager.flush();
      //  return "/erp/hr/EmployeeLeaveWell.xhtml";
    }

}
