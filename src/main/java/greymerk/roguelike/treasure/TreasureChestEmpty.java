package greymerk.roguelike.treasure;

import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class TreasureChestEmpty extends TreasureChestBase implements ITreasureChest {

	@Override
	public ITreasureChest generate(World inWorld, Random inRand, int x, int y, int z, int level, boolean trapped) {
		
		world = inWorld;
		rand = inRand;
		posX = x;
		posY = y;
		posZ = z;

		Block type = trapped ? Blocks.trapped_chest : Blocks.chest;
		
		
		if(!WorldGenPrimitive.setBlock(world, x, y, z, type)){
			return null;
		}
		
		chest = (TileEntityChest) world.getTileEntity(x, y, z);
		
		return this;

	}

	@Override
	protected void fillChest(TileEntityChest chest, int level) {
	}
}
