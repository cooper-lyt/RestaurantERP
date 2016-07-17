package cc.coopersoft.restaurant.operation.repository;

import cc.coopersoft.restaurant.ErpEntityManagerResolver;
import cc.coopersoft.restaurant.model.OfficeType;
import cc.coopersoft.restaurant.model.Res;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import javax.persistence.FlushModeType;
import java.util.List;

/**
 * Created by cooper on 6/24/16.
 */
@Repository
@EntityManagerConfig(entityManagerResolver = ErpEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface OfficeTypeRepository extends EntityRepository<OfficeType,String>{

    @Query("select type from OfficeType type order by botime")
    List<OfficeType> findAllOrderByBotimeAsc();

    @Query("select type from OfficeType type where type.enable = true order by botime")
    List<OfficeType> findEnableTypes();

    @Query("select res from Res res left join fetch res.resClass rc where res.enable = true and (rc.product = true or rc.semi = true) and res.id not in (?1) order by rc.botime , res.botime")
    List<Res> getCanSelectProduceRes(List<String> haveIds);

    @Query("select res from Res res left join fetch res.resClass rc where res.enable = true and (rc.product = true or rc.semi = true) order by rc.botime , res.botime")
    List<Res> getAllProduceRes();

}
