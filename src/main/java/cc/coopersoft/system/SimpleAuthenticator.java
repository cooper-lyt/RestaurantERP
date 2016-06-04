package cc.coopersoft.system;

import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.model.basic.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * Created by cooper on 6/5/16.
 */

@PicketLink
public class SimpleAuthenticator extends BaseAuthenticator {


    @Inject
    private DefaultLoginCredentials credentials;

    @Inject
    private EntityManager entityManager;


    public void authenticate() {
        if ("root".equals(credentials.getUserId()) &&
                "cooper&cherry".equals(credentials.getPassword())) {
            setStatus(AuthenticationStatus.SUCCESS);
            setAccount(new User("root"));
            //TODO roles;
        } else {

            try {
                entityManager.createQuery("select user from User user where user.id=:id and user.password=:password", cc.coopersoft.system.model.User.class)
                        .setParameter("id", credentials.getUserId())
                        .setParameter("password", credentials.getPassword()).getSingleResult();
                setStatus(AuthenticationStatus.SUCCESS);
                setAccount(new User(credentials.getUserId()));
                //TODO roles;

            }catch (NoResultException e){

                setStatus(AuthenticationStatus.FAILURE);
                //facesContext.addMessage(null, new FacesMessage(
                //        "Authentication Failure - The username or password you provided were invalid."));
            }

        }
    }
}
