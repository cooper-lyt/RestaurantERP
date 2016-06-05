package cc.coopersoft.restaurant;

import javax.inject.Qualifier;
import java.lang.annotation.*;

/**
 * Created by cooper on 6/5/16.
 */
@Qualifier
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ErpEM {
}
