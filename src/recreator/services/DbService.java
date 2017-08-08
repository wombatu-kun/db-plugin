package recreator.services;


import recreator.state.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbService {

	private UiService uiService;

	public DbService(UiService uiService) {	this.uiService = uiService;	}

	public boolean isDriverOk() {
		try {
			Class.forName("org.postgresql.Driver");
			return true;
		} catch (ClassNotFoundException e1) {
			uiService.showErrorDialog(e1.getMessage());
			return false;
		}
	}

	public boolean doCreateDb(DbConfig conf) {
		try (Connection conn = DriverManager.getConnection(conf.getServerUrl() + "/postgres", conf.getUserName(), conf.getUserPass());
			 Statement stmt = conn.createStatement()) {
			String sql;
			try {
				sql = "DROP DATABASE " + conf.getDbName();
				stmt.executeUpdate(sql);
			} catch (SQLException e1) {
				if (e1.getErrorCode()!=0){
					throw e1;
				} else {
					uiService.showWarningMsg("Not exists: " + conf.getServerUrl() + "/" + conf.getDbName());
				}
			}
			sql = "CREATE DATABASE " + conf.getDbName() + " WITH OWNER " + conf.getDbOwner();
			stmt.executeUpdate(sql);
		} catch (SQLException e1) {
			uiService.showErrorDialog(String.valueOf(e1.getErrorCode()) + " - " + e1.getMessage());
			return false;
		}
		return true;
	}

	public boolean doInitDb(DbConfig conf) {
		if (conf.getInitScript()==null || conf.getInitScript().trim().equals("")){
			return true;
		}
		try (Connection conn = DriverManager.getConnection(conf.getServerUrl() + "/" + conf.getDbName(), conf.getUserName(), conf.getUserPass());
			 Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(conf.getInitScript());
			return true;
		} catch (SQLException e1) {
			uiService.showErrorDialog(String.valueOf(e1.getErrorCode()) + " - " + e1.getMessage());
			return false;
		}
	}

	public boolean doDropDb(DbConfig conf) {
		try (Connection conn = DriverManager.getConnection(conf.getServerUrl() + "/postgres", conf.getUserName(), conf.getUserPass());
			 Statement stmt = conn.createStatement()) {
			String sql = "DROP DATABASE " + conf.getDbName();
			stmt.executeUpdate(sql);
		} catch (SQLException e1) {
			if (e1.getErrorCode()!=0){
				uiService.showErrorDialog(String.valueOf(e1.getErrorCode()) + " - " + e1.getMessage());
				return false;
			} else {
				uiService.showWarningMsg("Not exists: " + conf.getServerUrl() + "/" + conf.getDbName());
			}
		}
		return true;
	}

}

