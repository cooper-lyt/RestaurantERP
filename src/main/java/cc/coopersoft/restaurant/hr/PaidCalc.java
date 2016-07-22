package cc.coopersoft.restaurant.hr;

import cc.coopersoft.restaurant.Messages;
import cc.coopersoft.restaurant.hr.repository.EmployeeRepository;
import cc.coopersoft.restaurant.model.*;
import cc.coopersoft.restaurant.res.repository.ResRepository;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cooper on 7/17/16.
 */
@Named
@ConversationScoped
public class PaidCalc implements java.io.Serializable {

    @Inject
    private PaidProjectHome paidProjectHome;

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private ResRepository resRepository;

    @Inject
    private JsfMessage<Messages> messages;

    private Map<String, List<WorkContentMoney>> workContentData;

    public Map<String, List<WorkContentMoney>> getWorkContentData() {
        return workContentData;
    }

    public List<Map.Entry<String, List<WorkContentMoney>>> getWorkContentList(){
        if (workContentData == null){
            return new ArrayList<Map.Entry<String, List<WorkContentMoney>>>(0);
        }
        List<Map.Entry<String, List<WorkContentMoney>>> result = new ArrayList<Map.Entry<String, List<WorkContentMoney>>>(getWorkContentData().entrySet());
        Collections.sort(result, new Comparator<Map.Entry<String, List<WorkContentMoney>>>() {
            public int compare(Map.Entry<String, List<WorkContentMoney>> o1, Map.Entry<String, List<WorkContentMoney>> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        return result;
    }

    public void balanceAndPaid(Employee emp, PaidBalance paidBalance){
        List<PaidBalance> balances = employeeRepository.findNoPaidBalance(emp.getId(),employeeRepository.findLastPaidTime(emp.getId()));
        calcBalance(emp,paidBalance);
        balances.add(paidBalance);
        EmployeePaid employeePaid = new EmployeePaid(paidBalance.getEmployeeAction());
        BigDecimal money = BigDecimal.ZERO;
        for(PaidBalance pb: balances){
            pb.setEmployeePaid(employeePaid);
            employeePaid.getPaidBalances().add(pb);

            money = money.add(pb.getTotalMoney());
        }
        employeePaid.setTotalMoney(money);
        paidBalance.getEmployeeAction().setEmployeePaid(employeePaid);
    }

    public void balanceAndPaid(Employee emp, PaidBalance paidBalance, boolean fullWork){

        paidBalance.setWorkFullMoney(fullWork ? paidProjectHome.getInstance().getFullWorkMoney() : BigDecimal.ZERO);

        balanceAndPaid(emp,paidBalance);
    }




    public void calcBalance(Employee emp, PaidBalance paidBalance) {
        BigDecimal totalMoney = BigDecimal.ZERO;

        //基本工资部分
        for (Map.Entry<String, List<PaidItem>> entry : paidProjectHome.getPaidItems()) {
            for (PaidItem paidItem : entry.getValue()) {
                if (emp.getJobInfo().getJob().equals(paidItem.getJob()) && emp.getJobInfo().getLevel().equals(paidItem.getLevel())) {

                    BasicPaidItem basicPaidItem =
                            new BasicPaidItem(UUID.randomUUID().toString().replace("-", ""), entry.getKey(),paidItem.getMoney(), paidBalance.getWorkDay().multiply(paidItem.getMoney()), paidBalance);
                    paidBalance.getBasicPaidItems().add(basicPaidItem);
                    totalMoney = totalMoney.add(basicPaidItem.getMoney());
                    break;
                }
            }
        }

        //奖励部分

        List<EmployeeGiftMoney> employeeGiftMoneys = employeeRepository.findGiftMoney(emp.getId(), getLastBalanceTime(emp.getId()), paidBalance.getEmployeeAction().getValidTime());
        Map<String, List<EmployeeGiftMoney>> giftBalances = new HashMap<String, List<EmployeeGiftMoney>>();
        for (EmployeeGiftMoney egm : employeeGiftMoneys) {
            List<EmployeeGiftMoney> egbs = giftBalances.get(egm.getCategory());
            if (egbs == null) {
                egbs = new ArrayList<EmployeeGiftMoney>();
                giftBalances.put(egm.getCategory(), egbs);
            }
            egbs.add(egm);
        }

        //BigDecimal giftMoney = BigDecimal.ZERO;
        for (Map.Entry<String, List<EmployeeGiftMoney>> entry : giftBalances.entrySet()) {
            EmployeeGiftBalance egb = new EmployeeGiftBalance(UUID.randomUUID().toString().replace("-", ""), entry.getKey(), paidBalance);

            BigDecimal money = BigDecimal.ZERO;
            for (EmployeeGiftMoney egm : entry.getValue()) {
                money = money.add(egm.getMoney());
                egm.setEmployeeGiftBalance(egb);
                egb.getEmployeeGiftMoneys().add(egm);
            }
            egb.setMoney(money);
            //giftMoney = giftMoney.add(money);
            totalMoney = totalMoney.add(money);
            paidBalance.getEmployeeGiftBalances().add(egb);
        }
        //paidBalance.setWorkContentMoney(giftMoney);

        //绩效

        if (workContentData != null && emp.getJobInfo().getWorkCode() != null && !emp.getJobInfo().getWorkCode().trim().equals("")) {
            List<WorkContentMoney> contentMoneys = workContentData.get(emp.getJobInfo().getWorkCode());
            BigDecimal content = BigDecimal.ZERO;
            if (contentMoneys != null) {
                for (WorkContentMoney contentMoney : contentMoneys) {
                    if (contentMoney.getMoney() != null && !BigDecimal.ZERO.equals(contentMoney.getMoney())) {
                        content = content.add(contentMoney.getMoney());
                        paidBalance.getWorkContentMoneys().add(contentMoney);
                        contentMoney.setPaidBalance(paidBalance);
                    }

                }
            }

            paidBalance.setWorkContentMoney(content);
            totalMoney = totalMoney.add(content);
        }


        paidBalance.setTotalMoney(totalMoney.add(paidBalance.getWorkFullMoney()));

    }


    public Date getLastBalanceTime(String employeeId) {
        EmployeeAction employeeAction = employeeRepository.findLastBalance(employeeId);
        return employeeAction.getValidTime();
    }

    public void clearFile() {
        workContentData = null;
    }

    public void workContentFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        workContentData = new HashMap<String, List<WorkContentMoney>>();
        try {
            Workbook workbook;
            if (file.getFileName().endsWith(".xls")) {
                workbook = new HSSFWorkbook(file.getInputstream());
            } else if (file.getFileName().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(file.getInputstream());
            } else {
                messages.addError().fileMustExcelFile();
                return;
            }

            Iterator<Sheet> it = workbook.sheetIterator();
            while (it.hasNext()) {
                Sheet sheet = it.next();
                FormulaEvaluator evaluator = workbook.getCreationHelper()
                        .createFormulaEvaluator();

                int minRowIx = sheet.getFirstRowNum();
                int maxRowIx = sheet.getLastRowNum();

                for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
                    Row row = sheet.getRow(rowIx);

                    short minColIx = row.getFirstCellNum();

                    String workCode;
                    Cell cell = row.getCell(minColIx);
                    CellValue cellValue = evaluator.evaluate(cell);


                    if (cellValue != null) {
                        if (Cell.CELL_TYPE_STRING == cellValue.getCellType()){
                            workCode = cell.getStringCellValue().trim();
                        }else if (Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()){
                            workCode = new java.text.DecimalFormat("#0").format(cell.getNumericCellValue());
                        }else{
                            continue;
                        }

                    } else {
                        continue;
                    }


                    cell = row.getCell(minColIx+1);
                    cellValue = evaluator.evaluate(cell);
                    WorkContentMoney item = new WorkContentMoney(UUID.randomUUID().toString().replace("-", ""),workCode);
                    if (cellValue != null ) {
                        String resId;
                        if (Cell.CELL_TYPE_STRING == cellValue.getCellType()){
                            resId = cellValue.getStringValue().trim();
                        }else if (Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()){
                            resId = new java.text.DecimalFormat("#0").format(cellValue.getNumberValue());
                        }else{
                           continue;
                        }

                        Res res = resRepository.findBy(resId);
                        if (res != null) {
                            item.setRes(res);
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }

                    cell = row.getCell(minColIx+2);
                    cellValue = evaluator.evaluate(cell);
                    if (cellValue != null) {
                        if (Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()) {
                            item.setCount(new BigDecimal(cellValue.getNumberValue()));
                        } else if (Cell.CELL_TYPE_STRING == cellValue.getCellType()) {
                            try {
                                item.setCount(new BigDecimal(cellValue.getStringValue()));
                            } catch (NumberFormatException e) {
                                item.setCount(BigDecimal.ZERO);
                            }
                        }
                    }

                    cell = row.getCell(minColIx+3);
                    cellValue = evaluator.evaluate(cell);

                    if (cellValue != null) {
                        if (Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()) {
                            item.setPrice(new BigDecimal(cellValue.getNumberValue()));
                        } else if (Cell.CELL_TYPE_STRING == cellValue.getCellType()) {
                            try {
                                item.setPrice(new BigDecimal(cellValue.getStringValue()));
                            } catch (NumberFormatException e) {
                                item.setPrice(BigDecimal.ZERO);
                            }
                        }
                    }

                    for (PaidContentItem pci : paidProjectHome.getPaidContentItems()) {
                        if (pci.getRes().equals(item.getRes())) {
                            item.setMoney(item.getCount().multiply(pci.getMoney()));
                        }
                    }

                    List<WorkContentMoney> moneys = workContentData.get(workCode);
                    if (moneys == null) {
                        moneys = new ArrayList<WorkContentMoney>();
                        workContentData.put(workCode, moneys);
                    }
                    moneys.add(item);

                }
            }


        } catch (IOException e) {
            messages.addError().excelFileReadError();
        }

    }

}
