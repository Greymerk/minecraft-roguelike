package greymerk.roguelike.dungeon;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface IDungeonLevel {

	public LevelSettings getSettings();
	
	public boolean inRange(Coord pos);
	
	boolean hasNearbyNode(Coord pos);
	
	public LevelLayout getLayout();
	
	public void encase(IWorldEditor editor, Random rand);

	public void generate(ILevelGenerator generator, Coord start);
}
