package recreator.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import recreator.services.ConfigService;
import recreator.services.DbService;
import recreator.services.UiService;
import recreator.state.DbConfig;

public class MainAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent e) {
		UiService uiService = new UiService(e.getData(PlatformDataKeys.PROJECT));
		DbService dbService = new DbService(uiService);
		ConfigService configService = new ConfigService(uiService);

		boolean isOk = true;
		while ((!configService.isConfigOk() || !dbService.isDriverOk()) && isOk) { //get config map
			isOk = uiService.showConfigurationDialog(configService);
		}
		if (isOk) {
			String confName = configService.selectConfig(); //choose DB to recreate
			if (confName==null || confName.trim().equals("")) return;
			DbConfig conf = configService.getConfigByKey(confName);
			if (conf != null) {
				if (dbService.doCreateDb(conf) && dbService.doInitDb(conf)) { //drop and create db, reconnect to it and run init
					uiService.showSuccessMsg("Congratulations! <b>" + conf.getDbName() + "</b> was recreated successfully.");
				}
			} else {
				uiService.showErrorDialog(confName + ": config not found. Check plugin configuration.");
			}
		}
	}

}
