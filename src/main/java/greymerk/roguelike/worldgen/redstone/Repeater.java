package greymerk.roguelike.worldgen.redstone;

import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.init.Blocks;

public class Repeater {
	
	public static void generate(IWorldEditor editor, Random rand, Cardinal dir, int delay, Coord pos){
		generate(editor, rand, dir, delay, false, pos);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Cardinal dir, int delay, boolean powered, Coord pos){
		
		MetaBlock repeater = powered ? new MetaBlock(Blocks.powered_repeater) : new MetaBlock(Blocks.unpowered_repeater);
		repeater.withProperty(BlockRedstoneRepeater.FACING, Cardinal.getFacing(dir));
		if (delay > 0 && delay <= 4) repeater.withProperty(BlockRedstoneRepeater.DELAY, delay);
		repeater.setBlock(editor, pos);
	}
	
}
