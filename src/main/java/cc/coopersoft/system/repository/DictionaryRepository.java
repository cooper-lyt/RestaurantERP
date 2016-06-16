package cc.coopersoft.system.repository;

import cc.coopersoft.system.SystemEntityManagerResolver;
import cc.coopersoft.system.model.Dictionary;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import javax.persistence.FlushModeType;
import java.util.List;

/**
 * Created by cooper on 6/17/16.
 */
@Repository
@EntityManagerConfig(entityManagerResolver = SystemEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface DictionaryRepository extends EntityRepository<Dictionary,String> {


    @Query("select category from DictionaryCategaory category order by category.botime")
    List<Dictionary> findAll();
}
