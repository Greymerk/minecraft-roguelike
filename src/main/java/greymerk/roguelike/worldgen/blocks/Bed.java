package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class Bed {

	public static void generate(WorldEditor editor, Cardinal dir, Coord pos){
		
		Coord cursor = new Coord(pos);
		
		int meta;
		
		switch(Cardinal.reverse(dir)){
		case SOUTH: meta = 0; break;
		case WEST: meta = 1; break;
		case NORTH: meta = 2; break;
		case EAST: meta = 3; break;
		default: meta = 0; break;
		}
		
		MetaBlock head = new MetaBlock(Blocks.bed.getStateFromMeta(meta + 8));
		head.setBlock(editor, cursor);
		
		MetaBlock foot = new MetaBlock(Blocks.bed.getStateFromMeta(meta));
		cursor.add(dir);
		foot.setBlock(editor, cursor);
	}
}
