package cc.coopersoft.system;

import javax.inject.Qualifier;
import java.lang.annotation.*;

/**
 * Created by cooper on 6/10/16.
 */
@Qualifier
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemEM {
}
