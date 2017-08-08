package recreator.state;

public class DbConfig {
	private String serverUrl;
	private String dbName;
	private String dbOwner;
	private String userName;
	private String userPass;
	private String initScript;
	private Boolean isDefault;

	public DbConfig() {	}

	public String getServerUrl() { return serverUrl; }

	public void setServerUrl(String serverUrl) { this.serverUrl = serverUrl; }

	public String getDbName() { return dbName; }

	public void setDbName(String dbName) { this.dbName = dbName; }

	public String getDbOwner() { return dbOwner; }

	public void setDbOwner(String dbOwner) { this.dbOwner = dbOwner; }

	public String getUserName() { return userName; }

	public void setUserName(String userName) { this.userName = userName; }

	public String getUserPass() { return userPass; }

	public void setUserPass(String userPass) { this.userPass = userPass; }

	public String getInitScript() { return initScript; }

	public void setInitScript(String initScript) { this.initScript = initScript; }

	public Boolean getIsDefault() { return isDefault; }

	public void setIsDefault(Boolean aDefault) { isDefault = aDefault; }

	@Override
	public String toString() {
		return "recreator.state.DbConfig{" +
				"serverUrl='" + serverUrl + '\'' +
				", dbName='" + dbName + '\'' +
				", dbOwner='" + dbOwner + '\'' +
				", userName='" + userName + '\'' +
				", userPass='" + userPass + '\'' +
				", initScript='" + initScript + '\'' +
				", isDefault=" + isDefault +
				'}';
	}
}
