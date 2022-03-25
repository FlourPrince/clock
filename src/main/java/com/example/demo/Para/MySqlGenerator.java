package com.example.demo.Para;

import com.dcits.ensemble.tools.param.config.Context;
import com.dcits.ensemble.tools.param.db.DBColumnInfo;
import com.dcits.ensemble.tools.param.db.DbTableData;
import com.dcits.ensemble.tools.param.generator.IParamGenerator;
import com.dcits.ensemble.tools.param.generator.SqlGenerator;
import com.dcits.ensemble.tools.util.FileUtility;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * The <code>com.example.demo.Para.MySqlGenerator</code> class have to be described
 * <p>
 * The <code>MySqlGenerator</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/3/17 14:42
 * @see
 * @since 1.0
 */
public class MySqlGenerator implements IParamGenerator, Runnable {
	private static final Logger logger = LoggerFactory.getLogger(SqlGenerator.class);
	private Context context;
	private DbTableData dbTableData;
	private String NEXT_LINE;
	private Template template;

	public MySqlGenerator(Context context, DbTableData dbTableData) {
		this.context = context;
		this.dbTableData = dbTableData;
		String format = context.getFileConfig().getFormat();
		if ("UNIX".equalsIgnoreCase(format)) {
			this.NEXT_LINE = "\n";
		} else if ("PC".equalsIgnoreCase(format)) {
			this.NEXT_LINE = "\r\n";
		} else {
			this.NEXT_LINE = "";
		}

	}

	public MySqlGenerator(Context context, Template template, DbTableData dbTableData) {
		this.context = context;
		this.dbTableData = dbTableData;
		this.template = template;
		String format = context.getFileConfig().getFormat();
		if ("UNIX".equalsIgnoreCase(format)) {
			this.NEXT_LINE = "\n";
		} else if ("PC".equalsIgnoreCase(format)) {
			this.NEXT_LINE = "\r\n";
		} else {
			this.NEXT_LINE = "";
		}

	}

	@Override
	public void run() {
		this.write();
	}

	@Override
	public void write() {
		String tableName = this.dbTableData.getDbTable().getTableName().toUpperCase();
		String targetDir = this.context.getFileConfig().getTargetDir();
		String encoding = this.context.getFileConfig().getEncoding();
		String moduleId = tableName.split("_")[0];
		String fileName = tableName + ".sql";
		if ("PRN".equals(moduleId)) {
			moduleId = "PRNT";
		}

		List lists = new ArrayList();
		List<Map<String, Object>> datas = this.dbTableData.getDatas();
		Iterator var9 = datas.iterator();

		while(var9.hasNext()) {
			Map data = (Map)var9.next();
			List dataVals = new ArrayList();

			String value;
			for(Iterator var12 = this.dbTableData.getColumns().iterator(); var12.hasNext(); dataVals.add(value)) {
				DBColumnInfo dbColumnInfo = (DBColumnInfo)var12.next();
				value = "" + data.get(dbColumnInfo.getColumnName());
				if (!value.equalsIgnoreCase("NULL") && value.contains("'")) {
					value = value.replaceAll("'", "''");
				}
			}

			lists.add(dataVals);
		}

		HashMap outMap = new HashMap();
		outMap.put("datas", lists);
		outMap.put("nextline", this.NEXT_LINE);
		outMap.put("tablename", tableName);
		outMap.put("columns", this.dbTableData.getColumns());

		try {
			String paramDir = targetDir  + "\\";
			FileUtility.createDir(paramDir);
			FileWriterWithEncoding fw = new FileWriterWithEncoding(paramDir + fileName, encoding);
			this.template.process(outMap, fw);
			fw.close();
		} catch (TemplateException var14) {
			var14.printStackTrace();
		} catch (IOException var15) {
			var15.printStackTrace();
		}

	}
}
