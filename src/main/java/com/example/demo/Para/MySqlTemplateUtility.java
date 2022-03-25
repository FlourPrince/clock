package com.example.demo.Para;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The <code>com.example.demo.Para.MySqlTemplateUtility</code> class have to be described
 * <p>
 * The <code>MySqlTemplateUtility</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/3/17 15:10
 * @see
 * @since 1.0
 */
public class MySqlTemplateUtility {
	/**
	 * 日志处理类
	 */
	private static Logger logger = LoggerFactory.getLogger(MySqlTemplateUtility.class);

	public MySqlTemplateUtility() {
	}

	public Template getTemplate(String name) {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(this.getClass(), "/ftl");
		cfg.setNumberFormat("######");

		try {
			return cfg.getTemplate(name);
		} catch (IOException var4) {
			logger.debug("errorStack:", var4);
			var4.printStackTrace();
			return null;
		}
	}
}
