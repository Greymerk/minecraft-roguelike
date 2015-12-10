package greymerk.roguelike.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BlockLayers extends BlockBase{

	private List<IBlockFactory> blocks;
	
	public BlockLayers(){
		blocks = new ArrayList<IBlockFactory>();
	}
	
	public BlockLayers(JsonElement data) {
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
	public boolean setBlock(WorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = this.blocks.get(pos.getY() % this.blocks.size());
		return block.setBlock(editor, rand, pos, fillAir, replaceSolid);
	}

}
