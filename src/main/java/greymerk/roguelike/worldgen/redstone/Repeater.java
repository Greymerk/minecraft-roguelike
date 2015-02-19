package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.Random;

import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Repeater {
	
	public static void generate(World world, Random rand, Cardinal dir, int delay, Coord pos){
		
		MetaBlock repeater = new MetaBlock(Blocks.unpowered_repeater);
		repeater.withProperty(BlockRedstoneRepeater.FACING, Cardinal.getFacing(dir));
		repeater.withProperty(BlockRedstoneRepeater.field_176410_b, delay);
		repeater.setBlock(world, pos);
	}
	
}
