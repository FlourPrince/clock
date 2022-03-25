package com.example.demo.Clock;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The <code>com.example.demo.main.User</code> class have to be described
 * <p>
 * The <code>User</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/2/18 9:53
 * @see
 * @since 1.0
 */

@ConfigurationProperties(prefix = "clockUser")
@Configuration
@Getter
@Setter
public class ClockUser {
	private  String userName;
	private String passWord;


	@Override
	public String toString() {
		return "User{" +
				"userName='" + userName + '\'' +
				", passWord='" + passWord + '\'' +
				'}';
	}
}
