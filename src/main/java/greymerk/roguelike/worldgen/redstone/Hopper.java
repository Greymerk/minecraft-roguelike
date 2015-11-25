package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class Hopper {

	public static void generate(WorldEditor editor, Cardinal dir, Coord pos){
		
		MetaBlock hopper = new MetaBlock(Blocks.hopper);
		
		int meta;
		
		switch(dir){
		case NORTH: meta = 2; break;
		case SOUTH: meta = 3; break;
		case WEST: meta = 4; break;
		case EAST: meta = 5; break;
		default: meta = 0;
		}
		
		hopper.setMeta(meta);
		
		hopper.setBlock(editor, pos);
	}
}
