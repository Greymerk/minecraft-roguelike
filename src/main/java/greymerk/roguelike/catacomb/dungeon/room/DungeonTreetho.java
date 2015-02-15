package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonTreetho extends DungeonBase{

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-11, -1, -11));
		end.add(new Coord(11, 8, 11));
		
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, wall, false, true);
		
		MetaBlock birchSlab = new MetaBlock(Blocks.wooden_slab, 10);
		MetaBlock pumpkin = new MetaBlock(Blocks.lit_pumpkin);
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-9, 8, -9));
		end.add(new Coord(9, 8, 9));
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, birchSlab, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, pumpkin, true, true);
		
		cursor = new Coord(origin);
		cursor.add(new Coord(0, 8, 0));
		ceiling(world, rand, settings, cursor);
		
		cursor = new Coord(origin);
		treeFarm(world, rand, settings, cursor, entrances[0]);
		
		Cardinal[] orth = Cardinal.getOrthogonal(entrances[0]);
		for(Cardinal o : orth){
			cursor = new Coord(origin);
			cursor.add(o, 5);
			treeFarm(world, rand, settings, cursor, entrances[0]);
		}
		
		
		return true;
	}
	
	private void treeFarm(World world, Random rand, CatacombLevelSettings settings, Coord origin, Cardinal dir){
		Coord cursor;
		Coord start;
		Coord end;
		
		MetaBlock slab = new MetaBlock(Blocks.stone_slab, 1);
		MetaBlock light = new MetaBlock(Blocks.lit_pumpkin);
		MetaBlock sapling = new MetaBlock(Blocks.sapling, 2);
		MetaBlock glass = new MetaBlock(Blocks.glass, 4);
		MetaBlock dirt = new MetaBlock(Blocks.dirt);
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(orth[0]);
		end.add(orth[1]);
		
		start.add(Cardinal.reverse(dir), 7);
		end.add(dir, 7);
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, slab, true, true);
		
		cursor = new Coord(origin);
		
		cursor.add(Cardinal.reverse(dir), 6);
		for(int i = 0; i <= 12; ++i ){
			if(i % 2 == 0){
				Coord p = new Coord(cursor);
				if(i % 4 == 0){
					sapling.setBlock(world, p);
					p.add(Cardinal.DOWN);
					dirt.setBlock(world, p);
				} else {
					glass.setBlock(world, p);
					p.add(Cardinal.DOWN);
					light.setBlock(world, p);
				}
			}
			cursor.add(dir);
		}
		
		
		
	}
	
	private void ceiling(World world, Random rand, CatacombLevelSettings settings, Coord origin){
		
		MetaBlock fill = new MetaBlock(Blocks.planks, 5);
		
		MinimumSpanningTree tree = new MinimumSpanningTree(rand, 7, 3);
		tree.generate(world, rand, fill, origin);
		
		for(Cardinal dir : Cardinal.directions){
			Coord start = new Coord(origin);
			start.add(dir, 9);
			Coord end = new Coord(start);
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start.add(orth[0], 9);
			end.add(orth[1], 9);
			
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, fill, true, true);
			
			Coord cursor = new Coord(origin);
			cursor.add(Cardinal.DOWN);
			cursor.add(dir, 10);
			cursor.add(orth[0], 10);
			for(int i = 0; i < 5; ++i){
				pillar(world, rand, settings, cursor);
				cursor.add(orth[1], 4);
			}
		}
		
	}
	
	private void pillar(World world, Random rand, CatacombLevelSettings settings, Coord origin){
		
		ITheme theme = settings.getTheme();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord cursor = new Coord(origin);
		WorldGenPrimitive.fillDown(world, rand, cursor, pillar);
		
		for (Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir);
			if(world.isAirBlock(cursor.getX(), cursor.getY(), cursor.getZ())){
				WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);
			}
		}
	}

	@Override
	public int getSize() {
		return 12;
	}

}
