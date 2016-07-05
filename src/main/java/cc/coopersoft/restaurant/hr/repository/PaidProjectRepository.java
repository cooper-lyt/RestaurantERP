package cc.coopersoft.restaurant.hr.repository;

import cc.coopersoft.restaurant.ErpEntityManagerResolver;
import cc.coopersoft.restaurant.model.PaidProject;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import javax.persistence.FlushModeType;
import java.util.List;

/**
 * Created by cooper on 7/4/16.
 */
@Repository
@EntityManagerConfig(entityManagerResolver = ErpEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface PaidProjectRepository extends EntityRepository<PaidProject,String> {

    @Query("select project from PaidProject project order by project.botime")
    List<PaidProject> findAllOrderByBotimeAsc();

    @Query("select distinct item.category from PaidItem item where item.paidProject.id = ?1")
    List<String> findCategoryByProject(String id);

}
