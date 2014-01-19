package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public class TreasureChestEmpty extends TreasureChestBase implements ITreasureChest {

	@Override
	public ITreasureChest generate(World inWorld, Random inRand, int x, int y, int z, int level, boolean trapped) {
		
		world = inWorld;
		rand = inRand;
		posX = x;
		posY = y;
		posZ = z;

		int type = trapped ? Block.chestTrapped.blockID : Block.chest.blockID;
		
		
		if(!WorldGenPrimitive.setBlock(world, x, y, z, type)){
			return null;
		}
		
		chest = (TileEntityChest) world.getBlockTileEntity(x, y, z);
		
		return this;

	}

	@Override
	protected void fillChest(TileEntityChest chest, int level) {
	}
}
