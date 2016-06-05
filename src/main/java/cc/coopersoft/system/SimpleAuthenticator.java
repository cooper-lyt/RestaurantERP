package cc.coopersoft.system;

import cc.coopersoft.restaurant.ErpEM;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.model.basic.User;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * Created by cooper on 6/5/16.
 */

//@PicketLink
public class SimpleAuthenticator extends BaseAuthenticator {


    @Inject
    private DefaultLoginCredentials credentials;

    @Inject
    @ErpEM
    private EntityManager entityManager;

    @Inject
    private FacesContext facesContext;

    public void authenticate() {
        if ("root".equals(credentials.getUserId()) &&
                "cooper&cherry".equals(credentials.getPassword())) {
            setStatus(AuthenticationStatus.SUCCESS);
            setAccount(new User("root"));
            //TODO roles;
        } else if (!AuthenticationStatus.SUCCESS.equals(getStatus())){




                facesContext.addMessage(null, new FacesMessage(
                      "Authentication Failure - The username or password you provided were invalid."));


        }
    }
}
