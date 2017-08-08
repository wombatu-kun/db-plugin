package recreator.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import recreator.forms.DbConfigsForm;
import recreator.state.DbConfig;
import recreator.state.PluginProperties;

import java.util.HashMap;
import java.util.Map;

public class ConfigAction extends AnAction {

	private Project project;
	private Map<String,DbConfig> configMap;

	@Override
	public void actionPerformed(AnActionEvent e) {
		project = e.getData(PlatformDataKeys.PROJECT);
		PluginProperties props = ServiceManager.getService(project, PluginProperties.class);
		configMap = props.state;

		if (configMap==null || configMap.isEmpty()) {
			configMap = new HashMap<>();
			DbConfig config = new DbConfig();
			config.setDbName("module_test");
			config.setServerUrl("jdbc:postgresql://localhost:5432");
			config.setIsDefault(true);
			config.setUserName("postgres");
			config.setUserPass("cdek");
			config.setDbOwner("cdek");
			config.setInitScript("create extension pg_trgm;");
			configMap.put("TEST_LOCAL",config);
			props.state = configMap;
		}
		DialogWrapper form = new DbConfigsForm(project, configMap);
		form.show();
	}


}
