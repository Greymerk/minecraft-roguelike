package greymerk.roguelike.dungeon.settings;

import java.util.Collection;

public interface ISettingsContainer {

	public Collection<DungeonSettings> getByNamespace(String namespace);
	
	public Collection<DungeonSettings> getBuiltinSettings();
	
	public Collection<DungeonSettings> getCustomSettings();
	
	public DungeonSettings get(SettingIdentifier id);
	
	public boolean contains(SettingIdentifier id);
	
}
