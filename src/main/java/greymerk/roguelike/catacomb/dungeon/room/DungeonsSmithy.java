package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureChestEmpty;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Furnace;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.redstone.Hopper;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsSmithy extends DungeonBase {

	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();

		Coord cursor;
		
		Cardinal dir = entrances[0];
		
		clearBoxes(world, rand, theme, dir, origin);
		
		cursor = new Coord(origin);
		cursor.add(dir, 6);
		sideRoom(world, rand, settings, dir, cursor);
		anvilRoom(world, rand, settings, dir, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 6);
		sideRoom(world, rand, settings, dir, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 9);
		MetaBlock air = new MetaBlock(Blocks.air);
		air.setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		air.setBlock(world, cursor);
		
		mainRoom(world, rand, settings, dir, origin);
		
		
		return true;
	}
	
	private void sideRoom(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord origin){
		
		ITheme theme = settings.getTheme();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock stair = theme.getPrimaryStair();
		
		for(Cardinal side : orth){
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			start.add(side, 2);
			start.add(Cardinal.reverse(dir), 2);
			end.add(side, 3);
			end.add(dir, 2);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
			
			start.add(dir);
			end = new Coord(start);
			end.add(dir, 2);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(side), true), true, true);
			
			for(Cardinal o : Cardinal.getOrthogonal(side)){
				start = new Coord(origin);
				start.add(side, 3);
				start.add(o, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
				
				cursor = new Coord(end);
				cursor.add(Cardinal.reverse(side));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(side), true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				cursor.add(Cardinal.reverse(side));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(side), true).setBlock(world, cursor);
				
				cursor = new Coord(end);
				cursor.add(Cardinal.reverse(o));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
			}
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		overheadLight(world, rand, settings, cursor);
	}
	
	private void clearBoxes(World world, Random rand, ITheme theme, Cardinal dir, Coord origin){
		
		IBlockFactory wall = theme.getPrimaryWall();
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		// end room
		cursor = new Coord(origin);
		cursor.add(dir, 6);
		
		start = new Coord(cursor);
		start.add(Cardinal.DOWN);
		start.add(dir, 3);
		start.add(orth[0], 4);
		
		end = new Coord(cursor);
		end.add(Cardinal.UP, 4);
		end.add(Cardinal.reverse(dir), 3);
		end.add(orth[1], 4);
		
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, wall, true, true);
		
		// entrance
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 6);
		
		start = new Coord(cursor);
		start.add(Cardinal.DOWN);
		start.add(dir, 3);
		start.add(orth[0], 4);
		
		end = new Coord(cursor);
		end.add(Cardinal.UP, 4);
		end.add(Cardinal.reverse(dir), 3);
		end.add(orth[1], 4);
		
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, wall, true, true);
		
		// middle
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		start.add(orth[0], 6);
		start.add(Cardinal.reverse(dir), 4);
		
		end = new Coord(origin);
		end.add(Cardinal.UP, 6);
		end.add(orth[1], 6);
		end.add(dir, 4);
		
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, wall, false, true);
		
	}
	
	private void mainRoom(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord origin){
		
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(dir, 3);
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		start.add(orth[0], 5);
		end.add(orth[1], 5);
		end.add(Cardinal.UP);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
		start.add(Cardinal.reverse(dir), 6);
		end.add(Cardinal.reverse(dir), 6);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
		
		for(Cardinal side : orth){
			for(Cardinal o : Cardinal.getOrthogonal(side)){
				cursor = new Coord(origin);
				cursor.add(side, 2);
				cursor.add(o, 3);
				mainPillar(world, rand, theme, o, cursor);
				cursor.add(side, 3);
				mainPillar(world, rand, theme, o, cursor);
			}
		}
		
		cursor = new Coord(origin);
		smelterSide(world, rand, settings, orth[0], origin);
		fireplace(world, rand, settings, orth[1], origin);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 6);
		overheadLight(world, rand, settings, cursor);
		
	}
	
	private void mainPillar(World world, Random rand, ITheme theme, Cardinal dir, Coord origin){
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock stair = theme.getPrimaryStair();
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		end.add(Cardinal.UP, 3);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
		cursor = new Coord(end);
		cursor.add(orth[0]);
		WorldGenPrimitive.blockOrientation(stair, orth[0], true).setBlock(world, cursor);
		cursor = new Coord(end);
		cursor.add(orth[1]);
		WorldGenPrimitive.blockOrientation(stair, orth[1], true).setBlock(world, cursor);
		cursor = new Coord(end);
		cursor.add(Cardinal.reverse(dir));
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, rand, cursor, wall, true, true);
		cursor.add(Cardinal.reverse(dir));
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.UP);
		start = new Coord(cursor);
		end = new Coord(cursor);
		end.add(dir, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
		cursor = new Coord(end);
		cursor.add(orth[0]);
		WorldGenPrimitive.blockOrientation(stair, orth[0], true).setBlock(world, cursor);
		cursor = new Coord(end);
		cursor.add(orth[1]);
		WorldGenPrimitive.blockOrientation(stair, orth[1], true).setBlock(world, cursor);
	}
	
	
	private void smelterSide(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord origin){
		
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		Coord cursor;
		Coord start;
		Coord end;

		start = new Coord(origin);
		start.add(dir, 5);
		end = new Coord(start);
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
		start.add(Cardinal.reverse(dir));
		end.add(Cardinal.reverse(dir));
		MetaBlock stair = theme.getPrimaryStair();
		stair = WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, true);
		
		
		for(Cardinal o : orth){
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(o);
			smelter(world, rand, settings, dir, cursor);
			
			cursor.add(o, 2);
			WorldGenPrimitive.setBlock(world, rand, cursor, wall, true, true);
			cursor.add(dir);
			WorldGenPrimitive.setBlock(world, rand, cursor, wall, true, true);
		}
	}
	
	private void smelter(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord origin){
		Coord cursor;
		ITreasureChest input = new TreasureChestEmpty();
		input.generate(world, rand, settings.getLoot(), origin);
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		cursor.add(Cardinal.UP, 2);
		ITreasureChest output = new TreasureChestEmpty();
		output.generate(world, rand, settings.getLoot(), cursor);
		cursor.add(Cardinal.UP);
		cursor.add(Cardinal.reverse(dir));
		ITreasureChest fuel = new TreasureChestEmpty();
		fuel.generate(world, rand, settings.getLoot(), cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		cursor.add(dir);
		Furnace.generate(world, Cardinal.reverse(dir), cursor);
		
		cursor = new Coord(origin);
		cursor.add(dir);
		Hopper.generate(world, Cardinal.reverse(dir), cursor);
		
		cursor.add(dir);
		cursor.add(Cardinal.UP);
		Hopper.generate(world, Cardinal.reverse(dir), cursor);
		
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.UP);
		Hopper.generate(world, Cardinal.DOWN, cursor);
	}
	
	private void fireplace(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord origin){
		
		MetaBlock stair = new MetaBlock(Blocks.brick_stairs);
		MetaBlock brick = new MetaBlock(Blocks.brick_block);
		MetaBlock brickSlab = new MetaBlock(Blocks.stone_slab, 4);
		MetaBlock bars = new MetaBlock(Blocks.iron_bars);
		MetaBlock air = new MetaBlock(Blocks.air);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(dir, 4);
		end = new Coord(start);
		start.add(Cardinal.DOWN);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(dir, 2);
		end.add(Cardinal.UP, 5);
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, brick, true, true);
		
		start = new Coord(origin);
		start.add(dir, 5);
		end = new Coord(start);
		end.add(Cardinal.UP, 5);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 4);
		air.setBlock(world, cursor);
		
		for(Cardinal side : orth){
			
			cursor = new Coord(origin);
			cursor.add(dir, 4);
			cursor.add(side);
			
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(side), false).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(side), true).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, side, false).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			bars.setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			bars.setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, side, true).setBlock(world, cursor);
					
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(side);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(world, cursor);
			cursor.add(side);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(world, cursor);
			cursor.add(side);
			brick.setBlock(world, cursor);
			cursor.add(dir);
			brick.setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(side), false).setBlock(world, cursor);
			cursor.add(Cardinal.reverse(dir));
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(side), false).setBlock(world, cursor);
			
			cursor = new Coord(origin);
			cursor.add(dir, 4);
			cursor.add(side, 2);
			brick.setBlock(world, cursor);
			cursor.add(dir);
			brick.setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			brick.setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(world, cursor);
			cursor.add(Cardinal.DOWN);
			cursor.add(Cardinal.reverse(dir));
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(world, cursor);
			
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(Cardinal.UP, 5);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
			
		}
		
		MetaBlock netherrack = new MetaBlock(Blocks.netherrack);
		MetaBlock fire = new MetaBlock(Blocks.fire);
		
		start = new Coord(origin);
		start.add(dir, 5);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, netherrack, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, fire, true, true);
		
		cursor = new Coord(origin);
		cursor.add(dir, 3);
		brickSlab.setBlock(world, cursor);
		cursor.add(dir);
		brickSlab.setBlock(world, cursor);
		
	}
	
	private void anvilRoom(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord origin){
		
		ITheme theme = settings.getTheme();
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory wall = theme.getPrimaryWall();
		
		
		Coord cursor;
		Coord start;
		Coord end;
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		
		MetaBlock anvil = new MetaBlock(Blocks.anvil);
		cursor = new Coord(origin);
		cursor.add(dir);
		anvil.setBlock(world, cursor);
		
		start = new Coord(origin);
		start.add(orth[1], 2);
		end = new Coord(start);
		start.add(dir, 2);
		end.add(Cardinal.reverse(dir), 2);
		WorldGenPrimitive.blockOrientation(stair, orth[0], false);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, true);
		
		cursor = new Coord(origin);
		cursor.add(orth[1], 3);
		WorldGenPrimitive.setBlock(world, rand, cursor, wall, true, true);
		cursor.add(dir);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.flowing_water);
		cursor.add(Cardinal.reverse(dir), 2);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.flowing_lava);
		
		cursor = new Coord(origin);
		cursor.add(orth[0], 3);
		start = new Coord(cursor);
		end = new Coord(start);
		start.add(dir);
		end.add(Cardinal.reverse(dir));
		WorldGenPrimitive.blockOrientation(stair, orth[1], true);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, true);
		cursor.add(Cardinal.UP);
		TreasureChest.generate(world, rand, settings.getLoot(), cursor, TreasureChest.SMITH);
		
		cursor = new Coord(origin);
	}
	
	
	private void overheadLight(World world, Random rand, CatacombLevelSettings settings, Coord origin){
		
		ITheme theme = settings.getTheme();
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord cursor;
		
		WorldGenPrimitive.setBlock(world, origin, Blocks.air);
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
			cursor.add(Cardinal.getOrthogonal(dir)[0]);
			stair.setBlock(world, cursor);
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.lit_redstone_lamp);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.redstone_block);
	}
	
	public int getSize(){
		return 9;
	}
	
}
