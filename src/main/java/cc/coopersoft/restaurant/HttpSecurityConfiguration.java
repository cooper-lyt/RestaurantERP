package cc.coopersoft.restaurant;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

import javax.enterprise.event.Observes;

/**
 * Created by cooper on 6/5/16.
 */
public class HttpSecurityConfiguration {

    public void onInit(@Observes SecurityConfigurationEvent event) {
        SecurityConfigurationBuilder builder = event.getBuilder();

        builder.http()
                .allPaths()
                    .authenticateWith()
                    .form()
                    .authenticationUri("/login.xhtml")
                    .loginPage("/login.xhtml")
                    .errorPage("/login.xhtml?type=fail")
                    .restoreOriginalRequest()
                .forPath("/javax.faces.resource/*").unprotected()
                .forPath("/logout")
                    .logout()
                    .redirectTo("/index.html");
    }
}
