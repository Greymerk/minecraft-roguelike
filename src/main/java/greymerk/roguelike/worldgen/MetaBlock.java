package greymerk.roguelike.worldgen;

import java.util.Collection;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;

public class MetaBlock extends BlockFactoryBase implements IBlockState{

	private IBlockState state;
	private int flag;
    
	public MetaBlock(Block block){
		this(block.getDefaultState());
	}
	
	public MetaBlock(IBlockState state){
		this.state = state;
		flag = 2;
	}
	
	public MetaBlock(Block block, IProperty ... properties){
		BlockState s = new BlockState(block, properties);
		this.state = s.getBaseState();
	}
	
	public MetaBlock(JsonElement e){
		
	}
	
	public int getFlag(){
		return flag;
	}
	
	public void setState(IBlockState state){
		this.state = state;
	}
	
	public void setFlag(int in){
		flag = in;
	}

	public boolean setBlock(World world, Coord pos){
		return WorldGenPrimitive.setBlock(world, pos, this.state, this.flag, true, true);
	}
	
	@Override
	public boolean setBlock(World world, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		return WorldGenPrimitive.setBlock(world, pos, this.state, this.flag, fillAir, replaceSolid);
	}

	@Override
	public Collection<?> getPropertyNames() {
		return this.state.getPropertyNames();
	}

	@Override
	public Comparable<?> getValue(IProperty property) {
		return this.state.getValue(property);
	}

	@Override
	public IBlockState withProperty(IProperty property, Comparable value) {
		return this.state.withProperty(property, value);
	}

	@Override
	public IBlockState cycleProperty(IProperty property) {
		return this.state.cycleProperty(property);
	}

	@Override
	public ImmutableMap getProperties() {
		return this.state.getProperties();
	}

	@Override
	public Block getBlock() {
		return this.state.getBlock();
	}
	
}
