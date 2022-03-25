package com.example.demo.Git.Excel;

import com.example.demo.Git.Model.WriteExcelVo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * The <code>com.example.demo.Git.Excel.WriteExcel</code> class have to be described
 * <p>
 * The <code>WriteExcel</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/2/24 21:33
 * @see
 * @since 1.0
 */

public class WriteExcel {
	/**
	 * 日志处理类
	 */
	private static Logger logger = LoggerFactory.getLogger(WriteExcel.class);
	private  String fileName;

	public WriteExcel(String fileName) {
		this.fileName = fileName;
	}


	public void writeCodeComparePath(List<WriteExcelVo> writeExcelVos) {
		Workbook workbook=null;
		FileOutputStream fileOutputStream=null;
		try {
			 workbook = new SXSSFWorkbook();
			 fileOutputStream=new FileOutputStream(new File(fileName));
			Sheet sheet = workbook.createSheet();
			//创建标题
			Row row0 = sheet.createRow(0);
			row0.createCell(0).setCellValue("版本");
			row0.createCell(1).setCellValue("负责人");
			row0.createCell(2).setCellValue("业务模块");
			row0.createCell(3).setCellValue("涉及代码");
			//编写内容
			int num=1;
			for(WriteExcelVo writeExcelVo:writeExcelVos){
				Row row = sheet.createRow(num);
				row.createCell(0).setCellValue(writeExcelVo.getFeature());
				row.createCell(1).setCellValue(writeExcelVo.getOwner());
				if (
						"高嘉君".equals(writeExcelVo.getOwner()) ||
								"白雪".equals(writeExcelVo.getOwner()) ||
								"于昊天".equals(writeExcelVo.getOwner()) ||
								"巩飞跃".equals(writeExcelVo.getOwner()) ||
								"王坚文".equals(writeExcelVo.getOwner()) ||
								"潘松伟".equals(writeExcelVo.getOwner())
				) {
					row.createCell(2).setCellValue("核算");
				} else {
					row.createCell(2).setCellValue("核心");
				}
				row.createCell(3).setCellValue(writeExcelVo.getCodePath());
				num++;
			}
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
		} catch (IOException e) {
			logger.debug("errorStack:", e);
			e.printStackTrace();
		} finally {
			if(workbook!=null){
				try {
					workbook.close();
				} catch (IOException e) {
					logger.debug("errorStack:", e);
					e.printStackTrace();
				}
			}
			if(fileOutputStream!=null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					logger.debug("errorStack:", e);
					e.printStackTrace();
				}
			}
		}
	}
}
