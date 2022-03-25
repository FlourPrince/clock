package com.example.demo.Clock;

/**
 * The <code>com.ensemble.test.Result</code> class have to be described
 * <p>
 * The <code>Result</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/2/16 17:31
 * @see
 * @since 1.0
 */
public class LoadResult {
	String code;
	String message;
	Data data;

	public class Data {
		String token;
		String roleId;
		String userName;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getRoleId() {
			return roleId;
		}

		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
