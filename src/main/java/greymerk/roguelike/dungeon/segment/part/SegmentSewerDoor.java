package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.Door;
import greymerk.roguelike.worldgen.blocks.Leaves;
import net.minecraft.init.Blocks;

public class SegmentSewerDoor extends SegmentBase {
	
	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = theme.getSecondaryStair();
		MetaBlock bars = new MetaBlock(Blocks.iron_bars);
		MetaBlock water = new MetaBlock(Blocks.flowing_water);
		MetaBlock leaves = Leaves.get(Leaves.SPRUCE, false);
		MetaBlock glowstone = new MetaBlock(Blocks.glowstone);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.DOWN);
		bars.setBlock(editor, cursor);
		start = new Coord(cursor);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		WorldEditor.blockOrientation(stair, orth[0], true).setBlock(editor, start);
		WorldEditor.blockOrientation(stair, orth[1], true).setBlock(editor, end);
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.DOWN);
		bars.setBlock(editor, cursor);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		editor.fillRectSolid(rand, start, end, water, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 3);
		bars.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		editor.setBlock(rand, cursor, leaves, false, true);
		cursor.add(dir);
		editor.setBlock(rand, cursor, water, false, true);
		cursor.add(dir);
		editor.setBlock(rand, cursor, glowstone, false, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		start = new Coord(cursor);
		start.add(orth[0], 1);
		end = new Coord(cursor);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, air, true, true);
		
		SecretFactory secrets = level.getSettings().getSecrets();
		boolean room = secrets.genRoom(editor, rand, level.getSettings(), dir, new Coord(x, y, z));
		
		start.add(dir, 1);
		end.add(dir, 1);
		editor.fillRectSolid(rand, start, end, theme.getSecondaryWall(), false, true);

		cursor.add(Cardinal.UP, 2);
		for(Cardinal d : orth){
			Coord c = new Coord(cursor);
			c.add(d, 1);
			WorldEditor.blockOrientation(stair, Cardinal.reverse(d), true);
			editor.setBlock(rand, c, stair, true, true);
		}
		
		if(room){
			cursor = new Coord(x, y, z);
			cursor.add(dir, 3);
			Door.generate(editor, cursor, Cardinal.reverse(dir), Door.IRON);
		}
	}	
}
