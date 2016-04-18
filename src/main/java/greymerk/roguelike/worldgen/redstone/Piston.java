package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class Piston {

	public static void generate(IWorldEditor editor, Coord origin, Cardinal dir, boolean sticky){
		
		MetaBlock piston = new MetaBlock(sticky ? Blocks.sticky_piston : Blocks.piston);
		
		int meta = 0;
		switch(dir){
		case DOWN: meta = 0; break;
		case UP: meta = 1; break;
		case NORTH: meta = 2; break;
		case SOUTH: meta = 3; break;
		case WEST: meta = 4; break;
		case EAST: meta = 5; break;
		}
		
		piston.setMeta(meta);
		
		piston.set(editor, origin);
	}
	
}
