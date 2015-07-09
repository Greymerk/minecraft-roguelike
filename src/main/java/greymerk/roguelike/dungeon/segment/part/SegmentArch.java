package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentArch extends SegmentBase {

	@Override
	protected void genWall(World world, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
			
		MetaBlock stair = theme.getSecondaryStair(); 
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true);
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		Coord cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			cursor = new Coord(x, y, z);
			cursor.add(orth, 1);
			cursor.add(dir, 2);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getPrimaryWall(), true, true);
			cursor.add(Cardinal.reverse(dir), 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);			
		}
	}
}
