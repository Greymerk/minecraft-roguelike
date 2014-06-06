package greymerk.roguelike.worldgen;

import java.util.Random;

import com.google.gson.JsonObject;

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
	
	public MetaBlock(JsonObject json) throws Exception{
		
		if(!json.has("id")) throw new Exception("MetaBlock JSON requires an id field");
		
		blockID = json.get("id").getAsInt();
		meta = json.has("meta") ? json.get("meta").getAsInt() : 0;
		flag = json.has("flag") ? json.get("flag").getAsInt() : 2;
		
	}
	
	public MetaBlock(MetaBlock toCopy){
		this.blockID = toCopy.blockID;
		this.meta = toCopy.meta;
		this.flag = toCopy.flag;
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
	public void setBlock(World world, Random rand, int x, int y, int z) {
		WorldGenPrimitive.setBlock(world, x, y, z, blockID, meta, flag, true, true);
	}

	@Override
	public void setBlock(World world, Random rand, int x, int y, int z, boolean fillAir, boolean replaceSolid) {
		WorldGenPrimitive.setBlock(world, x, y, z, blockID, meta, flag, fillAir, replaceSolid);
	}
}
