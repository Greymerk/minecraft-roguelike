package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import net.minecraft.init.Blocks;

public class SegmentArch extends SegmentBase {

	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
			
		IStair stair = theme.getSecondaryStair(); 
		stair.setOrientation(Cardinal.reverse(dir), true);
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		Coord cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, stair, true, true);
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			cursor = new Coord(x, y, z);
			cursor.add(orth, 1);
			cursor.add(dir, 2);
			editor.setBlock(rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, theme.getPrimaryWall(), true, true);
			cursor.add(Cardinal.reverse(dir), 1);
			editor.setBlock(rand, cursor, stair, true, true);			
		}
	}
}
