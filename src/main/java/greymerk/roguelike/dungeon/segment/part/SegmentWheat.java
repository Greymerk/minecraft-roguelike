package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Crops;

public class SegmentWheat extends SegmentBase {


	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
		
		Coord cursor;
		Coord start;
		Coord end;
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.DOWN);
		cursor.add(dir, 3);
		editor.setBlock(cursor, BlockType.get(BlockType.WATER_FLOWING));
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		start.add(Cardinal.UP, 2);
		end.add(dir);
		editor.fillRectSolid(rand, start, end, theme.getSecondaryWall(), true, true);
		
		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 1);
		editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.AIR), true, true);
		start.add(Cardinal.DOWN, 1);
		end.add(Cardinal.DOWN, 2);

		editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.FARMLAND), true, true);
		start.add(Cardinal.UP, 1);
		end.add(Cardinal.UP, 1);
		BlockJumble crops = new BlockJumble();
		crops.addBlock(Crops.get(Crops.WHEAT));
		crops.addBlock(Crops.get(Crops.CARROTS));
		crops.addBlock(Crops.get(Crops.POTATOES));
		editor.fillRectSolid(rand, start, end, crops, true, true);
		
		cursor = new Coord(origin);
		cursor.add(dir, 3);
		cursor.add(Cardinal.UP, 1);
		MetaBlock pumpkin = Crops.getPumpkin(Cardinal.reverse(dir), true);		
		pumpkin.setBlock(editor, cursor);
		
		IStair stair = theme.getSecondaryStair();
		
		for(Cardinal d : orth){
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(d, 1);
			cursor.add(Cardinal.UP, 1);	
			stair.setOrientation(Cardinal.reverse(d), true);
			editor.setBlock(rand, cursor, stair, true, true);
		}
	}
}
