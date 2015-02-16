package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.SpawnerSettings;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonPyramidTomb extends DungeonBase{

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		

		ITheme theme = settings.getTheme();
		IBlockFactory pillar = theme.getPrimaryPillar();
		IBlockFactory blocks = theme.getPrimaryWall();
		MetaBlock air = new MetaBlock(Blocks.air);
		

		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.NORTH, 6);
		start.add(Cardinal.WEST, 6);
		end.add(Cardinal.SOUTH, 6);
		end.add(Cardinal.EAST, 6);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.UP, 3);
		start.add(Cardinal.NORTH, 4);
		start.add(Cardinal.WEST, 4);
		end.add(Cardinal.SOUTH, 4);
		end.add(Cardinal.EAST, 4);
		end.add(Cardinal.UP);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.UP, 5);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.WEST, 3);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.EAST, 3);
		end.add(Cardinal.UP);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.UP, 7);
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.WEST, 2);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.EAST, 2);
		end.add(Cardinal.UP);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		// outer walls
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(Cardinal.NORTH, 7);
		start.add(Cardinal.WEST, 7);
		end.add(Cardinal.SOUTH, 7);
		end.add(Cardinal.EAST, 7);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.UP, 3);
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, blocks, false, true);
		
		// floor
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 6);
		start.add(Cardinal.WEST, 6);
		end.add(Cardinal.SOUTH, 6);
		end.add(Cardinal.EAST, 6);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryFloor(), true, true);
		
		// pillars
		
		for (Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			cursor = new Coord(origin);
			cursor.add(dir, 5);
			cursor.add(Cardinal.UP, 3);
			ceilingTiles(world, rand, theme, 9, Cardinal.reverse(dir), cursor);
			
			start = new Coord(origin);
			start.add(dir, 5);
			start.add(orth[0], 5);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
			
			for(Cardinal o : orth){
				start = new Coord(origin);
				start.add(dir, 5);
				start.add(o);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
				
				start.add(o, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
			}
		}
		
		// ceiling top
		start = new Coord(origin);
		start.add(Cardinal.UP, 8);
		end = new Coord(start);
		start.add(Cardinal.NORTH);
		start.add(Cardinal.WEST);
		end.add(Cardinal.SOUTH);
		end.add(Cardinal.EAST);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
		
		sarcophagus(world, rand, settings, entrances[0], origin);
		
		return true;
	}

	private void ceilingTiles(World world, Random rand, ITheme theme, int width, Cardinal dir, Coord origin){
		
		if(width < 1) return;
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		Coord cursor;
		
		Coord start = new Coord(origin);
		Coord end = new Coord(origin);
		start.add(orth[0], width / 2);
		end.add(orth[1], width / 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
		
		for (Cardinal o : orth){
			for (int i = 0; i <= width / 2; ++i){
				if((width / 2) % 2 == 0){
					cursor = new Coord(origin);
					cursor.add(o, i);
					if(i % 2 == 0) tile(world, rand, theme, dir, cursor);
				} else {
					cursor = new Coord(origin);
					cursor.add(o, i);
					if(i % 2 == 1) tile(world, rand, theme, dir, cursor);
				}
			}
		}

		cursor = new Coord(origin);
		cursor.add(dir);
		cursor.add(Cardinal.UP);
		ceilingTiles(world, rand, theme, (width - 2), dir, cursor);
	}
	
	private void tile(World world, Random rand, ITheme theme, Cardinal dir, Coord origin){
		MetaBlock stair = theme.getPrimaryStair();
		WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, origin);
		Coord cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		theme.getPrimaryPillar().setBlock(world, rand, cursor);
	}
	
	
	private void sarcophagus(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord origin){
		SpawnerSettings spawners = settings.getSpawners();
		
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		MetaBlock blocks = new MetaBlock(Blocks.quartz_block);
		
		Coord cursor;
		
		cursor = new Coord(origin);
		blocks.setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		TreasureChest.generate(world, rand, settings, cursor, TreasureChest.ORE);
		cursor.add(Cardinal.UP);
		blocks.setBlock(world, cursor);
		
		for (Cardinal end : Cardinal.getOrthogonal(dir)){
			
			cursor = new Coord(origin);
			cursor.add(end);
			blocks.setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			spawners.generate(world, rand, cursor, Spawner.PIGZOMBIE, 4);
			cursor.add(Cardinal.UP);
			blocks.setBlock(world, cursor);
			
			cursor = new Coord(origin);
			cursor.add(end, 2);
			WorldGenPrimitive.blockOrientation(stair, end, false).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, end, true).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, end, false).setBlock(world, cursor);
			
			for(Cardinal side : Cardinal.getOrthogonal(end)){

				cursor = new Coord(origin);
				cursor.add(side);
				WorldGenPrimitive.blockOrientation(stair, side, false).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, side, true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, side, false).setBlock(world, cursor);
				
				cursor = new Coord(origin);
				cursor.add(side);
				cursor.add(end);
				WorldGenPrimitive.blockOrientation(stair, side, false).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, side, true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, side, false).setBlock(world, cursor);
				
				cursor = new Coord(origin);
				cursor.add(side);
				cursor.add(end, 2);
				WorldGenPrimitive.blockOrientation(stair, side, false).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, side, true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, side, false).setBlock(world, cursor);
			}
		}
	}
	
	@Override
	public int getSize() {
		return 8;
	}

	
	
}
