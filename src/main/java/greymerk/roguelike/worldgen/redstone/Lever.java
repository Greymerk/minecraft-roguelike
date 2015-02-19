package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockLever;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Lever {

	public static void generate(World world, Cardinal dir, Coord pos, boolean active){
		
		MetaBlock lever = new MetaBlock(Blocks.lever);
		lever.withProperty(BlockLever.FACING, Cardinal.getOrientation(dir));
		lever.withProperty(BlockLever.POWERED, active);
		lever.setBlock(world, pos);
	}
	
}
