package jp.co.internousecsite.model.form;

import java.io.Serializable;

public class LoginForm implements Serializable {
/*ブラウザから情報を受け取るformクラスは
 * serializableというインターフェイスをimplementsしてください
 */
	
	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
