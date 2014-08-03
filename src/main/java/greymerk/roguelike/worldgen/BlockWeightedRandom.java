package greymerk.roguelike.worldgen;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

import java.util.Random;

import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BlockWeightedRandom implements IBlockFactory{

	private WeightedRandomizer<IBlockFactory> blocks;
	
	public BlockWeightedRandom(){
		blocks = new WeightedRandomizer<IBlockFactory>();
	}
	
	public BlockWeightedRandom(JsonElement data) {
		this();
		for(JsonElement entry : (JsonArray)data){
			JsonObject d = entry.getAsJsonObject();
			String type = d.get("type").getAsString();
			JsonElement blockJson = d.get("data");
			int weight = d.get("weight").getAsInt();
			IBlockFactory toAdd = BlockFactory.create(type, blockJson);
			this.addBlock(toAdd, weight);
		}
	}

	public void addBlock(IBlockFactory toAdd, int weight){
		blocks.add(new WeightedChoice<IBlockFactory>(toAdd, weight));
	}
	
	@Override
	public void setBlock(World world, Random rand, int x, int y, int z) {
		setBlock(world, rand, x, y, z, true, true);
	}

	@Override
	public void setBlock(World world, Random rand, int x, int y, int z, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = blocks.get(rand);
		block.setBlock(world, rand, x, y, z, fillAir, replaceSolid);
	}
}
