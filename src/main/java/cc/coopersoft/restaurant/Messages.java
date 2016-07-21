package cc.coopersoft.restaurant;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageTemplate;

/**
 * Created by cooper on 6/8/16.
 */
@MessageBundle
public interface Messages {

    @MessageTemplate("{authenticate_fail}")
    String authenticateFail();

    @MessageTemplate("{primary_key_conflict}")
    String primaryKeyConflict();

    @MessageTemplate("{employeeOperTimeBeforOfBalance}")
    String employeeOperTimeBeforOfBalance();

    @MessageTemplate("{fileMustExcelFile}")
    String fileMustExcelFile();

    @MessageTemplate("{excelFileReadError}")
    String excelFileReadError();

    @MessageTemplate("{excelExportError}")
    String excelExportError();
}
