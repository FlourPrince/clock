package com.example.demo.Para;

import com.dcits.ensemble.tools.common.ShellType;
import com.dcits.ensemble.tools.param.config.Context;
import com.dcits.ensemble.tools.param.config.GeneratorConfigParser;
import com.dcits.ensemble.tools.param.config.TableModel;
import com.dcits.ensemble.tools.param.db.DbTable;
import com.dcits.ensemble.tools.param.db.DbTableData;
import com.dcits.ensemble.tools.param.db.ExcelMeta;
import com.dcits.ensemble.tools.param.generator.SqlGenerator;
import com.dcits.ensemble.tools.param.util.ParamUtil;
import com.dcits.ensemble.tools.param.util.SqlTemplateUtility;
import com.dcits.ensemble.tools.util.FileUtility;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The <code>com.example.demo.Para.EnsembleSqlGenerator</code> class have to be described
 * <p>
 * The <code>EnsembleSqlGenerator</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/3/17 13:28
 * @see
 * @since 1.0
 */
public class EnsembleSqlGenerator {
	private static final Logger logger = LoggerFactory.getLogger(com.dcits.ensemble.tools.param.generator.EnsembleSqlGenerator.class);
	public String fileName = "parameter.xml";

	public EnsembleSqlGenerator() {
	}

	public EnsembleSqlGenerator(String fileName) {
		this.fileName = fileName;
	}

	public void generator(ShellType shellType) {
		try {
			File configFile = ParamUtil.getConfigFile(this.fileName, shellType);
			if (logger.isDebugEnabled()) {
				logger.debug("config file is in : " + configFile.getAbsoluteFile());
			}

			GeneratorConfigParser gcp = new GeneratorConfigParser();
			Context context = gcp.parseConfig(configFile);
			String targetDir = context.getFileConfig().getTargetDir();
			String encoding = context.getFileConfig().getEncoding();
			MySqlTemplateUtility sqlTemplateUtility = new MySqlTemplateUtility();
			Template template = sqlTemplateUtility.getTemplate("sql.ftl");
			ExecutorService executor = Executors.newFixedThreadPool(context.getPoolConfig().getTotal());
			List tables = context.getTables();
			String excelDir = context.getExcelConfig().getTargetDir();
			DbTable dbTable = new DbTable(context);
			Template templateRunAll = null;
			String runAllFileName = "";
			HashMap outMap = new HashMap();
			String dbVerion = dbTable.getDataBaseInfo().getDatabaseProductName();
			if ("MySQL".equalsIgnoreCase(dbVerion)) {
				runAllFileName = "parameter_mysql_runall.sql";
				templateRunAll = sqlTemplateUtility.getTemplate("mysql-runall.ftl");
			} else {
				runAllFileName = "parameter_oracle_runall.sql";
				templateRunAll = sqlTemplateUtility.getTemplate("oracle-runall.ftl");
			}

			outMap.put("tables", tables);
			InputStream ds = FileUtility.readFileStream(excelDir);
			XSSFWorkbook wb = new XSSFWorkbook(ds);
			for(int i = 0; i < tables.size(); ++i) {
				TableModel tableModel = (TableModel)tables.get(i);
				String tableName = tableModel.getTableName();
				String moduleId = tableName.split("_")[0];
				if ("PRN".equals(moduleId)) {
					moduleId = "PRNT";
				}

				if (ds != null) {
					XSSFSheet sheet_table = wb.getSheet(tableName);
					ExcelMeta t = new ExcelMeta(sheet_table);
					DbTableData dbTableData = t.getDbTableData();
					if (tables.get(i) != null) {
						if (logger.isInfoEnabled()) {
							logger.info(tableName);
						}

						executor.execute(new MySqlGenerator(context, template, dbTableData));
					}

					ds.close();
				}
			}

			try {
				String paramDir = targetDir + "\\";
				FileUtility.createDir(paramDir);
				FileWriterWithEncoding runAll = new FileWriterWithEncoding(paramDir + runAllFileName, encoding);
				templateRunAll.process(outMap, runAll);
				runAll.close();
			} catch (TemplateException var27) {
				var27.printStackTrace();
			} catch (IOException var28) {
				var28.printStackTrace();
			}

			executor.shutdown();

			try {
				executor.awaitTermination(100L, TimeUnit.SECONDS);
			} catch (InterruptedException var26) {
				var26.printStackTrace();
			}

			if (logger.isInfoEnabled()) {
				logger.info("All Sql is Done");
			}
		} catch (Exception var29) {
			var29.printStackTrace();
		}

	}
}
