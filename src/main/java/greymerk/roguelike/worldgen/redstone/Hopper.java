package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockHopper;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Hopper {

	public static void generate(World world, Cardinal dir, Coord pos){
		
		MetaBlock hopper = new MetaBlock(Blocks.hopper);
		hopper.withProperty(BlockHopper.field_176430_a, Cardinal.getFacing(dir));
		
		hopper.setBlock(world, pos);
	}
}
