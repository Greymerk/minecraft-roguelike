package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.Random;

import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Comparator {
	
	public static void generate(World world, Random rand, Cardinal dir, boolean subtraction, Coord pos){
		
		MetaBlock comparator = new MetaBlock(Blocks.unpowered_comparator);
		comparator.withProperty(BlockRedstoneComparator.FACING, Cardinal.getFacing(dir));
		if(subtraction){
			comparator.withProperty(BlockRedstoneComparator.field_176463_b, BlockRedstoneComparator.Mode.SUBTRACT);
		} else {
			comparator.withProperty(BlockRedstoneComparator.field_176463_b, BlockRedstoneComparator.Mode.COMPARE);
		}
		comparator.setBlock(world, pos);
	}
	
}
