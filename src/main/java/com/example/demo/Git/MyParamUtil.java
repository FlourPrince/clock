package com.example.demo.Git;

import com.dcits.ensemble.tools.common.ShellType;
import com.dcits.ensemble.tools.param.util.ParamUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The <code>com.example.demo.Git.MyParamUtil</code> class have to be described
 * <p>
 * The <code>MyParamUtil</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/3/23 10:35
 * @see
 * @since 1.0
 */
public class MyParamUtil {
	public MyParamUtil() {
	}

	public static File getConfigFile(String fileName, ShellType shellType) {
		if (shellType.equals(ShellType.BAT)) {
			String path="";
			try {
				 path=ParamUtil.class.getResource("/").toURI().getPath();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			File lib = new File(path);
			File root = lib.getParentFile();
			return new File(root.getAbsolutePath() + "\\conf\\" + fileName);
		} else {
			return shellType.equals(ShellType.IDEA) ? new File(ParamUtil.class.getClassLoader().getResource(fileName).getPath()) : null;
		}
	}
}
