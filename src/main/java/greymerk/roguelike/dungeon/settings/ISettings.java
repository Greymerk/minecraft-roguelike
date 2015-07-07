package greymerk.roguelike.dungeon.settings;

import greymerk.roguelike.worldgen.Coord;
import net.minecraft.world.World;


public interface ISettings {

	public boolean isValid(World world, Coord pos);
	
	public LevelSettings getLevelSettings(int level);
	
	public TowerSettings getTower();
	
	public int getNumLevels();
}
