package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

import java.util.List;
import java.util.Random;

public class SegmentTomb extends SegmentBase {
	
	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getPrimaryStair();
		
		Coord cursor = new Coord(x, y, z);
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		cursor.add(dir, 2);
		start = new Coord(cursor);
		start.add(orth[0], 1);
		end = new Coord(cursor);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, air, true, true);
		
		start.add(dir, 1);
		end.add(dir, 1);
		editor.fillRectSolid(rand, start, end, theme.getSecondaryWall(), false, true);

		cursor.add(Cardinal.UP, 2);
		for(Cardinal d : orth){
			Coord c = new Coord(cursor);
			c.add(d, 1);
			stair.setOrientation(Cardinal.reverse(d), true);
			editor.setBlock(rand, c, stair, true, true);
		}
		
		tomb(editor, rand, level.getSettings(), theme, dir, new Coord(x, y, z));
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 3);
		editor.setBlock(cursor, BlockType.get(BlockType.QUARTZ));
		
	}
	
	private static void tomb(WorldEditor editor, Random rand, LevelSettings level, ITheme theme, Cardinal dir, Coord pos){
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start = new Coord(pos);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 3);
		end.add(dir, 3);
		List<Coord> box = editor.getRectHollow(start, end);
		
		// make sure the box is solid wall
		for(Coord c : box){
			if(!editor.getBlock(c).getBlock().getMaterial().isSolid()) return;
		}
		
		editor.fillRectHollow(rand, start, end, theme.getPrimaryWall(), true, true);
		if(!(rand.nextInt(3) == 0)) return;
		cursor = new Coord(pos);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 4);
		Spawner.generate(editor, rand, level, cursor, rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE);
		cursor.add(dir);
		TreasureChest.generate(editor, rand, level, cursor, rand.nextBoolean() ? TreasureChest.ARMOUR : TreasureChest.WEAPONS);
		
	}
}
