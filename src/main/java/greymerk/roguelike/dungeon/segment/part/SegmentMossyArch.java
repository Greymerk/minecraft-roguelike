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
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal wallDirection, ITheme theme, Coord origin) {
		
		IStair stair = theme.getSecondaryStair(); 
		stair.setOrientation(Cardinal.reverse(wallDirection), true);
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		level.getSettings().getSecrets().genRoom(editor, rand, level.getSettings(), wallDirection, new Coord(origin));
		
		Coord cursor = new Coord(origin);
		cursor.add(wallDirection, 2);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, stair, true, true);
		
		for(Cardinal orth : Cardinal.getOrthogonal(wallDirection)){
			cursor = new Coord(origin);
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
		
		cursor = new Coord(origin);
		cursor.add(wallDirection, 2);
		cursor.add(Cardinal.DOWN, 1);
		editor.setBlock(cursor, BlockType.get(BlockType.WATER_FLOWING));
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 3);
		cursor.add(wallDirection, 1);
		editor.setBlock(rand, cursor, BlockType.get(BlockType.VINE), true, true);
		
		if(!spawnHoleSet){
			editor.fillRectSolid(rand, new Coord(0, 2, 0).add(origin), new Coord(0, 5, 0).add(origin), BlockType.get(BlockType.AIR));
			Vine.fill(editor, rand, new Coord(0, 3, 0).add(origin), new Coord(0, 5, 0).add(origin));
			
			if(!editor.isAirBlock(new Coord(0, 6, 0).add(origin))) editor.setBlock(new Coord(0, 7, 0).add(origin), BlockType.get(BlockType.WATER_FLOWING));
			spawnHoleSet = true;
		}
	}

}
