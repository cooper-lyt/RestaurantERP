package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.BatchData;
import cc.coopersoft.common.I18n;
import cc.coopersoft.common.util.DataHelper;
import cc.coopersoft.restaurant.BusinessHelper;
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
    private BusinessHelper businessHelper;

    @Inject
    @Default
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    @Inject
    private I18n i18n;


    private Date validTime;

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }


    private EmployeeGiftMoney employeeGiftMoney;


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

            Business business = businessHelper.createEmployeeBusiness(Business.Type.EMP_GIFT);
            for(Employee emp: employeeRepository.findAllNormal()){
                createGift(business,emp);
            }
            entityManager.persist(business);
            entityManager.flush();
            endConversation();
            return "/erp/hr/giftWell.xhtml";
        }
    }


    @Inject
    private EmployeeChoice employeeChoice;

    @Transactional
    public String createGift(){
        Business business = businessHelper.createEmployeeBusiness(Business.Type.EMP_GIFT);
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

        //getEmployeeGiftMoney().setId(employeeAction.getId());
        getEmployeeGiftMoney().setEmployeeAction(employeeAction);

        EmployeeGiftMoney egm = new EmployeeGiftMoney(getEmployeeGiftMoney().getMoney(),getEmployeeGiftMoney().getCategory(),employeeAction);
        employeeAction.setEmployeeGiftMoney(egm);


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

        Business business = businessHelper.createEmployeeBusiness(Business.Type.EMP_JOIN);
        EmployeeAction employeeAction = new EmployeeAction(business.getId(), i18n.getDayBeginTime(employeeHome.getInstance().getJoinDate()),employeeHome.getInstance(),business);
        employeeAction.setJobInfo(new JobInfo(employeeHome.getInstance().getJob(),employeeHome.getInstance().getLevel(),employeeHome.getInstance().getWorkCode(),employeeHome.getInstance().getOffice(),employeeAction));
        employeeHome.getInstance().setJoinDate(employeeAction.getValidTime());
        business.getEmployeeActions().add(employeeAction);
        entityManager.persist(business);
        entityManager.flush();
        endConversation();
        return "/erp/hr/EmployeeJoinWell.xhtml";
    }



}
