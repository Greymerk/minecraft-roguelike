package greymerk.roguelike.dungeon.settings;

import java.util.Collection;

public interface ISettingsContainer {

  Collection<DungeonSettings> getByNamespace(String namespace);

  Collection<DungeonSettings> getBuiltinSettings();

  Collection<DungeonSettings> getCustomSettings();

  DungeonSettings get(SettingIdentifier id);

  boolean contains(SettingIdentifier id);

}
