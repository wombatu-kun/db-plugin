package recreator.state;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StorageScheme;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@State(
		name = "recreator.state.PluginProperties",
		storages = {
				@Storage(id = "default", file = "$PROJECT_FILE$"),
				@Storage(id = "dir", file = "$PROJECT_CONFIG_DIR$/db-configs.xml", scheme = StorageScheme.DIRECTORY_BASED)
		}
)
public class PluginProperties implements PersistentStateComponent<PluginProperties> {

	public Map<String, DbConfig> state;

	@Nullable
	@Override
	public PluginProperties getState() { return this; }

	@Override
	public void loadState(PluginProperties state) { XmlSerializerUtil.copyBean(state, this); }
}
