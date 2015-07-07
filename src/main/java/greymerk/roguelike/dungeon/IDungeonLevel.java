package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.settings.LevelSettings;

public interface IDungeonLevel {

	public void generate(DungeonNode oldEnd);
	
	public DungeonNode getEnd();
	
	public LevelSettings getSettings();
	
}
