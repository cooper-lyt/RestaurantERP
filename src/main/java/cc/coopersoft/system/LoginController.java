package cc.coopersoft.system;

import cc.coopersoft.restaurant.Messages;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.picketlink.Identity;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by cooper on 6/5/16.
 */
@Stateless
@Named
public class LoginController {

    @Inject
    private Identity identity;

    @Inject
    private JsfMessage<Messages> messages;

    public void login() {
        Identity.AuthenticationResult result = identity.login();
        if (Identity.AuthenticationResult.FAILED.equals(result)) {
            messages.addFatal().authenticateFail();
        }
    }

}
