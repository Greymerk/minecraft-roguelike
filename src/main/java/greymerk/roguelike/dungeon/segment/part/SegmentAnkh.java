package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.DyeColor;

import java.util.Random;

public class SegmentAnkh extends SegmentBase{

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		Coord start;
		Coord end;
		Coord cursor;
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getSecondaryStair();
		DyeColor color = DyeColor.get(rand);
		MetaBlock glass = ColorBlock.get(ColorBlock.GLASS, color);
		MetaBlock back = ColorBlock.get(ColorBlock.CLAY, color);
		MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(x, y, z);
		start.add(dir, 2);
		end = new Coord(start);
		end.add(Cardinal.UP, 2);
		
		editor.fillRectSolid(rand, start, end, air, true, true);
		
		
		for(Cardinal o : orth){
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(o);
			stair.setOrientation(Cardinal.reverse(o), false).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(o), false).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
		}
		
		start = new Coord(x, y, z);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, glass, true, true);
		start.add(dir);
		end.add(dir);
		editor.fillRectSolid(rand, start, end, back, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 3);
		cursor.add(Cardinal.DOWN);
		glowstone.setBlock(editor, cursor);
		cursor.add(Cardinal.UP, 4);
		glowstone.setBlock(editor, cursor);
	}

}
