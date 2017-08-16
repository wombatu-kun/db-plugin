package recreator.services;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import recreator.forms.DbConfigsForm;

public class UiService {
	private static final int FADEOUT_TIME = 3000;

	private Project project;

	public UiService(Project project) { this.project = project; }

	public Project getProject() { return project; }

	public void showErrorDialog(String msg) {
		Messages.showMessageDialog(project, msg, "Error", Messages.getErrorIcon());
	}

	public void showInfoDialog(String msg) { Messages.showMessageDialog(project, msg, "Information", Messages.getInformationIcon()); }

	public void showSuccessMsg(String msg) {
		StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
		JBPopupFactory.getInstance()
				.createHtmlTextBalloonBuilder(msg, MessageType.INFO, null)
				.setFadeoutTime(FADEOUT_TIME).createBalloon()
				.show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);
	}

	public void showWarningMsg(String msg) {
		StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
		JBPopupFactory.getInstance()
				.createHtmlTextBalloonBuilder(msg, MessageType.WARNING, null)
				.setFadeoutTime(FADEOUT_TIME).createBalloon()
				.show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);
	}

	public boolean showConfigurationDialog(ConfigService configService) {
		final DbConfigsForm form = new DbConfigsForm(project, configService.getConfigMap());

		DialogBuilder builder = new DialogBuilder(project);
		builder.setCenterPanel(form.getRoot());
		builder.setTitle("Database Configurations");
		builder.removeAllActions();
		builder.addOkAction();

		return builder.show() == DialogWrapper.OK_EXIT_CODE;
	}
}
