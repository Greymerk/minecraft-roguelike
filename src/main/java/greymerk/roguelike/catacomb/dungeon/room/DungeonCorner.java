package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonCorner extends DungeonBase {

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory blocks = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// fill air inside
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y, z - 2, x + 2, y + 3, z + 2, air);
		
		// shell
		WorldGenPrimitive.fillRectHollow(world, rand, x - 3, y - 1, z - 3, x + 3, y + 4, z + 3, blocks, false, true);
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, y - 1, z - 3, x + 3, y - 1, z + 3, theme.getPrimaryFloor(), false, true);
		
		Coord start;
		Coord end;
		Coord cursor;
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 4);
		air.setBlock(world, cursor);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, blocks, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(Cardinal.getOrthogonal(dir)[0], 2);
			start = new Coord(cursor);
			cursor.add(Cardinal.UP, 2);
			end = new Coord(cursor);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, blocks, true, true);
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 1);
			cursor.add(Cardinal.UP, 4);
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(dir), true));
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
			
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				cursor = new Coord(x, y, z);
				cursor.add(dir, 2);
				cursor.add(orth, 1);
				cursor.add(Cardinal.UP, 3);
				stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
				WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
			}
		}
		
		return true;
	}
	
	public int getSize(){
		return 4;
	}

}
