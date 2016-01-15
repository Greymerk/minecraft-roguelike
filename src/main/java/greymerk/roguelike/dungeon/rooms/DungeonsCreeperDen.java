package greymerk.roguelike.dungeon.rooms;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsCreeperDen extends DungeonBase {

	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();
		
		Coord start;
		Coord end;
		
		MetaBlock tnt = BlockType.get(BlockType.TNT);
		
		BlockWeightedRandom mossy = new BlockWeightedRandom();
		mossy.addBlock(theme.getPrimaryWall(), 3);
		mossy.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 1);
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(theme.getPrimaryFloor(), 1);
		mossy.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 1);
		floor.addBlock(BlockType.get(BlockType.GRAVEL), 3);
		
		BlockWeightedRandom subfloor = new BlockWeightedRandom();
		subfloor.addBlock(floor, 3);
		subfloor.addBlock(tnt, 1);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-4, -4, -4));
		end.add(new Coord(4, 5, 4));
		RectHollow.fill(editor, rand, start, end, mossy, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, -1, -3));
		end.add(new Coord(3, -1, 3));
		RectSolid.fill(editor, rand, start, end, floor, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, -3, -3));
		end.add(new Coord(3, -2, 3));
		RectSolid.fill(editor, rand, start, end, subfloor, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, 0, -3));
		end.add(new Coord(3, 0, 3));
		
		List<Coord> chestSpaces = editor.getRectSolid(start, end);
		Collections.shuffle(chestSpaces, rand);
		
		int counter = 0;
		for(Coord spot : chestSpaces){
			if(Treasure.isValidChestSpace(editor, spot)){
				Treasure.generate(editor, rand, spot, Treasure.ORE, settings.getDifficulty(spot), true);
				Coord cursor = new Coord(spot);
				cursor.add(Cardinal.DOWN, 2);
				tnt.setBlock(editor, cursor);
				++counter;
			}
			
			if(counter >= 2) break;
		}
		
		Spawner.generate(editor, rand, settings, new Coord(origin), Spawner.CREEPER);
		
		return true;
	}
	
	public int getSize(){
		return 7;
	}
}
