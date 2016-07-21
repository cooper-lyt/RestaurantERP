package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.model.BasicPaidItem;
import cc.coopersoft.restaurant.model.EmployeeGiftBalance;
import cc.coopersoft.restaurant.model.PaidBalance;
import cc.coopersoft.system.DictionaryProducer;
import cc.coopersoft.system.model.*;
import cc.coopersoft.system.model.Dictionary;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cooper on 7/21/16.
 */
public class PaidTable {

    public static class PaidTableItem{

        private Map<String,BasicPaidItem> baseMoney = new HashMap<String, BasicPaidItem>();

        private Map<String,EmployeeGiftBalance> giftMoney = new HashMap<String, EmployeeGiftBalance>();

        private PaidBalance paidBalance;

        public PaidTableItem(PaidBalance paidBalance) {
            this.paidBalance = paidBalance;
            for(BasicPaidItem basicPaidItem: paidBalance.getBasicPaidItems()){
                baseMoney.put(basicPaidItem.getCategory(),basicPaidItem);
            }
            for(EmployeeGiftBalance giftBalance: paidBalance.getEmployeeGiftBalances()){
                giftMoney.put(giftBalance.getCategory(),giftBalance);
            }
        }

        public PaidBalance getPaidBalance() {
            return paidBalance;
        }



        public BigDecimal getBasicParam(String category){
            BasicPaidItem result = baseMoney.get(category);
            if (result == null){
                return BigDecimal.ZERO;
            }else{
                return result.getCalcParam();
            }
        }

        public BigDecimal getGiftMoney(String category){
            EmployeeGiftBalance result = giftMoney.get(category);
            if (result == null){
                return BigDecimal.ZERO;
            }else{
                return result.getMoney();
            }
        }

        public BigDecimal getDayPaid(){
            BigDecimal result = BigDecimal.ZERO;
            for(BasicPaidItem bpi: paidBalance.getBasicPaidItems()){
                result = result.add(bpi.getCalcParam());
            }
            return result;
        }
    }

    private Set<Dictionary> basePaidCategories = new HashSet<Dictionary>();

    private Set<Dictionary> giftPaidCategories = new HashSet<Dictionary>();

    private List<PaidTableItem> items = new ArrayList<PaidTableItem>();

    public List<PaidTableItem> getItems() {
        return items;
    }

    public List<Dictionary> getBasePaidCategoryList(){
        List<Dictionary> result = new ArrayList<Dictionary>(basePaidCategories);
        Collections.sort(result);
        return result;
    }

    public List<Dictionary> getGiftPaidCategoryList(){
        List<Dictionary> result = new ArrayList<Dictionary>(giftPaidCategories);
        Collections.sort(result);
        return result;
    }


    public PaidTable(Collection<PaidBalance> paidBalances, DictionaryProducer dictionaryProducer) {
        for(PaidBalance pb: paidBalances){
            items.add(new PaidTableItem(pb));
            for(EmployeeGiftBalance egf: pb.getEmployeeGiftBalances()){
                giftPaidCategories.add(dictionaryProducer.getDictionary(egf.getCategory()));
            }
            for(BasicPaidItem bpi: pb.getBasicPaidItems()){
                basePaidCategories.add(dictionaryProducer.getDictionary(bpi.getCategory()));
            }
        }
        Collections.sort(items, new Comparator<PaidTableItem>() {
            public int compare(PaidTableItem o1, PaidTableItem o2) {
                if ( o1.getPaidBalance().getEmployeeAction().getEmployee().equals(o2.getPaidBalance().getEmployeeAction().getEmployee())){
                    return o1.getPaidBalance().getEmployeeAction().getValidTime().compareTo(o2.getPaidBalance().getEmployeeAction().getValidTime());
                }else{
                    return o1.getPaidBalance().getEmployeeAction().getEmployee().getJoinDate().compareTo(o2.getPaidBalance().getEmployeeAction().getEmployee().getJoinDate());
                }
            }
        });
    }
}
