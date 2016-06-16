package cc.coopersoft.system.repository;

import cc.coopersoft.system.SystemEntityManagerResolver;
import cc.coopersoft.system.model.DictionaryCategaory;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.persistence.FlushModeType;

/**
 * Created by cooper on 6/17/16.
 */
@Repository
@EntityManagerConfig(entityManagerResolver = SystemEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface DictionaryCategoryRepository extends EntityRepository<DictionaryCategaory,String> {
}
