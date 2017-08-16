package recreator.services;


import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.ui.Messages;
import recreator.state.DbConfig;
import recreator.state.PluginProperties;

import java.util.HashMap;
import java.util.Map;

public class ConfigService {
	public static final String DEFAULT_DB_NAME = "EDIT_DB_NAME_HERE";
	private static final String DEFAULT_DB_KEY = "EDIT_CONFIG_ALIAS_HERE";

	private Map<String,DbConfig> configMap;

	public ConfigService(UiService uiService) {
		PluginProperties props = ServiceManager.getService(uiService.getProject(), PluginProperties.class);
		configMap = props.state;
		if (configMap==null || configMap.isEmpty()) {
			configMap = new HashMap<>();
			configMap.put(DEFAULT_DB_KEY, createExampleConfig());
			props.state = configMap;
		}
	}

	public boolean isConfigOk() {
		return !configMap.values().stream().allMatch(c -> c.getDbName().equals(DEFAULT_DB_NAME));
	}

	public String selectConfig(){
		String[] choices = configMap.keySet().toArray(new String[1]);
		String defaultChoice = configMap.entrySet().stream().filter(c -> c.getValue().getIsDefault()).findAny().map(me -> me.getKey()).orElse("");
		return Messages.showEditableChooseDialog("Choose db:", "Database ReCreation", Messages.getQuestionIcon(),
				choices, defaultChoice, null);
	}

	public DbConfig getConfigByKey(String confName) {
		DbConfig conf = null;
		if (confName!=null && !confName.trim().equals("")) {
			conf = configMap.get(confName);
		}
		return conf;
	}

	public static DbConfig createExampleConfig() {
		DbConfig config = new DbConfig();
		config.setDbName(DEFAULT_DB_NAME);
		config.setServerUrl("jdbc:postgresql://localhost:5432");
		config.setIsDefault(true);
		config.setUserName("postgres");
		config.setUserPass("cdek");
		config.setDbOwner("cdek");
		config.setInitScript("create extension pg_trgm;");
		return config;
	}

	public Map<String, DbConfig> getConfigMap() { return configMap; }

	public void setConfigMap(Map<String, DbConfig> configMap) {	this.configMap = configMap; }
}
