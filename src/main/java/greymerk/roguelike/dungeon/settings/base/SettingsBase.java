package greymerk.roguelike.dungeon.settings.base;

import java.util.ArrayList;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;

public class SettingsBase extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "base");

  public SettingsBase() {
    super(ID);
    setInherit(new ArrayList<>());
    getInherit().add(SettingsRooms.ID);
    getInherit().add(SettingsSecrets.ID);
    getInherit().add(SettingsSegments.ID);
    getInherit().add(SettingsLayout.ID);
    getInherit().add(SettingsTheme.ID);
    getInherit().add(SettingsLootRules.ID);
  }
}
