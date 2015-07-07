package greymerk.roguelike.dungeon.segment;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;

import java.util.Random;

import net.minecraft.world.World;

public interface ISegment {

	public void generate(World world, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z);
	
}
