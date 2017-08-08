package recreator.forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import recreator.state.DbConfig;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Map;

public class DbConfigsForm extends DialogWrapper {
	private JList list1;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JTextField textField5;
	private JTextArea textArea1;
	private JCheckBox defaultCheckBox;
	private JPanel jPanel;

	private Map<String, DbConfig> configMap;
	private DbConfig selectedConfig;

	public DbConfigsForm (Project project, Map<String, DbConfig> map) {
		super(project);
		init();
		setTitle("Database Configurations");
		configMap = map;
		list1.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

			}
		});
	}

	@Nullable
	@Override
	protected JComponent createCenterPanel() {
		return jPanel;
	}


}
