package cc.coopersoft.system;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

import javax.enterprise.event.Observes;

/**
 * Created by cooper on 6/10/16.
 */
public class HttpSecurityConfiguration {

    public void onInit(@Observes SecurityConfigurationEvent event) {
        SecurityConfigurationBuilder builder = event.getBuilder();

        builder
                .http()
                .allPaths()
                .authenticateWith()
                .form()
                .authenticationUri("/login.jsf")
                .loginPage("/login.jsf")
                .errorPage("/error.jsf")
                .restoreOriginalRequest()
                .forPath("/logout")
                .logout()
                .redirectTo("/index.html");
    }
}
