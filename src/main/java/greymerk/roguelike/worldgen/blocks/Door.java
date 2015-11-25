package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public enum Door {

	IRON, OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK;
	
	public static void generate(WorldEditor editor, Coord pos, Cardinal dir, Door type){
		generate(editor, pos, dir, type, false);
	}
	
	public static void generate(WorldEditor editor, Coord pos, Cardinal dir, Door type, boolean open){
		
		Coord cursor = new Coord(pos);
		
		MetaBlock doorBase = new MetaBlock(getBlockId(type), getMeta(false, dir, open, false));
		doorBase.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		MetaBlock doorTop = new MetaBlock(getBlockId(type), getMeta(true, dir, open, false));
		doorTop.setBlock(editor, cursor);
	}
	
	private static Block getBlockId(Door type){
		if(type == Door.IRON) return Blocks.iron_door; 
		return Blocks.wooden_door;
	}
	
	private static int getMeta(boolean top, Cardinal dir, boolean open, boolean hingeLeft){
		int meta = 0;

		if(top){
			if(hingeLeft) meta += 1;
			return meta + 8;
		} else {
			if(open) meta += 4;
			
			switch(dir){
			case WEST: return meta;
			case NORTH: return meta + 1;
			case EAST: return meta + 2;
			case SOUTH: return meta + 3;
			default: return meta;
			}
		}
	}
}
