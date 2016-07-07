package cc.coopersoft.restaurant.hr;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

    //private Business business;

    private void putWorkContent(Date date, String workCode, double count, String commodityCode, String commodityName) {

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
