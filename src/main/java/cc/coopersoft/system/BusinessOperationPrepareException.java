package cc.coopersoft.system;

/**
 * Created by cooper on 7/25/16.
 */
public class BusinessOperationPrepareException extends RuntimeException{

    private String messageKey;

    public BusinessOperationPrepareException(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
