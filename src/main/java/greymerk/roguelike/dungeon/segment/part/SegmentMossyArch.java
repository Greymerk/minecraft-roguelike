package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class SegmentMossyArch extends SegmentBase {

	private boolean spawnHoleSet = false;
	
	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal wallDirection, ITheme theme, int x, int y, int z) {
		
		IStair stair = theme.getSecondaryStair(); 
		stair.setOrientation(Cardinal.reverse(wallDirection), true);
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
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
		editor.setBlock(cursor, Blocks.flowing_water);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 3);
		cursor.add(wallDirection, 1);
		editor.setBlock(rand, cursor, new MetaBlock(Blocks.vine), true, true);
		
		if(!spawnHoleSet){
			editor.fillRectSolid(rand, x, y + 2, z, x, y + 5, z, new MetaBlock(Blocks.air));
			editor.randomVines(rand, x, y + 3, z, x, y + 5, z);
			
			if(!editor.isAirBlock(new Coord(x, y + 6, z))) editor.setBlock(x, y + 7, z, Blocks.flowing_water);
			spawnHoleSet = true;
		}
	}

}
