package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.I18n;
import cc.coopersoft.restaurant.BusinessHelper;
import cc.coopersoft.restaurant.hr.repository.EmployeeRepository;
import cc.coopersoft.restaurant.model.*;
import cc.coopersoft.restaurant.operation.OfficeHome;
import cc.coopersoft.system.DictionaryProducer;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cooper on 7/6/16.
 */
@Named
@ConversationScoped
public class EmployeePaidCalc implements java.io.Serializable {

    private Date calcDate;

    @Inject
    @Default
    private Conversation conversation;

    @Inject
    private PaidProjectHome paidProjectHome;

    @Inject
    private BusinessHelper businessHelper;

    @Inject
    private OfficeHome officeHome;

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private PaidCalc paidCalc;

    @Inject
    private I18n i18n;

    @Inject
    private PaidBusinessHome paidBusinessHome;

    @Inject
    @Default
    private EntityManager entityManager;

    private Business business;


    private List<EmployeeWorkTimeInfo> workTimeInfos;

    public List<EmployeeWorkTimeInfo> getWorkTimeInfos() {
        return workTimeInfos;
    }

    public String beginCalc() {
        business = businessHelper.createEmployeeBusiness(Business.Type.EMP_BALANCE);

        List<Employee> emps = employeeRepository.findByOfficeValid(officeHome.getInstance().getId());
        workTimeInfos = new ArrayList<EmployeeWorkTimeInfo>(emps.size());

        for(Employee emp: emps ){
            EmployeeAction ea = new EmployeeAction(UUID.randomUUID().toString().replace("-",""),calcDate,emp,business);
            ea.setPaidBalance(new PaidBalance(ea));
            ea.setJobInfo(new JobInfo(employeeRepository.findJobInfoWithTime(emp.getId(),calcDate),ea));
            business.getEmployeeActions().add(ea);

            workTimeInfos.add(new EmployeeWorkTimeInfo(ea.getPaidBalance(),employeeRepository.findLastPaidTime(emp.getId()),employeeRepository.findLastBalance(emp.getId()).getValidTime(),paidProjectHome.getInstance().getFullWorkMoney()));
        }

        if (conversation.isTransient()) {
            conversation.begin();
            conversation.setTimeout(800000);
        }

        return "/erp/hr/PaidCalcWorkTime.xhtml";
    }

    public String beginWorkContent(){

        return "/erp/hr/PaidCalcWorkContext.xhtml";
    }

    @Transactional
    public String paidBalance(){
        List<PaidBalance> paidBalanceList = new ArrayList<PaidBalance>();

        for(EmployeeAction ea: business.getEmployeeActions()){

            paidCalc.balanceAndPaid(ea.getEmployee(),ea.getPaidBalance());
            paidBalanceList.addAll(ea.getPaidBalance().getEmployeePaid().getPaidBalances());

        }

        paidBusinessHome.setInstance(business);
        paidBusinessHome.saveOrUpdate();

//        entityManager.persist(business);
//        entityManager.flush();
//        paidBusinessHome.setId(business.getId());
        endConversation();
        return "/erp/hr/PaidCalcWell.xhtml";
    }

    @PreDestroy
    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    public Date getCalcDate() {
        return calcDate;
    }

    public void setCalcDate(Date calcDate) {
        this.calcDate = i18n.getDayEndTime(calcDate);
    }


    public class EmployeeWorkTimeInfo{

        private PaidBalance paidBalance;

        private Date paidTime;

        private Date balanceTime;

        private BigDecimal fullWorkMoney;

        public EmployeeWorkTimeInfo(PaidBalance paidBalance, Date paidTime, Date balanceTime, BigDecimal fullWorkMoney) {
            this.paidBalance = paidBalance;
            this.paidTime = paidTime;
            this.balanceTime = balanceTime;
            this.fullWorkMoney = fullWorkMoney;
        }

        public PaidBalance getPaidBalance() {
            return paidBalance;
        }

        public Date getPaidTime() {
            return paidTime;
        }

        public Date getBalanceTime() {
            return balanceTime;
        }

        public boolean isFullWork(){
            return ! BigDecimal.ZERO.equals(paidBalance.getWorkFullMoney());
        }

        public void setFullWork(boolean fullWork){
            if (fullWork){
                paidBalance.setWorkFullMoney(fullWorkMoney);
            }else{
                paidBalance.setWorkFullMoney(BigDecimal.ZERO);
            }
        }
    }
}
