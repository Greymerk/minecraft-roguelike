package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Vine;

import java.util.Random;

public class SegmentMossyArch extends SegmentBase {

	private boolean spawnHoleSet = false;
	
	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal wallDirection, ITheme theme, int x, int y, int z) {
		
		IStair stair = theme.getSecondaryStair(); 
		stair.setOrientation(Cardinal.reverse(wallDirection), true);
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		level.getSettings().getSecrets().genRoom(editor, rand, level.getSettings(), wallDirection, new Coord(x, y, z));
		
		Coord cursor = new Coord(x, y, z);
		cursor.add(wallDirection, 2);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, stair, true, true);
		
		for(Cardinal orth : Cardinal.getOrthogonal(wallDirection)){
			cursor = new Coord(x, y, z);
			cursor.add(orth, 1);
			cursor.add(wallDirection, 2);
			editor.setBlock(rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, theme.getSecondaryWall(), true, true);
			cursor.add(Cardinal.reverse(wallDirection), 1);
			editor.setBlock(rand, cursor, stair, true, true);			
		}
		
		cursor = new Coord(x, y, z);
		cursor.add(wallDirection, 2);
		cursor.add(Cardinal.DOWN, 1);
		editor.setBlock(cursor, BlockType.get(BlockType.WATER_FLOWING));
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 3);
		cursor.add(wallDirection, 1);
		editor.setBlock(rand, cursor, BlockType.get(BlockType.VINE), true, true);
		
		if(!spawnHoleSet){
			editor.fillRectSolid(rand, new Coord(x, y + 2, z), new Coord(x, y + 5, z), BlockType.get(BlockType.AIR));
			Vine.fill(editor, rand, new Coord(x, y + 3, z), new Coord(x, y + 5, z));
			
			if(!editor.isAirBlock(new Coord(x, y + 6, z))) editor.setBlock(new Coord(x, y + 7, z), BlockType.get(BlockType.WATER_FLOWING));
			spawnHoleSet = true;
		}
	}

}
