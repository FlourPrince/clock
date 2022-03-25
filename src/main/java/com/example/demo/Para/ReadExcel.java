package com.example.demo.Para;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * The <code>com.example.demo.Para.readExcel</code> class have to be described
 * <p>
 * The <code>readExcel</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/3/16 13:48
 * @see
 * @since 1.0
 */
public class ReadExcel {

	/**
	 * 日志处理类
	 */
	private static Logger logger = LoggerFactory.getLogger(ReadExcel.class);

	public void read() {
	}


	public static void main(String[] args) {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(new File("C:\\Users\\pansw\\Desktop\\110156-锡望贷-易起投\\ProdPara_110156.xlsx"));
			int sheetNum = workbook.getNumberOfSheets();
			sheetNum = 1;
			for (int i = 0; i < sheetNum; i++) {
				//1.获取每个sheet
				Sheet sheet = workbook.getSheetAt(i);
				//2.获取sheetName
				String sheetName = sheet.getSheetName();
				//3.拼接sql,其中IRL_FEE_MAPPING 为update,其他insert
				//3.1 拼接insert *** values前部分
				StringBuilder stringBuilderHead = new StringBuilder();
				stringBuilderHead.append("INSERT INTO ").append("ENSEMBLE.").append(sheetName).append(" (");
				Row row2 = sheet.getRow(2);
				int row2Num = row2.getPhysicalNumberOfCells();
				for (int j = 0; j < row2Num; j++) {
					stringBuilderHead.append(row2.getCell(j).getStringCellValue());
					if (j != (row2Num - 1)) {
						stringBuilderHead.append(", ");
					} else {
						stringBuilderHead.append(") ");
					}
				}
				stringBuilderHead.append("VALUES ( ");
				//3.2 values后部分
				int rowNum = sheet.getPhysicalNumberOfRows();
				for (int k = 4; k < rowNum; k++) {
					Row row = sheet.getRow(k);
					StringBuilder stringBuilderBody = new StringBuilder();
					stringBuilderBody.append(stringBuilderHead);
					for (int m = 0; m < row2Num; m++) {
						String value = row.getCell(m).getStringCellValue();

						if (!"".equals(value)) {
							stringBuilderBody.append("'").append(value).append("'");
						} else {
							stringBuilderBody.append("NULL");
						}

						if (m != (row2Num - 1)) {
							stringBuilderBody.append(", ");
						} else {
							stringBuilderBody.append(") ");
						}
					}
					System.out.println(stringBuilderBody);

				}

			}

		} catch (InvalidFormatException e) {
			logger.debug("errorStack:", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("errorStack:", e);
			e.printStackTrace();
		}
	}
}
