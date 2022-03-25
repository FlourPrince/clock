package com.example.demo.Git;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * The <code>com.example.demo.Git.ApplicationStartQuartzJobListner</code> class have to be described
 * <p>
 * The <code>ApplicationStartQuartzJobListner</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/3/2 9:57
 * @see
 * @since 1.0
 */
@Component
public class ApplicationStartQuartzJobListner implements ApplicationRunner {

	@Autowired
//	private CodeListMain codeListMain;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		System.out.println("生成");
		//codeListMain.createCodeList();
	}


}
