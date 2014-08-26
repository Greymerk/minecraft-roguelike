package greymerk.roguelike.catacomb.tower;

import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;

import java.util.Random;

import net.minecraft.world.World;

public interface ITower {

	public void generate(World world, Random rand, CatacombLevelSettings settings, int x, int y, int z);
	
}
