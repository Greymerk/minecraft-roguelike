package greymerk.roguelike.worldgen;

import java.util.Collection;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class MetaBlock extends BlockBase implements IBlockState{

	private IBlockState state;
	private int flag;
    
	public MetaBlock(Block block){
		this.state = block.getDefaultState();
		flag = 2;
	}
	
	public MetaBlock(IBlockState state){
		this.state = state;
		flag = 2;
	}
	
	@SuppressWarnings("rawtypes")
	public MetaBlock(Block block, IProperty ... properties){
		BlockState s = new BlockState(block, properties);
		this.state = s.getBaseState();
	}
	
	public MetaBlock(JsonElement e){
		JsonObject json = (JsonObject)e;
		String name = json.get("name").getAsString();
		ResourceLocation location = new ResourceLocation(name);
		Block block = (Block) Block.blockRegistry.getObject(location);
		int meta = json.has("meta") ? json.get("meta").getAsInt() : 0;
		this.state = block.getStateFromMeta(meta);
		flag = json.has("flag") ? json.get("flag").getAsInt() : 2;
	}
	
	public void setState(IBlockState state){
		this.state = state;
	}

	public boolean setBlock(IWorldEditor editor, Coord pos){
		return editor.setBlock(pos, this, true, true);
	}
		
	@Override
	public boolean setBlock(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		return editor.setBlock(pos, this, fillAir, replaceSolid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Collection<IProperty> getPropertyNames() {
		return this.state.getPropertyNames();
	}

	@Override
	public <T extends Comparable<T>> T getValue(IProperty<T> property) {
		return state.getValue(property);
	}
	
	@Override
	public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
		this.state = this.state.withProperty(property, value);
		return this.state;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IBlockState cycleProperty(IProperty property) {
		return this.state.cycleProperty(property);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ImmutableMap<IProperty, Comparable> getProperties(){
		return this.state.getProperties();
	}

	public IBlockState getState(){
		return this.state;
	}
	
	@Override
	public Block getBlock() {
		return this.state.getBlock();
	}
	
	public int getFlag(){
		return this.flag;
	}
	
	@Override
	public String toString(){
		return this.state.getBlock().getUnlocalizedName();
	}




	
}
