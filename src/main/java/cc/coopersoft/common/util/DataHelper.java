package cc.coopersoft.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by cooper on 6/22/16.
 */
public class DataHelper {

    public static  <U> boolean isDirty(U oldValue, U newValue)
    {
        return oldValue!=newValue && (
                oldValue==null ||
                        !oldValue.equals(newValue)
        );
    }

    public static boolean empty(String value){
        return value == null || "".equals(value.trim());
    }


}
