package greymerk.roguelike.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BlockJumble implements IBlockFactory {

	private List<IBlockFactory> blocks;
	
	public BlockJumble(){
		blocks = new ArrayList<IBlockFactory>();
	}
	
	public BlockJumble(JsonElement data) throws Exception {
		this();
		for(JsonElement entry : (JsonArray)data){
			JsonObject d = entry.getAsJsonObject();
			String type = d.get("type").getAsString();
			JsonElement blockJson = d.get("data");
			IBlockFactory toAdd = BlockFactory.create(type, blockJson);
			this.addBlock(toAdd);
		}
	}

	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}
	
	@Override
	public void setBlock(World world, Random rand, int x, int y, int z) {
		setBlock(world, rand, x, y, z, true, true);
	}

	@Override
	public void setBlock(World world, Random rand, int x, int y, int z, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = blocks.get(rand.nextInt(blocks.size()));
		block.setBlock(world, rand, x, y, z, fillAir, replaceSolid);
	}

}
