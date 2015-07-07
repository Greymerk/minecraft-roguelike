package greymerk.roguelike.dungeon.segment;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;

import java.util.Random;

import net.minecraft.world.World;

public interface ISegment {

	public void generate(World world, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z);
	
}
