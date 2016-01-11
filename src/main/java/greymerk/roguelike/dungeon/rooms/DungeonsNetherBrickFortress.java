package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Crops;

public class DungeonsNetherBrickFortress extends DungeonBase {
	
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		MetaBlock lava = BlockType.get(BlockType.LAVA_FLOWING);
		MetaBlock air = BlockType.get(BlockType.AIR);
		BlockWeightedRandom netherwart = new BlockWeightedRandom();
		netherwart.addBlock(air, 3);
		netherwart.addBlock(Crops.get(Crops.NETHERWART), 1);
		List<Coord> chests = new ArrayList<Coord>();
		
		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-8, -1, -8));
		end.add(new Coord(8, 6, 8));
		wall.fillRectHollow(editor, rand, start, end, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-4, 6, -4));
		end.add(new Coord(4, 6, 4));
		wall.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, 7, -3));
		end.add(new Coord(3, 7, 3));
		wall.fillRectSolid(editor, rand, start, end, true, true);

		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-2, 7, -2));
		end.add(new Coord(2, 7, 2));
		lava.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-4, -1, -4));
		end.add(new Coord(4, -3, 4));
		wall.fillRectSolid(editor, rand, start, end, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, -2, -3));
		end.add(new Coord(3, -2, 3));
		BlockType.get(BlockType.SOUL_SAND).fillRectSolid(editor, rand, start, end, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, -1, -3));
		end.add(new Coord(3, -1, 3));
		netherwart.fillRectSolid(editor, rand, start, end, false, true);
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.orthogonal(dir);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 5);
			start.add(dir, 4);
			end = new Coord(start);
			start.add(orth[0], 6);
			end.add(orth[1], 6);
			wall.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 5);
			start.add(dir, 6);
			end = new Coord(start);
			start.add(orth[0], 6);
			end.add(orth[1], 6);
			wall.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(Cardinal.DOWN);
			start.add(dir, 4);
			end = new Coord(start);
			start.add(orth[0], 2);
			end.add(orth[1], 2);
			stair.setOrientation(Cardinal.reverse(dir), false).fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(dir, 4);
			cursor.add(orth[0], 4);
			supportPillar(editor, rand, settings, cursor);
			
			for(Cardinal o : orth){
				cursor = new Coord(origin);
				cursor.add(dir, 7);
				cursor.add(o, 2);
				pillar(editor, rand, settings, cursor);
				cursor.add(o);
				chests.add(new Coord(cursor));
				cursor.add(o);
				chests.add(new Coord(cursor));
				cursor.add(o);
				pillar(editor, rand, settings, cursor);
			}
		}
		
		Treasure[] types = new Treasure[]{
				Treasure.ARMOUR,
				Treasure.WEAPONS,
				Treasure.ENCHANTING,
				Treasure.ORE,
				Treasure.TOOLS
				};
		
		Treasure.createChests(editor, rand, rand.nextInt(3) + 1, chests, Arrays.asList(types), settings.getDifficulty(origin));
		return true;
	}
	
	private void supportPillar(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin){
		
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		MetaBlock lava = BlockType.get(BlockType.LAVA_FLOWING);
		
		Coord start;
		Coord end;
		Coord cursor;
		
		for(Cardinal dir : Cardinal.directions){
			start = new Coord(origin);
			start.add(dir);
			end = new Coord(start);
			end.add(Cardinal.UP, 5);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(Cardinal.UP, 4);
			stair.setOrientation(dir, true).setBlock(editor, cursor);
		}
		
		start = new Coord(origin);
		end = new Coord(start);
		end.add(Cardinal.UP, 5);
		lava.fillRectSolid(editor, rand, start, end, true, true);
		List<Coord> core = editor.getRectSolid(start, end);
		Spawner.generate(editor, rand, settings, core.get(rand.nextInt(core.size())));
	}
	
	private void pillar(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin){
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		
		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		end = new Coord(start);
		end.add(Cardinal.UP, 5);
		pillar.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 4);
			cursor.add(dir);
			stair.setOrientation(dir, true).setBlock(editor, rand, cursor, true, false);
			cursor.add(Cardinal.UP);
			wall.setBlock(editor, rand, cursor);
		}
		
		
	}
	
	public int getSize(){
		return 10;
	}
    
    
}
