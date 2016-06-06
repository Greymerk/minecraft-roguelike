package greymerk.roguelike.worldgen.redstone;

import java.util.Arrays;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.block.BlockHopper;
import net.minecraft.init.Blocks;

public class Hopper {

	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		MetaBlock hopper = new MetaBlock(Blocks.HOPPER);
		if(Arrays.asList(Cardinal.directions).contains(dir)){
			hopper.withProperty(BlockHopper.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		}
		hopper.set(editor, pos);
	}
}
