package com.example.demo.Git.Excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The <code>com.example.demo.Git.Excel.ReadExcel</code> class have to be described
 * <p>
 * The <code>ReadExcel</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/2/24 21:33
 * @see
 * @since 1.0
 */
public class ReadExcel {
	/**
	 * 日志处理类
	 */
	private static Logger logger = LoggerFactory.getLogger(ReadExcel.class);
	private  String fileName;

	public ReadExcel(String fileName) {
		this.fileName = fileName;
	}
	public Map<String, String> getFeature() {
		Map<String, String> map = new HashMap<>();
		Workbook workbook = null;
			try {
				workbook = WorkbookFactory.create(new File(fileName));
			Sheet sheet = workbook.getSheetAt(0);
			int RowsNumber = sheet.getPhysicalNumberOfRows();
			for (int i = 1; i < RowsNumber; i++) {
				Row row = sheet.getRow(i);
				if(row!=null){
					Cell cell13 = row.getCell(13);
					if (cell13 != null) {
						String branches = cell13.getStringCellValue().trim();
						String owner = row.getCell(16).getStringCellValue().trim();
						map.put(branches, owner);
					}
				}

			}
		} catch (IOException e) {
			logger.debug("errorStack:", e);
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			logger.debug("errorStack:", e);
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.debug("errorStack:", e);
					e.printStackTrace();
				}
			}
		}

		return map;
	}
}
