package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentChest extends SegmentBase {

	
	@Override
	protected void genWall(World world, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = theme.getSecondaryStair();
		
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);		
		
		start = new Coord(x, y, z);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		start.add(dir, 1);
		end.add(dir, 1);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryWall(), true, true);
		
		for(Cardinal d : orth){
			cursor = new Coord(x, y, z);
			cursor.add(Cardinal.UP, 2);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true);
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(d), false);
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
		}
	
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 1);
		cursor.add(dir, 3);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true);
		WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
		
		Coord shelf = new Coord(x, y, z);
		shelf.add(dir, 3);
		Coord below = new Coord(shelf);
		shelf.add(Cardinal.UP, 1);
		
		if(world.isAirBlock(below.getBlockPos())) return;	
		
		boolean trapped = Dungeon.getLevel(y) == 3 && rand.nextInt(3) == 0;
		TreasureChest.generate(world, rand, level.getSettings(), shelf, Dungeon.getLevel(y), trapped);
		if(trapped){
			WorldGenPrimitive.setBlock(world, shelf.getX(), shelf.getY() - 2, shelf.getZ(), Blocks.tnt);
			if(rand.nextBoolean()) WorldGenPrimitive.setBlock(world, shelf.getX(), shelf.getY() - 3, shelf.getZ(), Blocks.tnt);
		}
	}
}
