package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonReward implements IDungeon {

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, int x, int y, int z) {

		ITheme theme = settings.getTheme();
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y, z - 7, x + 7, y + 5, z + 7, new MetaBlock(Blocks.air), true, true);
		WorldGenPrimitive.fillRectHollow(world, rand, x - 8, y - 1, z - 8, x + 8, y + 6, z + 8, theme.getPrimaryWall(), false, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 4, z - 1, x + 1, y + 5, z + 1, theme.getPrimaryWall());
		
		
		Coord cursor;
		Coord start;
		Coord end;
		
		MetaBlock stair = theme.getPrimaryStair();
		
		for(Cardinal dir : Cardinal.directions){
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				cursor = new Coord(x, y, z);
				cursor.add(dir, 7);
				cursor.add(orth, 2);
				start = new Coord(cursor);
				end = new Coord(start);
				end.add(Cardinal.UP, 5);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
				cursor.add(Cardinal.reverse(dir));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(world, cursor);
				cursor.add(Cardinal.UP, 2);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				start = new Coord(cursor);
				end = new Coord(start);
				end.add(Cardinal.UP, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
				cursor.add(Cardinal.reverse(dir));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				start = new Coord(cursor);
				end = new Coord(start);
				end.add(Cardinal.UP);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
				cursor.add(Cardinal.UP);
				cursor.add(Cardinal.reverse(dir));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
				
				start = new Coord(x, y, z);
				start.add(dir, 7);
				start.add(Cardinal.UP, 3);
				end = new Coord(start);
				end.add(Cardinal.UP, 2);
				end.add(orth);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
				start.add(Cardinal.reverse(dir));
				start.add(Cardinal.UP);
				end.add(Cardinal.reverse(dir));
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
				start.add(Cardinal.reverse(dir));
				start.add(Cardinal.UP);
				end.add(Cardinal.reverse(dir));
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 8);
				cursor.add(Cardinal.UP, 2);
				cursor.add(orth);
				WorldGenPrimitive.setBlock(world, rand, cursor, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true), true, false);
				cursor.add(Cardinal.reverse(dir));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir), 2);
				WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);
				
				start = new Coord(x, y, z);
				start.add(dir, 7);
				start.add(orth, 3);
				start.add(Cardinal.UP, 3);
				end = new Coord(start);
				end.add(Cardinal.UP, 2);
				end.add(orth, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryPillar(), true, true);
				
				start.add(Cardinal.reverse(dir));
				start.add(Cardinal.UP);
				end.add(Cardinal.reverse(dir));
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryPillar(), true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 7);
				cursor.add(orth, 3);
				WorldGenPrimitive.blockOrientation(stair, orth, false).setBlock(world, cursor);
				cursor.add(orth, 2);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), false).setBlock(world, cursor);
				cursor.add(Cardinal.UP, 2);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(orth), 2);
				WorldGenPrimitive.blockOrientation(stair, orth, true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, orth, true).setBlock(world, cursor);
				cursor.add(orth, 2);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.UP);
				end = new Coord(cursor);
				end.add(Cardinal.reverse(orth), 2);
				WorldGenPrimitive.fillRectSolid(world, rand, cursor, end, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true), true, true);
				cursor.add(Cardinal.UP);
				end.add(Cardinal.UP);
				WorldGenPrimitive.fillRectSolid(world, rand, cursor, end, theme.getPrimaryWall(), true, true);
				end.add(Cardinal.reverse(dir));
				WorldGenPrimitive.blockOrientation(stair, orth, true).setBlock(world, cursor);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 7);
				cursor.add(orth, 4);
				cursor.add(Cardinal.DOWN);
				WorldGenPrimitive.setBlock(world, rand, cursor, new MetaBlock(Blocks.glowstone), true, true);
				
			}
			
			Cardinal o = Cardinal.getOrthogonal(dir)[0];
			
			start = new Coord(x, y, z);
			start.add(dir, 6);
			start.add(o, 6);
			end = new Coord(start);
			end.add(dir);
			end.add(o);
			end.add(Cardinal.UP, 5);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryPillar(), true, true);
			
			cursor = new Coord(x, y, z);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getPrimaryWall(), true, true);
			cursor.add(dir);
			WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
			cursor.add(o);
			WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
			cursor.add(Cardinal.UP, 4);
			WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);
			cursor.add(Cardinal.reverse(o));
			WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);
			
		}
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 4);
		WorldGenPrimitive.setBlock(world, rand, cursor, new MetaBlock(Blocks.glowstone), true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP);
		TreasureChest.generate(world, rand, settings.getLoot(), cursor, TreasureChest.REWARD);
		
		return true;
	}

	@Override
	public int getSize() {
		return 10;
	}

	@Override
	public boolean validLocation(World world, Cardinal dir, int x, int y, int z) {
		return false;
	}

}
