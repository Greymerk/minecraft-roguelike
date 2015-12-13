package greymerk.roguelike.worldgen;

import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class BlockWeightedRandom extends BlockBase {

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
			IBlockFactory toAdd = BlockProvider.create(type, blockJson);
			this.addBlock(toAdd, weight);
		}
	}

	public void addBlock(IBlockFactory toAdd, int weight){
		blocks.add(new WeightedChoice<IBlockFactory>(toAdd, weight));
	}

	@Override
	public boolean setBlock(WorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = blocks.get(rand);
		return block.setBlock(editor, rand, origin, fillAir, replaceSolid);
	}
}
