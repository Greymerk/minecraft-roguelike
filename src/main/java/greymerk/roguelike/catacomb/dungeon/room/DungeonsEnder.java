package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsEnder extends DungeonBase {
	World world;
	Random rand;

	byte dungeonHeight;
	int dungeonLength;
	int dungeonWidth;
	
	public DungeonsEnder() {
		dungeonHeight = 10;
		dungeonLength = 4;
		dungeonWidth = 4;
	}

	public boolean generate(World inWorld, Random inRandom, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {

		world = inWorld;
		rand = inRandom;

		MetaBlock black = new MetaBlock(Blocks.obsidian);
		MetaBlock white = new MetaBlock(Blocks.quartz_block);
		MetaBlock air = new MetaBlock(Blocks.air);

		Coord start;
		Coord end;
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-3, 0, -3);
		end.add(3, 2, 3);
		air.fillRectSolid(inWorld, inRandom, start, end, true, true);
		for (Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			start = new Coord(origin);
			start.add(dir, 4);
			end = new Coord(start);
			start.add(orth[0], 4);
			start.add(Cardinal.DOWN, 1);
			end.add(orth[1], 4);
			end.add(Cardinal.UP, 5);
			black.fillRectSolid(inWorld, inRandom, start, end, false, true);
			
		}
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-3, 2, -3);
		end.add(3, 10, 3);
		List<Coord> box = WorldGenPrimitive.getRectSolid(start, end);
		
		int top = end.getY() - start.getY() + 1;
		for(Coord cell : box){
			boolean disolve = rand.nextInt((cell.getY() - start.getY()) + 1) < 2;
			air.setBlock(inWorld, inRandom, cell, false, disolve);
			black.setBlock(inWorld, rand, cell, false, rand.nextInt(top - (cell.getY() - start.getY())) == 0 && !disolve);
		}
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, -1, 4);
		
		BlockFactoryCheckers checkers = new BlockFactoryCheckers(black, white);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, start, end, checkers, true, true);
		// TODO: add ender chest
		Spawner.generate(world, rand, settings, origin, Spawner.ENDERMAN);

		return true;
	}
	
	
	public int getSize(){
		return 7;
	}
}
