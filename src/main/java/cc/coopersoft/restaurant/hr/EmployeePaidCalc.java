package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.util.DataHelper;
import cc.coopersoft.restaurant.hr.repository.EmployeeRepository;
import cc.coopersoft.restaurant.model.*;
import cc.coopersoft.restaurant.operation.OfficeHome;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static cc.coopersoft.restaurant.model.Business.Type.EMP_BALANCE;
import static cc.coopersoft.restaurant.model.Business.Type.EMP_JOB_CHANGE;
import static cc.coopersoft.restaurant.model.Business.Type.EMP_JOIN;

/**
 * Created by cooper on 7/6/16.
 */
@Named
@ConversationScoped
public class EmployeePaidCalc implements java.io.Serializable {

    private Date calcDate;

    @Inject
    @Default
    private Conversation conversation;

    @Inject
    private PaidProjectHome paidProjectHome;

    @Inject
    private OfficeHome officeHome;

    @Inject
    private EmployeeRepository employeeRepository;

    public Map<Employee,List<EmployeeAction>> actions;

    public Map<Employee,PaidBalance> paidBalanceMap;

    public Business business;



    //private Business business;

    private void putWorkContent(Date date, String workCode, double count, String commodityCode, String commodityName) {

        for(List<EmployeeAction> eavs : actions.values()){
            String curCode = null;
            for(EmployeeAction ea: eavs){

                if (EnumSet.of(EMP_BALANCE,EMP_JOIN,EMP_JOB_CHANGE).contains(ea.getBusiness().getType())){

                    if (curCode != null && curCode.equals(workCode) && date.before(ea.getValidTime())){
                        PaidBalance paidBalance = paidBalanceMap.get(ea.getEmployee());
                        paidBalance.setWorkContentMoney(paidBalance.getWorkContentMoney().add(paidProjectHome.getInstance().getWorkContentMoney().multiply(new BigDecimal(count))));
                        return;
                    }

                    curCode = ea.getJobInfo().getWorkCode().trim();
                    if (curCode != null && curCode.trim().equals("")){
                        curCode = null;
                    }

                }
            }
        }
    }

    public void workContentFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();

        try {
            Workbook workbook;
            if (file.getFileName().endsWith(".xls")) {
                workbook = new HSSFWorkbook(file.getInputstream());
            } else if (file.getFileName().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(file.getInputstream());
            } else {
                //TODO messages
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
                    Cell cell = row.getCell(0);
                    CellValue cellValue = evaluator.evaluate(cell);

                    if (cellValue != null && (Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()) &&
                            DateUtil.isCellDateFormatted(cell)) {
                        Date day = cell.getDateCellValue();
                        cell = row.getCell(1);
                        cellValue = evaluator.evaluate(cell);
                        if (cellValue != null) {
                            String workCode;
                            if (Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()) {
                                workCode = String.valueOf(cellValue.getNumberValue());
                            } else if (Cell.CELL_TYPE_STRING == cellValue.getCellType()) {
                                workCode = cellValue.getStringValue();
                            } else {
                                continue;
                            }
                            cell = row.getCell(2);
                            cellValue = evaluator.evaluate(cell);
                            if (cellValue != null && Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()) {
                                double count = cellValue.getNumberValue();

                                cell = row.getCell(3);
                                cellValue = evaluator.evaluate(cell);

                                String commodityCode = null;
                                if (cellValue != null) {
                                    if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                        commodityCode = String.valueOf(cellValue.getNumberValue());
                                    } else if (cellValue.getCellType() == Cell.CELL_TYPE_STRING) {
                                        commodityCode = cellValue.getStringValue();
                                    }
                                }

                                cell = row.getCell(4);
                                cellValue = evaluator.evaluate(cell);

                                String commodityName = null;
                                if (cellValue != null) {
                                    if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                        commodityName = String.valueOf(cellValue.getNumberValue());
                                    } else if (cellValue.getCellType() == Cell.CELL_TYPE_STRING) {
                                        commodityName = cellValue.getStringValue();
                                    }
                                }

                                putWorkContent(day, workCode, count, commodityCode, commodityName);


                            }


                        }

                        if (Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()) {

                        }


                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
//        file.

    }

    public void workTimeFileUpload(FileUploadEvent event) {

    }


    public String beginCalc() {
        business = new Business(UUID.randomUUID().toString().replace("-",""),
                Business.Type.EMP_BALANCE,Business.Status.COMPLETE,new Date());

        List<Employee> emps = employeeRepository.findByOfficeValid(officeHome.getInstance().getId());
        actions = new HashMap<Employee, List<EmployeeAction>>();
        paidBalanceMap = new HashMap<Employee, PaidBalance>();
        for(Employee emp: emps ){
            EmployeeAction lastBalance = employeeRepository.findLastBalance(emp.getId());
            Date startDate = (lastBalance == null) ? null : lastBalance.getValidTime();
            List<EmployeeAction> eas = employeeRepository.findBalanceAction(emp.getId(),startDate, DataHelper.getDayEndTime(calcDate),(lastBalance == null));
            actions.put(emp,eas);



            EmployeeAction ea = new EmployeeAction(UUID.randomUUID().toString().replace("-",""),
                    DataHelper.getDayEndTime(calcDate),emp,business);
            business.getEmployeeActions().add(ea);
            ea.setPaidBalance(new PaidBalance(ea));

            List<EmployeeGiftMoney> giftMoneys = new ArrayList<EmployeeGiftMoney>();
            JobInfo lastJobInfo = null;
            for (EmployeeAction oea : eas){
                if (Business.Type.EMP_GIFT.equals(oea.getBusiness().getType())){
                    if (oea.getEmployeeGiftMoney().getEmployeeGiftBalance() == null)
                        giftMoneys.add(oea.getEmployeeGiftMoney());
                }
                if (oea.getJobInfo() != null){
                    lastJobInfo = oea.getJobInfo();
                }
            }

            if (!giftMoneys.isEmpty()){
                Map<String,EmployeeGiftBalance> giftBalanceMap = new HashMap<String, EmployeeGiftBalance>();
                for(EmployeeGiftMoney egm: giftMoneys){
                    EmployeeGiftBalance egb = giftBalanceMap.get(egm.getCategory());
                    if (egb == null){
                        egb = new EmployeeGiftBalance(UUID.randomUUID().toString().replace("-",""),
                                BigDecimal.ZERO,egm.getCategory(),ea.getPaidBalance());
                        giftBalanceMap.put(egm.getCategory(),egb);
                    }
                    egb.setMoney(egb.getMoney().add(egm.getMoney()));
                    egb.getEmployeeGiftMoneys().add(egm);
                    egm.setEmployeeGiftBalance(egb);
                }

                ea.getPaidBalance().getEmployeeGiftBalances().addAll(giftBalanceMap.values());
            }

            ea.setJobInfo(new JobInfo(lastJobInfo,ea));
            paidBalanceMap.put(emp,ea.getPaidBalance());

        }

        if (conversation.isTransient()) {
            conversation.begin();
            conversation.setTimeout(800000);
        }

        return "/erp/hr/PaidCalcWorkContext.xhtml";
    }

    @PreDestroy
    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    public Date getCalcDate() {
        return calcDate;
    }

    public void setCalcDate(Date calcDate) {
        this.calcDate = calcDate;
    }
}
