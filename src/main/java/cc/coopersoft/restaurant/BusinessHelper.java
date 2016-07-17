package cc.coopersoft.restaurant;

import cc.coopersoft.restaurant.model.Business;
import cc.coopersoft.restaurant.model.Operation;
import org.picketlink.Identity;
import org.picketlink.idm.model.basic.User;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cooper on 7/17/16.
 */
@Named
@ConversationScoped
public class BusinessHelper implements java.io.Serializable{


    @Inject
    private Identity identity;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Business createEmployeeBusiness(Business.Type type){
        String id = UUID.randomUUID().toString().replace("-","");
        Business business = new Business(id,type, Business.Status.COMPLETE,new Date());
        business.setDescription(description);
        User user = (User)identity.getAccount();
        business.getOperations().add(new Operation(id,user.getLoginName(),user.getFirstName() + user.getLastName(),"建立",new Date(), Operation.Type.APPLY,business));
        return business;
    }

}
