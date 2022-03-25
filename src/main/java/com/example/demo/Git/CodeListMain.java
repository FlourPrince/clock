package com.example.demo.Git;

import com.dcits.ensemble.tools.common.ShellType;
import com.dcits.ensemble.tools.param.config.XMLConfig;
import com.dcits.ensemble.tools.param.util.ParamUtil;
import com.example.demo.Git.Excel.ReadExcel;
import com.example.demo.Git.Excel.WriteExcel;
import com.example.demo.Git.GitLab.JobCommitMessage;
import com.example.demo.Git.Model.CompareCommitsVo;
import com.example.demo.Git.Model.WriteExcelVo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The <code>com.example.demo.Git.CodeListMain</code> class have to be described
 * <p>
 * The <code>CodeListMain</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/2/25 14:05
 * @see
 * @since 1.0
 */

public class CodeListMain {

	public static void main(String[] args) {
		String fileName = "codeList.xml";
		File configFile = MyParamUtil.getConfigFile(fileName, ShellType.BAT);
		System.out.println("配置文件"+configFile.toString());
		XMLConfig xmlConfig = XMLConfig.getInstance(configFile);
		String sourceDir = xmlConfig.getStringProperty("excelConfig", "[@sourceDir]");
		System.out.println("源文件"+sourceDir);
		String targetDir = xmlConfig.getStringProperty("excelConfig", "[@targetDir]");
		System.out.println("目标文件"+targetDir);
		String hostUrl = xmlConfig.getStringProperty("gitlabApi", "[@hostUrl]");
		String privateToken = xmlConfig.getStringProperty("gitlabApi", "[@privateToken]");
		String projectId = xmlConfig.getStringProperty("gitlabApi", "[@projectId]");
		ReadExcel readExcel = new ReadExcel(sourceDir);
		Map<String, String> map = readExcel.getFeature();
		JobCommitMessage jobCommitMessage = new JobCommitMessage(hostUrl, privateToken);
		WriteExcel writeExcel = new WriteExcel(targetDir);
		//1.读取送测清单，获取需要生成代码清单的内容  版本  负责人
		//2.根据版本 获取比对信息 传入实体类
		List<WriteExcelVo> writeExcelVos = new ArrayList<>();
		for (String feature : map.keySet()) {
			WriteExcelVo writeExcelVo = new WriteExcelVo();
			writeExcelVo.setFeature(feature);
			writeExcelVo.setOwner(map.get(feature));
			StringBuilder codePath = new StringBuilder();
			CompareCommitsVo compareCommitsVo = jobCommitMessage.getCompareBranchesCommitDetail(projectId, "master", feature);
			for (CompareCommitsVo.Diff diff : compareCommitsVo.getDiffs()) {
				codePath.append(diff.getNew_path() + "\n");
			}
			writeExcelVo.setCodePath(codePath.toString());
			writeExcelVos.add(writeExcelVo);
		}
		//3.写入文件
		writeExcel.writeCodeComparePath(writeExcelVos);

	}

}
