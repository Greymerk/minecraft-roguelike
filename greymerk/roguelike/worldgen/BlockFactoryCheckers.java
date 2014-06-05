package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.src.World;

public class BlockFactoryCheckers implements IBlockFactory {

	private MetaBlock fillOne;
	private MetaBlock fillTwo;
	
	public BlockFactoryCheckers(MetaBlock fillOne, MetaBlock fillTwo){
		this.fillOne = fillOne;
		this.fillTwo = fillTwo;
	}
	
	@Override
	public void setBlock(World world, Random rand, int x, int y, int z) {
		setBlock(world, rand, x, y, z, true, true);

	}

	@Override
	public void setBlock(World world, Random rand, int x, int y, int z, boolean fillAir, boolean replaceSolid) {
		
		if (x % 2 == 0) {
			if(z % 2 == 0){
				if(y % 2 == 0){
					WorldGenPrimitive.setBlock(world, x, y, z, fillOne, fillAir, replaceSolid);
				} else {
					WorldGenPrimitive.setBlock(world, x, y, z, fillTwo, fillAir, replaceSolid);
				}
			} else {
				if(y % 2 == 0){
					WorldGenPrimitive.setBlock(world, x, y, z, fillTwo, fillAir, replaceSolid);
				} else {
					WorldGenPrimitive.setBlock(world, x, y, z, fillOne, fillAir, replaceSolid);
				}
			}
		} else {
			if(z % 2 == 0){
				if(y % 2 == 0){
					WorldGenPrimitive.setBlock(world, x, y, z, fillTwo, fillAir, replaceSolid);
				} else {
					WorldGenPrimitive.setBlock(world, x, y, z, fillOne, fillAir, replaceSolid);
				}
			} else {
				if(y % 2 == 0){
					WorldGenPrimitive.setBlock(world, x, y, z, fillOne, fillAir, replaceSolid);
				} else {
					WorldGenPrimitive.setBlock(world, x, y, z, fillTwo, fillAir, replaceSolid);
				}
			}
		}
	}
}
