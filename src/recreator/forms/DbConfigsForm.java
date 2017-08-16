package recreator.forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;
import recreator.services.ConfigService;
import recreator.state.DbConfig;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class DbConfigsForm extends DialogWrapper implements ActionListener {
	private JList listConfigNames;
	private JTextField txtServerUrl;
	private JTextField txtDbName;
	private JTextField txtOwner;
	private JTextField txtUserName;
	private JTextField txtUserPass;
	private JTextArea txtInitScript;
	private JCheckBox chbIsDefault;
	private JPanel root;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnSave;
	private JTextField txtConfigKey;
	private JLabel lblDbName;

	private Map<String,DbConfig> configMap;
	private DbConfig selectedConfig;
	private String selectedConfigKey;

	public DbConfigsForm (Project project, Map<String,DbConfig> map) {
		super(project);
		configMap = map;
		init();

		listConfigNames.addListSelectionListener(e -> {
			if (listConfigNames.getSelectedValue() != null) {
				selectedConfigKey = String.valueOf(listConfigNames.getSelectedValue());
				txtConfigKey.setText(selectedConfigKey);
				selectedConfig = configMap.get(selectedConfigKey);
				setData(selectedConfig);
				if (selectedConfig.getDbName().equals(ConfigService.DEFAULT_DB_NAME)) {
					lblDbName.setForeground(JBColor.RED);
				} else {
					lblDbName.setForeground(JBColor.BLACK);
				}
			}
		});
		listConfigNames.setSelectedIndex(0);

		btnAdd.addActionListener(this);
		btnRemove.addActionListener(this);
		btnSave.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand().trim().toUpperCase()) {
			case "+":
				listConfigNames.clearSelection();
				selectedConfig = ConfigService.createExampleConfig();
				txtConfigKey.setText("NEW_CONFIG");
				setData(selectedConfig);
				break;
			case "-":
				if (listConfigNames.getSelectedValue() != null) {
					selectedConfigKey = String.valueOf(listConfigNames.getSelectedValue());
					configMap.remove(selectedConfigKey);
					listConfigNames.clearSelection();
				}
				updateList(null);
				listConfigNames.setSelectedIndex(0);
				break;
			case "SAVE":
				String newKey = txtConfigKey.getText();
				DbConfig newConfig = new DbConfig();
				getData(newConfig);
				if (configMap.keySet().contains(newKey)) {
					configMap.replace(newKey, newConfig);
				} else {
					if (listConfigNames.getSelectedValue() != null) {
						selectedConfigKey = String.valueOf(listConfigNames.getSelectedValue());
						if (newKey.equals(selectedConfigKey)) {
							configMap.replace(selectedConfigKey, newConfig);
						} else {
							configMap.put(newKey, newConfig);
							configMap.remove(selectedConfigKey);
						}
					} else {
						configMap.put(txtConfigKey.getText(), newConfig);
					}
				}
				updateList(newKey);
				break;
		}
		repaint();
	}

	public JPanel getRoot() { return root; }

	public Map<String, DbConfig> getConfigMap(){ return configMap; }

	@Nullable
	@Override
	protected JComponent createCenterPanel() { return root; }

	private void createUIComponents() {
		listConfigNames = new JBList(configMap.keySet());
	}

	private void setData(DbConfig data) {
		txtServerUrl.setText(data.getServerUrl());
		txtDbName.setText(data.getDbName());
		txtOwner.setText(data.getDbOwner());
		txtUserName.setText(data.getUserName());
		txtUserPass.setText(data.getUserPass());
		txtInitScript.setText(data.getInitScript());
		chbIsDefault.setSelected(data.getIsDefault());
	}

	private void getData(DbConfig data) {
		data.setServerUrl(txtServerUrl.getText());
		data.setDbName(txtDbName.getText());
		data.setDbOwner(txtOwner.getText());
		data.setUserName(txtUserName.getText());
		data.setUserPass(txtUserPass.getText());
		data.setInitScript(txtInitScript.getText());
		data.setIsDefault(chbIsDefault.isSelected());
	}

	private boolean isModified(DbConfig data) {
		if (txtServerUrl.getText() != null ? !txtServerUrl.getText().equals(data.getServerUrl()) : data.getServerUrl() != null)
			return true;
		if (txtDbName.getText() != null ? !txtDbName.getText().equals(data.getDbName()) : data.getDbName() != null)
			return true;
		if (txtOwner.getText() != null ? !txtOwner.getText().equals(data.getDbOwner()) : data.getDbOwner() != null)
			return true;
		if (txtUserName.getText() != null ? !txtUserName.getText().equals(data.getUserName()) : data.getUserName() != null)
			return true;
		if (txtUserPass.getText() != null ? !txtUserPass.getText().equals(data.getUserPass()) : data.getUserPass() != null)
			return true;
		if (txtInitScript.getText() != null ? !txtInitScript.getText().equals(data.getInitScript()) : data.getInitScript() != null)
			return true;
		return chbIsDefault.isSelected() != data.getIsDefault();
	}

	private void updateList(String item) {
		DefaultListModel<String> listModel = new DefaultListModel<>();
		configMap.keySet().forEach(listModel :: addElement);
		listConfigNames.setModel(listModel);
		if (item != null) {
			listConfigNames.setSelectedValue(item, true);
		}
		if (listModel.getSize()>1) {
			btnRemove.setEnabled(true);
		} else {
			btnRemove.setEnabled(false);
		}
	}

}
