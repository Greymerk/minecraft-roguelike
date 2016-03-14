package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Quartz;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import scala.actors.threadpool.Arrays;

public class DungeonsEnder extends DungeonBase {


	public boolean generate(IWorldEditor editor, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		MetaBlock black = BlockType.get(BlockType.OBSIDIAN);
		MetaBlock white = Quartz.get(Quartz.SMOOTH);
		MetaBlock air = BlockType.get(BlockType.AIR);

		Coord start;
		Coord end;
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-3, 0, -3);
		end.add(3, 2, 3);
		RectSolid.fill(editor, inRandom, start, end, air);
		for (Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.orthogonal(dir);
			
			start = new Coord(origin);
			start.add(dir, 4);
			end = new Coord(start);
			start.add(orth[0], 4);
			start.add(Cardinal.DOWN, 1);
			end.add(orth[1], 4);
			end.add(Cardinal.UP, 5);
			RectSolid.fill(editor, inRandom, start, end, black, false, true);
			
		}
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-3, 2, -3);
		end.add(3, 10, 3);
		
		int top = end.getY() - start.getY() + 1;
		for(Coord cell : new RectSolid(start, end)){
			boolean disolve = inRandom.nextInt((cell.getY() - start.getY()) + 1) < 2;
			air.set(editor, inRandom, cell, false, disolve);
			black.set(editor, inRandom, cell, false, inRandom.nextInt(top - (cell.getY() - start.getY())) == 0 && !disolve);
		}
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, -1, 4);
		
		BlockCheckers checkers = new BlockCheckers(black, white);
		RectSolid.fill(editor, inRandom, start, end, checkers);
		for(Cardinal dir : Cardinal.directions){
			if(Arrays.asList(entrances).contains(dir)) continue;
			Coord ender = new Coord(origin);
			origin.add(dir, 4);
			MetaBlock enderchest = BlockType.get(BlockType.ENDERCHEST);
			enderchest.set(editor, ender);
			break;
		}
		Spawner.generate(editor, inRandom, settings, origin, Spawner.ENDERMAN);

		return true;
	}
	
	
	public int getSize(){
		return 7;
	}
}
