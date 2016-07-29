package cc.coopersoft.system;

import cc.coopersoft.restaurant.model.Business;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cooper on 7/29/16.
 */
@Qualifier
@Target({METHOD, FIELD, PARAMETER, TYPE})
@Retention(RUNTIME)
public @interface BusinessRemoveAfter {

    Business.Type value();
}
