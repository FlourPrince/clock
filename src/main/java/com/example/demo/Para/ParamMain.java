package com.example.demo.Para;

import com.dcits.ensemble.tools.common.ShellType;

/**
 * The <code>com.example.demo.Para.TestMain</code> class have to be described
 * <p>
 * The <code>TestMain</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/3/16 17:04
 * @see
 * @since 1.0
 */
public class ParamMain {
	private static String TOOLS_NAME = "BusinessParamTools";

	public ParamMain() {
	}


	public static void main(String[] args) {

		(new EnsembleSqlGenerator()).generator(ShellType.IDEA);


	}
}
