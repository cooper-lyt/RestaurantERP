package cc.coopersoft.system.repository;

import cc.coopersoft.system.SystemEntityManagerResolver;
import cc.coopersoft.system.model.Dictionary;
import cc.coopersoft.system.model.DictionaryCategory;
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
public interface DictionaryCategoryRepository extends EntityRepository<DictionaryCategory,String> {

    @Query("select category from DictionaryCategory category order by category.botime")
    List<DictionaryCategory> findAll();



}
