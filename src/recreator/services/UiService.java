package recreator.services;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

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
}
