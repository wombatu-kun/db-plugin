package recreator.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import recreator.services.ConfigService;
import recreator.services.UiService;

public class ConfigAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent e) {
		Project project = e.getData(PlatformDataKeys.PROJECT);
		UiService uiService = new UiService(project);
		ConfigService configService = new ConfigService(uiService);
		uiService.showConfigurationDialog(configService);
	}

}
