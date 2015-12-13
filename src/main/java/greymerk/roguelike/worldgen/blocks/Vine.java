package greymerk.roguelike.worldgen.blocks;

import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;

public class Vine {

	public static void fill(WorldEditor editor, Random rand, Coord start, Coord end){
		for(Coord cursor : WorldEditor.getRectSolid(start, end)){
			set(editor, cursor);
		}
	}
	
	public static MetaBlock setOrientation(MetaBlock vine, Cardinal dir){
		switch(dir){
		case SOUTH: vine.setMeta(0); break;
		case WEST: vine.setMeta(1); break;
		case NORTH: vine.setMeta(2); break;
		case EAST: vine.setMeta(3); break;
		default: vine.setMeta(0); break;
		}
		return vine;
	}
	
	public static void set(WorldEditor editor, Coord pos){
		if(!editor.isAirBlock(pos)) return;		
		
		for (int dir = 2; dir <= 5; ++dir){
			if (editor.canPlaceOnSide(Blocks.vine, pos, dir)){
				editor.setBlock(pos, new MetaBlock(Blocks.vine, 1 << Direction.facingToDirection[Facing.oppositeSide[dir]]), true, true);
			};
		}
	}
}
