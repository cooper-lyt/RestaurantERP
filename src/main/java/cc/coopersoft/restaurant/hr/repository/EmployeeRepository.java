package cc.coopersoft.restaurant.hr.repository;

import cc.coopersoft.restaurant.ErpEntityManagerResolver;
import cc.coopersoft.restaurant.hr.repository.model.EmployeeOffice;
import cc.coopersoft.restaurant.model.Employee;
import org.apache.deltaspike.data.api.*;

import javax.persistence.FlushModeType;
import java.util.List;

/**
 * Created by cooper on 6/18/16.
 */
@Repository
@EntityManagerConfig(entityManagerResolver = ErpEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface EmployeeRepository extends EntityRepository<Employee,String> {


    @Query("select new cc.coopersoft.restaurant.hr.repository.model.EmployeeOffice(emp.office.id,max(emp.office.name),count(emp.id) ) from Employee emp where emp.status <> 'LEAVE' and (emp.jobs = :job or false = :hasJob) and (emp.name like :likeCondition or emp.id = :condition or emp.phone =:condition or false = :hasCondition)  group by emp.office.id order by emp.office.botime")
    List<EmployeeOffice> searchEmployeeOffices(@QueryParam("likeCondition") String likeCondition,
                                                  @QueryParam("condition")String fullCondition,
                                                  @QueryParam("hasCondition") boolean hasCondition,
                                                  @QueryParam("job") String job,
                                                  @QueryParam("hasJob") boolean hasJob);

    @Query("select emp from Employee emp where emp.status <> 'LEAVE' and emp.office.id = :officeId and (emp.jobs = :job or false = :hasJob) and (emp.name like :likeCondition or emp.id = :condition or emp.phone =:condition or false = :hasCondition)  order by emp.joinDate")
    List<Employee> searchEmployee(@QueryParam("likeCondition") String likeCondition,
                                  @QueryParam("condition")String fullCondition,
                                  @QueryParam("hasCondition") boolean hasCondition,
                                  @QueryParam("job") String job,
                                  @QueryParam("hasJob") boolean hasJob,
                                  @QueryParam("officeId") String officeId);

}
