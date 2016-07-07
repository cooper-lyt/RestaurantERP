package cc.coopersoft.restaurant.hr.repository;

import cc.coopersoft.restaurant.ErpEntityManagerResolver;
import cc.coopersoft.restaurant.hr.repository.model.EmployeeOffice;
import cc.coopersoft.restaurant.model.Employee;
import cc.coopersoft.restaurant.model.EmployeeAction;
import org.apache.deltaspike.data.api.*;

import javax.persistence.FlushModeType;
import java.util.List;

/**
 * Created by cooper on 6/18/16.
 */
@Repository
@EntityManagerConfig(entityManagerResolver = ErpEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface EmployeeRepository extends EntityRepository<Employee,String> {


    @Query("select new cc.coopersoft.restaurant.hr.repository.model.EmployeeOffice(emp.office.id,max(emp.office.name),count(emp.id) ) from Employee emp where emp.status <> 'LEAVE' and (emp.name like :likeCondition or emp.id = :condition or emp.phone =:condition or false = :hasCondition)  group by emp.office.id order by emp.office.botime")
    List<EmployeeOffice> searchEmployeeOffices(@QueryParam("likeCondition") String likeCondition,
                                                  @QueryParam("condition")String fullCondition,
                                                  @QueryParam("hasCondition") boolean hasCondition);

    @Query("select emp from Employee emp where emp.status <> 'LEAVE' and emp.office.id = :officeId and (emp.name like :likeCondition or emp.id = :condition or emp.phone =:condition or false = :hasCondition)  order by emp.joinDate")
    List<Employee> searchEmployee(@QueryParam("likeCondition") String likeCondition,
                                  @QueryParam("condition")String fullCondition,
                                  @QueryParam("hasCondition") boolean hasCondition,
                                  @QueryParam("officeId") String officeId);

    @Query("select emp from Employee emp where emp.status <> 'LEAVE' and emp.office.id = :officeId and (emp.job.id = :jobId or false = :hasJob) and (emp.level = :level or false = :hasLevel) order by emp.joinDate")
    List<Employee> findByOffice(@QueryParam("officeId") String officeId,
                                @QueryParam("jobId") String jobId,
                                @QueryParam("hasJob") boolean hasJob,
                                @QueryParam("level") String level,
                                @QueryParam("hasLevel") boolean hasLevel);

    @Query("select emp from Employee emp where emp.status = 'NORMAL' order by emp.joinDate")
    List<Employee> findAllNormal();

    @Query("select emp from Employee emp where emp.status = 'NORMAL' and emp.office.id = ?1 order by emp.joinDate")
    List<Employee> findByOfficeValid(String officeId);

    @Query(value = "select empAction from EmployeeAction empAction where empAction.employee.id = ?1 and empAction.business.type = 'EMP_BALANCE' and empAction.business.status = 'COMPLETE' order by empAction.validTime desc", max = 1)
    EmployeeAction findLastBalance(String employeeId);

}
