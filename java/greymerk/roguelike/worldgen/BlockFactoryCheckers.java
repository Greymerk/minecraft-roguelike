package greymerk.roguelike.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BlockFactoryCheckers implements IBlockFactory {

	private IBlockFactory fillOne;
	private IBlockFactory fillTwo;
	
	public BlockFactoryCheckers(IBlockFactory fillOne, IBlockFactory fillTwo){
		this.fillOne = fillOne;
		this.fillTwo = fillTwo;
	}
	
	public BlockFactoryCheckers(JsonElement json) throws Exception{
		JsonArray arr = (JsonArray)json;
		List<IBlockFactory> blocks = new ArrayList<IBlockFactory>(); 
		
		for(JsonElement entry : arr){
			JsonObject d = entry.getAsJsonObject();
			String type = d.get("type").getAsString();
			JsonElement blockJson = d.get("data");
			blocks.add(BlockFactory.create(type, blockJson));
		}
		
		this.fillOne = blocks.get(0);
		this.fillTwo = blocks.get(1);
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
					WorldGenPrimitive.setBlock(world, rand, x, y, z, fillOne, fillAir, replaceSolid);
				} else {
					WorldGenPrimitive.setBlock(world, rand, x, y, z, fillTwo, fillAir, replaceSolid);
				}
			} else {
				if(y % 2 == 0){
					WorldGenPrimitive.setBlock(world, rand, x, y, z, fillTwo, fillAir, replaceSolid);
				} else {
					WorldGenPrimitive.setBlock(world, rand, x, y, z, fillOne, fillAir, replaceSolid);
				}
			}
		} else {
			if(z % 2 == 0){
				if(y % 2 == 0){
					WorldGenPrimitive.setBlock(world, rand, x, y, z, fillTwo, fillAir, replaceSolid);
				} else {
					WorldGenPrimitive.setBlock(world, rand, x, y, z, fillOne, fillAir, replaceSolid);
				}
			} else {
				if(y % 2 == 0){
					WorldGenPrimitive.setBlock(world, rand, x, y, z, fillOne, fillAir, replaceSolid);
				} else {
					WorldGenPrimitive.setBlock(world, rand, x, y, z, fillTwo, fillAir, replaceSolid);
				}
			}
		}
	}
}
