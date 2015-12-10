package greymerk.roguelike.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BlockJumble extends BlockBase {

	private List<IBlockFactory> blocks;
	
	public BlockJumble(){
		blocks = new ArrayList<IBlockFactory>();
	}
	
	public BlockJumble(JsonElement data) {
		this();
		for(JsonElement entry : (JsonArray)data){
			JsonObject d = entry.getAsJsonObject();
			String type = d.get("type").getAsString();
			JsonElement blockJson = d.get("data");
			IBlockFactory toAdd = BlockProvider.create(type, blockJson);
			this.addBlock(toAdd);
		}
	}

	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}

	@Override
	public boolean setBlock(WorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = blocks.get(rand.nextInt(blocks.size()));
		return block.setBlock(editor, rand, origin, fillAir, replaceSolid);
	}

}
