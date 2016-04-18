package greymerk.roguelike.worldgen;

import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class MetaBlock extends BlockBase {

	private Block block;
	private int meta;
	private int flag;
	
	public MetaBlock(Block block){
		this(block, 0, 2);		
	}
	
	public MetaBlock(Block block, int meta){
		this(block, meta, 2);
	}
	
	public MetaBlock(Block block, int meta, int flag){
		this.block = block;
		this.meta = meta;
		this.flag = flag;
	}
	
	public MetaBlock(JsonElement data){
		JsonObject json = (JsonObject)data;
		String name = json.get("name").getAsString();
		this.block = (Block) Block.blockRegistry.getObject(name);
		meta = json.has("meta") ? json.get("meta").getAsInt() : 0;
		flag = json.has("flag") ? json.get("flag").getAsInt() : 2;
		
	}
	
	public MetaBlock(MetaBlock toCopy){
		this.block = toCopy.block;
		this.meta = toCopy.meta;
		this.flag = toCopy.flag;
	}
	
	public Block getBlock(){
		return block;
	}
	
	public int getMeta(){
		return meta;
	}
	
	public int getFlag(){
		return flag;
	}
	
	public void setBlockID(Block in){
		block = in;
	}
	
	public void setMeta(int in){
		meta = in;
	}
	
	public void setFlag(int in){
		flag = in;
	}

	public boolean set(IWorldEditor editor, Coord pos){
		return editor.setBlock(pos, this, true, true);
	}
	
	public Material getMaterial(){
		return this.block.getMaterial();
	}

	public boolean isOpaqueCube(){
		return this.block.isOpaqueCube();
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		return editor.setBlock(pos, this, fillAir, replaceSolid);
	}

	
}