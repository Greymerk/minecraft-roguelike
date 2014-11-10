package greymerk.roguelike.catacomb.tower;

import greymerk.roguelike.catacomb.theme.ITheme;

import java.util.Random;

import net.minecraft.world.World;

public interface ITower {

	public void generate(World world, Random rand, ITheme theme, int x, int y, int z);
	
}
