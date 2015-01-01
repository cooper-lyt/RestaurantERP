package cc.coopersoft.system.repository;

import cc.coopersoft.system.SystemEntityManagerResolver;
import cc.coopersoft.system.model.Dictionary;
import org.apache.deltaspike.data.api.*;

import javax.persistence.FlushModeType;
import java.util.List;

/**
 * Created by cooper on 6/17/16.
 */
@Repository
@EntityManagerConfig(entityManagerResolver = SystemEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface DictionaryRepository extends EntityRepository<Dictionary,String> {

    @Query("select max(dic.pri) from Dictionary dic where dic.category.id=?1")
    Integer getMaxPri(String id);

    @Query("select dic from Dictionary dic where dic.category.id=?1 and dic.pri < ?2 order by dic.pri desc")
    List<Dictionary> getUp(String categoryId, int pri, @MaxResults int pageSize);

    @Query("select dic from Dictionary dic where dic.category.id=?1 and dic.pri > ?2 order by dic.pri")
    List<Dictionary> getDown(String categoryId,int pri,@MaxResults int pageSize);

    @Query("select dic from Dictionary dic where dic.category.id =?1 and dic.enable = true order by dic.pri")
    List<Dictionary> getValidDictionaries(String categoryId);


    @Query("select dic from Dictionary dic left join fetch dic.category where dic.id=?1")
    Dictionary getDictionary(String dictionaryId);
}
