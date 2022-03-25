package com.example.demo.Git.Model;

import java.util.List;

/**
 * The <code>com.example.demo.Git.Model.WriteExcelVo</code> class have to be described
 * <p>
 * The <code>WriteExcelVo</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/2/25 11:15
 * @see
 * @since 1.0
 */
public class WriteExcelVo {
	//版本
	private String feature;
	//负责人
	private String owner;
	//业务模块  涉及代码
	private String codePath;

	@Override
	public String toString() {
		return "WriteExcelVo{" +
				"feature='" + feature + '\'' +
				", owner='" + owner + '\'' +
				", codePath='" + codePath + '\'' +
				'}';
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}
}
