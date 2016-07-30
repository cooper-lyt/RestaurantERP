package cc.coopersoft.restaurant.hr.repository;

import cc.coopersoft.restaurant.ErpEntityManagerResolver;
import cc.coopersoft.restaurant.hr.repository.model.EmployeeOffice;
import cc.coopersoft.restaurant.model.*;
import org.apache.deltaspike.data.api.*;

import javax.persistence.FlushModeType;
import java.util.Date;
import java.util.List;

/**
 * Created by cooper on 6/18/16.
 */
@Repository
@EntityManagerConfig(entityManagerResolver = ErpEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface EmployeeRepository extends EntityRepository<Employee,String> {


    @Query("select new cc.coopersoft.restaurant.hr.repository.model.EmployeeOffice(emp.jobInfo.office.id,max(emp.jobInfo.office.name),count(emp.id) ) from Employee emp where emp.status <> 'LEAVE' and (emp.name like :likeCondition or emp.id = :condition or emp.phone =:condition or false = :hasCondition)  group by emp.jobInfo.office.id order by emp.jobInfo.office.botime")
    List<EmployeeOffice> searchEmployeeOffices(@QueryParam("likeCondition") String likeCondition,
                                                  @QueryParam("condition")String fullCondition,
                                                  @QueryParam("hasCondition") boolean hasCondition);

    @Query("select emp from Employee emp where emp.status <> 'LEAVE' and emp.jobInfo.office.id = :officeId and (emp.name like :likeCondition or emp.id = :condition or emp.phone =:condition or false = :hasCondition)  order by emp.joinDate")
    List<Employee> searchEmployee(@QueryParam("likeCondition") String likeCondition,
                                  @QueryParam("condition")String fullCondition,
                                  @QueryParam("hasCondition") boolean hasCondition,
                                  @QueryParam("officeId") String officeId);

    @Query("select emp from Employee emp where emp.status <> 'LEAVE' and emp.jobInfo.office.id = :officeId and (emp.jobInfo.job.id = :jobId or false = :hasJob) and (emp.jobInfo.level = :level or false = :hasLevel) order by emp.joinDate")
    List<Employee> findByOffice(@QueryParam("officeId") String officeId,
                                @QueryParam("jobId") String jobId,
                                @QueryParam("hasJob") boolean hasJob,
                                @QueryParam("level") String level,
                                @QueryParam("hasLevel") boolean hasLevel);

    @Query("select emp from Employee emp where emp.status = 'NORMAL' order by emp.joinDate")
    List<Employee> findAllNormal();

    @Query("select emp from Employee emp where emp.status = 'NORMAL' and emp.jobInfo.office.id = ?1 order by emp.joinDate")
    List<Employee> findByOfficeValid(String officeId);

    @Query(value = "select empAction from EmployeeAction empAction left join fetch empAction.jobInfo left join fetch empAction.paidBalance left join fetch empAction.employeeGiftMoney where empAction.employee.id = ?1 and (empAction.business.type = 'EMP_BALANCE' or empAction.business.type = 'EMP_JOIN' or empAction.business.type = 'EMP_JOB_CHANGE' or empAction.business.type = 'EMP_LEAVE') and empAction.business.status = 'COMPLETE' order by empAction.validTime desc", max = 1)
    EmployeeAction findLastBalance(String employeeId);

    @Query("select egm from EmployeeGiftMoney egm left join fetch egm.employeeAction ea where egm.employeeGiftBalance is null and ea.validTime > ?2 and ea.validTime < ?3 and ea.employee.id = ?1")
    List<EmployeeGiftMoney> findGiftMoney(String employeeId, Date start, Date end);


    @Query("select pb from PaidBalance pb where pb.employeePaid is null and pb.employeeAction.employee.id = ?1 and pb.employeeAction.validTime > ?2")
    List<PaidBalance> findNoPaidBalance(String employeeId, Date startDate);

    @Query(value = "select ea.validTime from EmployeeAction ea where ea.employee.id = ?1 and (ea.business.type = 'EMP_JOIN' or ea.business.type = 'EMP_BALANCE') order by ea.validTime desc" ,max = 1)
    Date findLastPaidTime(String employeeId);



}
