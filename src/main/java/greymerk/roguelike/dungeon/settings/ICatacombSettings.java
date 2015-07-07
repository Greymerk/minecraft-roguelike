package greymerk.roguelike.dungeon.settings;

import greymerk.roguelike.worldgen.Coord;
import net.minecraft.world.World;


public interface ICatacombSettings {

	public boolean isValid(World world, Coord pos);
	
	public CatacombLevelSettings getLevelSettings(int level);
	
	public CatacombTowerSettings getTower();
	
	public int getNumLevels();
}
