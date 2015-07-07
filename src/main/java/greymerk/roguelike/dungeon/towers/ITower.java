package greymerk.roguelike.dungeon.towers;

import greymerk.roguelike.dungeon.theme.ITheme;

import java.util.Random;

import net.minecraft.world.World;

public interface ITower {

	public void generate(World world, Random rand, ITheme theme, int x, int y, int z);
	
}
