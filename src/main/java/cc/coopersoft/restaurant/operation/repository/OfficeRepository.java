package cc.coopersoft.restaurant.operation.repository;

import cc.coopersoft.restaurant.operation.model.Office;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

/**
 * Created by cooper on 6/10/16.
 */
@Repository
public interface OfficeRepository extends EntityRepository<Office,String> {

    @Query("select office from Office office where office.name like ?1 and office.status <> 'DESTROY' order by office.botime")
    List<Office> findByCondition(String condition);

    @Query("select office from Office office where office.name like ?1 order by office.botime")
    List<Office> findByConditionAll(String condition);

}
