package greymerk.roguelike.dungeon.settings;

import java.util.HashMap;

import greymerk.roguelike.treasure.loot.LootRuleManager;

public class SettingsBlank extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "blank");

  public SettingsBlank() {
    super(ID);
    levels = new HashMap<>();
    this.lootRules = new LootRuleManager();
  }
}
