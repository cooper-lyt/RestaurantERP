package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.hr.repository.EmployeeBusinessRepository;
import cc.coopersoft.restaurant.model.*;
import cc.coopersoft.system.BusinessOperationEvent;
import cc.coopersoft.system.BusinessOperationPrepareException;
import cc.coopersoft.system.BusinessRemove;
import cc.coopersoft.system.BusinessRemovePrepare;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.EnumSet;

/**
 * Created by cooper on 7/25/16.
 */
public class EmployeeBusinessOperation {

    @Inject
    private EmployeeBusinessRepository employeeBusinessRepository;

    @Inject
    @Default
    private EntityManager entityManager;

    private void detachGiftMoney(Business business){
        for(EmployeeAction ea: business.getEmployeeActions()){
            if (ea.getPaidBalance() != null){
                for(EmployeeGiftBalance egb: ea.getPaidBalance().getEmployeeGiftBalances()){
                    for(EmployeeGiftMoney egm: egb.getEmployeeGiftMoneys()){
                        egm.setEmployeeGiftBalance(null);
                    }
                    egb.getEmployeeGiftMoneys().clear();
                }

            }

        }
    }

    public void employeeBusinessRemovePrepare(@Observes @BusinessRemovePrepare BusinessOperationEvent event) {
        Business business = employeeBusinessRepository.findBy(event.getBusinessId());
        if (EnumSet.of(Business.Type.EMP_JOIN,Business.Type.EMP_JOB_CHANGE,Business.Type.EMP_LEAVE,Business.Type.EMP_GIFT,Business.Type.EMP_BALANCE).contains(business.getType())){
            for(EmployeeAction ea: business.getEmployeeActions()){
                System.out.println("search count:" + employeeBusinessRepository.findAfterCount(ea.getValidTime(),ea.getEmployee().getId()));
                if( Long.valueOf(0).compareTo(employeeBusinessRepository.findAfterCount(ea.getValidTime(),ea.getEmployee().getId())) < 0){
                    throw new BusinessOperationPrepareException("employee_after_operation");
                }
            }
        }
    }

    public void removePaidBalance(@Observes @BusinessRemove(Business.Type.EMP_BALANCE) BusinessOperationEvent event) {
        Business business = employeeBusinessRepository.findBy(event.getBusinessId());
          for (EmployeeAction ea : business.getEmployeeActions()) {
                for(PaidBalance pb: ea.getEmployeePaid().getPaidBalances()){
                    pb.setEmployeePaid(null);
                }
                ea.getEmployeePaid().getPaidBalances().clear();
            }


        detachGiftMoney(business);
    }

    public void removeJoin(@Observes @BusinessRemove(Business.Type.EMP_JOIN) BusinessOperationEvent event) {
        Business business = employeeBusinessRepository.findBy(event.getBusinessId());
         for (EmployeeAction ea : business.getEmployeeActions()) {
                entityManager.remove(ea.getEmployee());
            }

    }

    public void removeJobChange(@Observes @BusinessRemove(Business.Type.EMP_JOB_CHANGE) BusinessOperationEvent event) {
        Business business = employeeBusinessRepository.findBy(event.getBusinessId());
          for (EmployeeAction ea : business.getEmployeeActions()) {
                ea.getEmployee().setJobInfo(ea.getJobInfo());
            }

        detachGiftMoney(business);
    }

    public void removeLeave(@Observes @BusinessRemove(Business.Type.EMP_LEAVE) BusinessOperationEvent event) {
        Business business = employeeBusinessRepository.findBy(event.getBusinessId());
          for (EmployeeAction ea : business.getEmployeeActions()) {
                ea.getEmployee().setStatus(Employee.Status.NORMAL);
            }

        detachGiftMoney(business);
    }

    public void removeGift(@Observes @BusinessRemove(Business.Type.EMP_GIFT) BusinessOperationEvent event) {

    }

}
