package greymerk.roguelike.worldgen.redstone;

import java.util.Arrays;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.block.BlockHopper;
import net.minecraft.init.Blocks;

public class Hopper {

	public static void generate(WorldEditor editor, Cardinal dir, Coord pos){
		MetaBlock hopper = new MetaBlock(Blocks.hopper);
		if(Arrays.asList(Cardinal.directions).contains(dir)){
			hopper.withProperty(BlockHopper.field_176430_a, Cardinal.getFacing(dir));
		}
		hopper.setBlock(editor, pos);
	}
}
