package greymerk.roguelike.worldgen;

import net.minecraft.src.World;

public class MetaBlock implements IBlockFactory{

	private int blockID;
	private int meta;
	private int flag;
	
	public MetaBlock(int blockID){
		this(blockID, 0, 2);		
	}
	
	public MetaBlock(int blockID, int meta){
		this(blockID, meta, 2);
	}
	
	public MetaBlock(int blockID, int meta, int flag){
		this.blockID = blockID;
		this.meta = meta;
		this.flag = flag;
	}
	
	public int getBlockID(){
		return blockID;
	}
	
	public int getMeta(){
		return meta;
	}
	
	public int getFlag(){
		return flag;
	}
	
	public void setBlockID(int in){
		blockID = in;
	}
	
	public void setMeta(int in){
		meta = in;
	}
	
	public void setFlag(int in){
		flag = in;
	}

	@Override
	public void setBlock(World world, int x, int y, int z) {
		WorldGenPrimitive.setBlock(world, x, y, z, this);
	}

	@Override
	public void setBlock(World world, int x, int y, int z, boolean fillAir, boolean replaceSolid) {
		WorldGenPrimitive.setBlock(world, x, y, z, this, fillAir, replaceSolid);
	}

	@Override
	public MetaBlock getMetaBlock() { return this; }
}
