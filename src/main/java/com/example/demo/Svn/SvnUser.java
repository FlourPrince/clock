package com.example.demo.Svn;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The <code>com.example.demo.Svn.SvnUser</code> class have to be described
 * <p>
 * The <code>SvnUser</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/3/1 15:55
 * @see
 * @since 1.0
 */
@ConfigurationProperties(prefix = "svnUser")
@Configuration
@Getter
@Setter
public class SvnUser {
	String userName;
	String passWord;
	String url;

	@Override
	public String toString() {
		return "SvnUser{" +
				"userName='" + userName + '\'' +
				", passWord='" + passWord + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
