package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.model.PaidBalance;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by cooper on 7/17/16.
 */
@Named
@ConversationScoped
public class PaidCalc implements java.io.Serializable{

    @Inject
    private PaidProjectHome paidProjectHome;

   // private List<>

   // public PaidBalance
}
