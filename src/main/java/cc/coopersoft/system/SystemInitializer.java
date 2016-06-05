package cc.coopersoft.system;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Created by cooper on 6/5/16.
 */
@Singleton
@Startup
public class SystemInitializer {

    @Inject
    private PartitionManager partitionManager;

    @PostConstruct
    public void create() {
//        User john = new User("hr");
//        john.setEmail("hr@acme.com");
//        john.setFirstName("hr");
//        john.setLastName("hr");
//
//        IdentityManager identityManager = this.partitionManager.createIdentityManager();
//
//        identityManager.add(john);
//        identityManager.updateCredential(john, new Password("hr"));

//        // Create role "manager"
//        Role manager = new Role("manager");
//        identityManager.add(manager);
//
//        // Create application role "superuser"
//        Role superuser = new Role("superuser");
//        identityManager.add(superuser);
//
//        // Create group "sales"
//        Group sales = new Group("sales");
//        identityManager.add(sales);
//
//        RelationshipManager relationshipManager = this.partitionManager.createRelationshipManager();
//
//        // Make john a member of the "sales" group
//        addToGroup(relationshipManager, john, sales);
//
//        // Make mary a manager of the "sales" group
//        grantGroupRole(relationshipManager, mary, manager, sales);
//
//        // Grant the "superuser" application role to jane
//        grantRole(relationshipManager, jane, superuser);
    }
}
