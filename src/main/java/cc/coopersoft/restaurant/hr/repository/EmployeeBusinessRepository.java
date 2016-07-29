package cc.coopersoft.restaurant.hr.repository;

import cc.coopersoft.restaurant.ErpEntityManagerResolver;
import cc.coopersoft.restaurant.model.Business;
import org.apache.deltaspike.data.api.*;

import javax.persistence.FlushModeType;
import java.util.Date;
import java.util.List;

/**
 * Created by cooper on 7/22/16.
 */
@Repository
@EntityManagerConfig(entityManagerResolver = ErpEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface EmployeeBusinessRepository extends EntityRepository<Business,String> {

    @Query("select biz from Business biz where biz.type in ('EMP_JOIN','EMP_JOB_CHANGE','EMP_LEAVE','EMP_GIFT','EMP_BALANCE') and (biz.id = :condition or biz.description like :likeCondition or false = :hasCondition) and (biz.startTime >= :startDate or false = :hasStartDate) and (biz.startTime <= :endDate or false = :hasEndDate) and (biz.type = :businessType or false = :hasBusinessType) order by biz.startTime desc")
    List<Business> searchResultData(@QueryParam("condition") String condition, @QueryParam("likeCondition") String likeCondition, @QueryParam("hasCondition") boolean hasCondition, @QueryParam("startDate") Date startDate, @QueryParam("hasStartDate") boolean hasStartDate , @QueryParam("endDate") Date endDate, @QueryParam("hasEndDate") boolean hasEndDate,@QueryParam("businessType") Business.Type businessType, @QueryParam("hasBusinessType") boolean hasBusinessType, @FirstResult int firstResult,@MaxResults int maxResults);

    @Query("select count(biz) from Business biz where biz.type in ('EMP_JOIN','EMP_JOB_CHANGE','EMP_LEAVE','EMP_GIFT','EMP_BALANCE') and (biz.id = :condition or biz.description like :likeCondition or false = :hasCondition) and (biz.startTime >= :startDate or false = :hasStartDate) and (biz.startTime <= :endDate or false = :hasEndDate) and (biz.type = :businessType or false = :hasBusinessType) order by biz.startTime desc")
    Long searchResultCount(@QueryParam("condition") String condition, @QueryParam("likeCondition") String likeCondition, @QueryParam("hasCondition") boolean hasCondition, @QueryParam("startDate") Date startDate, @QueryParam("hasStartDate") boolean hasStartDate , @QueryParam("endDate") Date endDate, @QueryParam("hasEndDate") boolean hasEndDate,@QueryParam("businessType") Business.Type businessType, @QueryParam("hasBusinessType") boolean hasBusinessType);


    @Query("select count(ea) from EmployeeAction ea where ea.validTime > ?1 and ea.employee.id = ?2")
    Long findAfterCount(Date date, String employeeId);
}
