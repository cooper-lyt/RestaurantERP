package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.I18n;
import cc.coopersoft.common.util.DataHelper;
import cc.coopersoft.restaurant.BusinessHelper;
import cc.coopersoft.restaurant.hr.repository.EmployeeRepository;
import cc.coopersoft.restaurant.model.*;
import cc.coopersoft.restaurant.operation.OfficeHome;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static cc.coopersoft.restaurant.model.Business.Type.EMP_BALANCE;
import static cc.coopersoft.restaurant.model.Business.Type.EMP_JOB_CHANGE;
import static cc.coopersoft.restaurant.model.Business.Type.EMP_JOIN;

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
