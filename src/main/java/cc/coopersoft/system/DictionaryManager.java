package cc.coopersoft.system;

import cc.coopersoft.system.model.DictionaryCategory;
import cc.coopersoft.system.repository.DictionaryCategoryRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by cooper on 6/17/16.
 */
@Named
@RequestScoped
public class DictionaryManager {

    private List<DictionaryCategory> categories;

    @Inject
    private DictionaryCategoryRepository dictionaryCategoryRepository;

    public List<DictionaryCategory> getCategories() {
        if (categories == null){
            categories = dictionaryCategoryRepository.findAll();
        }
        return categories;
    }

}
