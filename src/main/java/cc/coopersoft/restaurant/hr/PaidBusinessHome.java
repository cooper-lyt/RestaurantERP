package cc.coopersoft.restaurant.hr;

import cc.coopersoft.common.EntityHome;
import cc.coopersoft.restaurant.Messages;
import cc.coopersoft.restaurant.hr.repository.BusinessRepository;
import cc.coopersoft.restaurant.model.*;
import cc.coopersoft.system.DictionaryProducer;
import cc.coopersoft.system.model.*;
import cc.coopersoft.system.model.Dictionary;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cooper on 7/21/16.
 */
@Named
@RequestScoped
public class PaidBusinessHome extends EntityHome<Business, String> {

    @Inject
    @Default
    private EntityManager entityManager;

    @Inject
    private BusinessRepository businessRepository;

    @Inject
    private DictionaryProducer dictionaryProducer;

    @Inject
    private FacesContext facesContext;

    @Inject
    private JsfMessage<Messages> messages;

    protected Business createInstance() {
        return new Business();
    }

    protected EntityRepository<Business, String> getEntityRepository() {
        return businessRepository;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }


    public static class PaidTableItem {

        private Map<String, BasicPaidItem> baseMoney = new HashMap<String, BasicPaidItem>();

        private Map<String, EmployeeGiftBalance> giftMoney = new HashMap<String, EmployeeGiftBalance>();

        private PaidBalance paidBalance;

        public PaidTableItem(PaidBalance paidBalance) {
            this.paidBalance = paidBalance;
            for (BasicPaidItem basicPaidItem : paidBalance.getBasicPaidItems()) {
                baseMoney.put(basicPaidItem.getCategory(), basicPaidItem);
            }
            for (EmployeeGiftBalance giftBalance : paidBalance.getEmployeeGiftBalances()) {
                giftMoney.put(giftBalance.getCategory(), giftBalance);
            }
        }

        public PaidBalance getPaidBalance() {
            return paidBalance;
        }


        public BigDecimal getBasicParam(String category) {
            BasicPaidItem result = baseMoney.get(category);
            if (result == null) {
                return BigDecimal.ZERO;
            } else {
                return result.getCalcParam();
            }
        }

        public BigDecimal getGiftMoney(String category) {
            EmployeeGiftBalance result = giftMoney.get(category);
            if (result == null) {
                return BigDecimal.ZERO;
            } else {
                return result.getMoney();
            }
        }

        public BigDecimal getDayPaid() {
            BigDecimal result = BigDecimal.ZERO;
            for (BasicPaidItem bpi : paidBalance.getBasicPaidItems()) {
                result = result.add(bpi.getCalcParam());
            }
            return result;
        }
    }

    private Set<Dictionary> basePaidCategories;

    private Set<Dictionary> giftPaidCategories;

    private List<PaidTableItem> items;

    public List<PaidTableItem> getItems() {
        initPaidTable();
        return items;
    }

    public List<Dictionary> getBasePaidCategoryList() {
        initPaidTable();
        List<Dictionary> result = new ArrayList<Dictionary>(basePaidCategories);
        Collections.sort(result);
        return result;
    }

    public List<Dictionary> getGiftPaidCategoryList() {
        initPaidTable();
        List<Dictionary> result = new ArrayList<Dictionary>(giftPaidCategories);
        Collections.sort(result);
        return result;
    }

    public BigDecimal getAllTotalMoney() {
        initPaidTable();
        BigDecimal result = BigDecimal.ZERO;
        for (PaidTableItem item : items) {
            result = result.add(item.getPaidBalance().getTotalMoney());
        }
        return result;
    }


    @Override
    protected void clearDirtyInstance(){
        super.clearDirtyInstance();
        items = null;
        basePaidCategories = null;
        giftPaidCategories = null;
    }

    @Override
    protected String getInstaceId() {
        return getInstance().getId();
    }

    @PostConstruct
    public void initParam(){
        setId(facesContext.getExternalContext().getRequestParameterMap().get("paidBusinessId"));
        logger.config("set Business Home ID form param:" + getId());
    }


    private void initPaidTable() {

        if (giftPaidCategories == null || basePaidCategories == null || items == null) {
            giftPaidCategories = new HashSet<Dictionary>();
            basePaidCategories = new HashSet<Dictionary>();

            items = new ArrayList<PaidTableItem>();

            System.out.println("1C:" + getInstance().getEmployeeActions().size());
            for (EmployeeAction ea : getInstance().getEmployeeActions()) {

                System.out.println("2C:" + ea.getEmployeePaid().getPaidBalances().size());
                for (PaidBalance pb : ea.getEmployeePaid().getPaidBalances()) {
                    System.out.println("3C:" + pb);
                    items.add(new PaidTableItem(pb));
                    for (EmployeeGiftBalance egf : pb.getEmployeeGiftBalances()) {
                        giftPaidCategories.add(dictionaryProducer.getDictionary(egf.getCategory()));
                    }
                    for (BasicPaidItem bpi : pb.getBasicPaidItems()) {
                        basePaidCategories.add(dictionaryProducer.getDictionary(bpi.getCategory()));
                    }
                }
            }

            Collections.sort(items, new Comparator<PaidTableItem>() {
                public int compare(PaidTableItem o1, PaidTableItem o2) {
                    if (o1.getPaidBalance().getEmployeeAction().getEmployee().equals(o2.getPaidBalance().getEmployeeAction().getEmployee())) {
                        return o1.getPaidBalance().getEmployeeAction().getValidTime().compareTo(o2.getPaidBalance().getEmployeeAction().getValidTime());
                    } else {
                        return o1.getPaidBalance().getEmployeeAction().getEmployee().getJoinDate().compareTo(o2.getPaidBalance().getEmployeeAction().getEmployee().getJoinDate());
                    }
                }
            });
        }
    }


    public void exportToExcel() {
        initPaidTable();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle = workbook.createCellStyle();

        //标题一格式
        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
        XSSFFont font = workbook.createFont();// 创建字体对象
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
        headCellStyle.setFont(font);


        int i = 0;
        int j = 0;

        XSSFSheet sheet = workbook.createSheet("工资报表");
        Row row = sheet.createRow(i++);
        Cell cell = row.createCell(j++);
        cell.setCellStyle(headCellStyle);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("编号");

        cell = row.createCell(j++);
        cell.setCellStyle(headCellStyle);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("姓名");

        cell = row.createCell(j++);
        cell.setCellStyle(headCellStyle);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("职位");

        cell = row.createCell(j++);
        cell.setCellStyle(headCellStyle);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("技能等级");


        for (Dictionary d : getBasePaidCategoryList()) {
            cell = row.createCell(j++);
            cell.setCellStyle(headCellStyle);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(d.getName());
        }


        cell = row.createCell(j++);
        cell.setCellStyle(headCellStyle);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("日工资总计");

        cell = row.createCell(j++);
        cell.setCellStyle(headCellStyle);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("绩效");

        cell = row.createCell(j++);
        cell.setCellStyle(headCellStyle);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("出勤");

        cell = row.createCell(j++);
        cell.setCellStyle(headCellStyle);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("满勤奖");


        for (Dictionary d : getGiftPaidCategoryList()) {
            cell = row.createCell(j++);
            cell.setCellStyle(headCellStyle);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(d.getName());
        }

        cell = row.createCell(j++);
        cell.setCellStyle(headCellStyle);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("工资合计");


        for (PaidTableItem item : getItems()) {
            j = 0;
            row = sheet.createRow(i++);

            cell = row.createCell(j++);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(item.getPaidBalance().getEmployeeAction().getEmployee().getId());


            cell = row.createCell(j++);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(item.getPaidBalance().getEmployeeAction().getEmployee().getName());


            cell = row.createCell(j++);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(item.getPaidBalance().getEmployeeAction().getEmployee().getJob().getName());


            cell = row.createCell(j++);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(dictionaryProducer.getDictionaryValue(item.getPaidBalance().getEmployeeAction().getEmployee().getLevel()));

            for (Dictionary d : getBasePaidCategoryList()) {
                cell = row.createCell(j++);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(item.getBasicParam(d.getId()).doubleValue());

            }

            cell = row.createCell(j++);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(item.getDayPaid().doubleValue());

            cell = row.createCell(j++);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(item.getPaidBalance().getWorkContentMoney().doubleValue());

            cell = row.createCell(j++);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(item.getPaidBalance().getWorkDay().doubleValue());

            cell = row.createCell(j++);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(item.getPaidBalance().getWorkFullMoney().doubleValue());


            for (Dictionary d : getGiftPaidCategoryList()) {
                cell = row.createCell(j++);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(item.getGiftMoney(d.getId()).doubleValue());
            }

            cell = row.createCell(j++);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(item.getPaidBalance().getTotalMoney().doubleValue());


        }

        sheet.setForceFormulaRecalculation(true);
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=exportBusiness.xlsx");
        try {
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();
        } catch (IOException e) {
            messages.addError().excelExportError();
        }
    }


}
