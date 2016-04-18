package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class Hopper {

	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		
		MetaBlock hopper = new MetaBlock(Blocks.hopper);
		
		int meta;
		
		switch(dir){
		case NORTH: meta = 2; break;
		case SOUTH: meta = 3; break;
		case WEST: meta = 4; break;
		case EAST: meta = 5; break;
		default: meta = 0;
		}
		
		hopper.set(editor, pos);
		editor.setBlockMetadata(pos, meta);
	}
}
