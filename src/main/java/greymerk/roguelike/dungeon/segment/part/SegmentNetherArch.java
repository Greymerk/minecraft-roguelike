package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class SegmentNetherArch extends SegmentBase {

	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		IStair step = theme.getSecondaryStair();
		step.setOrientation(Cardinal.reverse(dir), true);
		IBlockFactory pillar = theme.getSecondaryPillar();
		

		Coord cursor;
		
		boolean hasLava = rand.nextInt(5) == 0;
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			cursor = new Coord(x, y, z);
			cursor.add(dir, 1);
			cursor.add(orth, 1);
			cursor.add(Cardinal.UP, 2);
			editor.setBlock(rand, cursor, step, true, true);
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(orth, 1);
			editor.setBlock(rand, cursor, pillar, true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, pillar, true, true);
		}
			
		MetaBlock fence = new MetaBlock(Blocks.nether_brick_fence);
		MetaBlock lava = new MetaBlock(Blocks.flowing_lava);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);		
		editor.setBlock(rand, cursor, fence, true, true);
		cursor.add(Cardinal.UP, 1);		
		editor.setBlock(rand, cursor, fence, true, true);
		
		if(hasLava){
			cursor.add(dir, 1);
			editor.setBlock(rand, cursor, lava, true, true);
			cursor.add(Cardinal.DOWN, 1);		
			editor.setBlock(rand, cursor, lava, true, true);
		}
	}
}
