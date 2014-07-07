package greymerk.roguelike.catacomb.dungeon;

import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;

import java.util.Random;

import net.minecraft.world.World;

public abstract class DungeonBase implements IDungeon{

	@Override
	public abstract boolean generate(World world, Random rand, ITheme theme, int x, int y, int z);
	
	public abstract int getSize();
	
	public boolean validLocation(World world, Cardinal dir, int x, int y, int z){
		return false;
	}
}
