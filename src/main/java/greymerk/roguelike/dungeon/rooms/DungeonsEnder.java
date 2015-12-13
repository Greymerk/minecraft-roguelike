package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Quartz;

public class DungeonsEnder extends DungeonBase {


	public boolean generate(WorldEditor editor, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		MetaBlock black = BlockType.get(BlockType.OBSIDIAN);
		MetaBlock white = Quartz.get(Quartz.SMOOTH);
		MetaBlock air = BlockType.get(BlockType.AIR);

		Coord start;
		Coord end;
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-3, 0, -3);
		end.add(3, 2, 3);
		air.fillRectSolid(editor, inRandom, start, end, true, true);
		for (Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			start = new Coord(origin);
			start.add(dir, 4);
			end = new Coord(start);
			start.add(orth[0], 4);
			start.add(Cardinal.DOWN, 1);
			end.add(orth[1], 4);
			end.add(Cardinal.UP, 5);
			black.fillRectSolid(editor, inRandom, start, end, false, true);
			
		}
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-3, 2, -3);
		end.add(3, 10, 3);
		List<Coord> box = WorldEditor.getRectSolid(start, end);
		
		int top = end.getY() - start.getY() + 1;
		for(Coord cell : box){
			boolean disolve = inRandom.nextInt((cell.getY() - start.getY()) + 1) < 2;
			air.setBlock(editor, inRandom, cell, false, disolve);
			black.setBlock(editor, inRandom, cell, false, inRandom.nextInt(top - (cell.getY() - start.getY())) == 0 && !disolve);
		}
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, -1, 4);
		
		BlockCheckers checkers = new BlockCheckers(black, white);
		editor.fillRectSolid(inRandom, start, end, checkers, true, true);
		// TODO: add ender chest
		Spawner.generate(editor, inRandom, settings, origin, Spawner.ENDERMAN);

		return true;
	}
	
	
	public int getSize(){
		return 7;
	}
}
