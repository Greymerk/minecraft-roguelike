package greymerk.roguelike.dungeon.settings;

import java.util.List;
import java.util.Random;
import java.util.Set;

import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;


public interface ISettings {

	public boolean isValid(IWorldEditor editor, Coord pos);
	
	public List<SettingIdentifier> getInherits();
	
	public boolean isExclusive();
	
	public LevelSettings getLevelSettings(int level);
	
	public TowerSettings getTower();
	
	public void processLoot(Random rand, TreasureManager treasure);
		
	public int getNumLevels();
	
	public Set<SettingsType> getOverrides();
}
