package greymerk.roguelike.dungeon.settings;

import java.util.List;

import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;


public interface ISettings {

	public boolean isValid(IWorldEditor editor, Coord pos);
	
	public LevelSettings getLevelSettings(int level);
	
	public TowerSettings getTower();
	
	public LootRuleManager getLootRules();
	
	public int getNumLevels();
	
	public List<SettingsType> getOverrides();
}
