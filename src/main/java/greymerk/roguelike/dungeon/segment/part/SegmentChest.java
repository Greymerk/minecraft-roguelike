package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

import java.util.Random;

public class SegmentChest extends SegmentBase {

	
	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getSecondaryStair();
		
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);		
		
		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, air, true, true);
		start.add(dir, 1);
		end.add(dir, 1);
		editor.fillRectSolid(rand, start, end, theme.getSecondaryWall(), true, true);
		
		for(Cardinal d : orth){
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 2);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			stair.setOrientation(Cardinal.reverse(dir), true);
			editor.setBlock(rand, cursor, stair, true, true);
			
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			stair.setOrientation(Cardinal.reverse(d), false);
			editor.setBlock(rand, cursor, stair, true, true);
		}
	
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 1);
		cursor.add(dir, 3);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		stair.setOrientation(Cardinal.reverse(dir), true);
		editor.setBlock(rand, cursor, stair, true, true);
		
		Coord shelf = new Coord(origin);
		shelf.add(dir, 3);
		Coord below = new Coord(shelf);
		shelf.add(Cardinal.UP, 1);
		
		if(editor.isAirBlock(below)) return;	
		
		boolean trapped = Dungeon.getLevel(origin.getY()) == 3 && rand.nextInt(3) == 0;
		Treasure.generate(editor, rand, shelf, Dungeon.getLevel(origin.getY()), trapped);
		if(trapped){
			editor.setBlock(new Coord(shelf.getX(), shelf.getY() - 2, shelf.getZ()), BlockType.get(BlockType.TNT));
			if(rand.nextBoolean()) editor.setBlock(new Coord(shelf.getX(), shelf.getY() - 3, shelf.getZ()), BlockType.get(BlockType.TNT));
		}
	}
}
