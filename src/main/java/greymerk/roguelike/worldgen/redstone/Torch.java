package greymerk.roguelike.worldgen.redstone;


import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public enum Torch {

	REDSTONE, WOODEN, REDSTONE_UNLIT;
	
	public static void generate(WorldEditor editor, Torch type, Cardinal dir, Coord pos){
		
		Block name;
		
		switch(type){
		case WOODEN: name = Blocks.torch; break;
		case REDSTONE: name = Blocks.redstone_torch; break;
		case REDSTONE_UNLIT: name = Blocks.unlit_redstone_torch; break;
		default: name = Blocks.torch; break;
		}		
		
		int meta;
		switch(dir){
		case EAST: meta = 1; break;
		case WEST: meta = 2; break;
		case SOUTH: meta = 3; break;
		case NORTH: meta = 4; break;
		default: meta = 5; break;
		}
		
		MetaBlock torch = new MetaBlock(name, meta);
		
		torch.setBlock(editor, pos);
	}
}
