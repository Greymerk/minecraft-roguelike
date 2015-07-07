package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;

public interface IDungeonLevel {

	public void generate(DungeonNode oldEnd);
	
	public DungeonNode getEnd();
	
	public CatacombLevelSettings getSettings();
	
}
