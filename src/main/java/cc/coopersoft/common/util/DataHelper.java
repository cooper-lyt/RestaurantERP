package cc.coopersoft.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public static Date getDayBeginTime(Date value){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(value);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime();
    }

    public static Date getDayEndTime(Date value){
        return new Date(getDayBeginTime(value).getTime() + 24 * 60 * 60 * 1000 - 1);
    }
}
