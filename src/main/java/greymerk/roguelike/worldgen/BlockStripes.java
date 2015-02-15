package greymerk.roguelike.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BlockStripes extends BlockFactoryBase {

	private List<IBlockFactory> blocks;
	
	public BlockStripes(){
		blocks = new ArrayList<IBlockFactory>();
	}
	
	public BlockStripes(JsonElement data) {
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
	public boolean setBlock(World world, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		int size = blocks.size();
		int choice = Math.abs((origin.getX() % size + origin.getY() % size + origin.getZ() % size)) % size;
		IBlockFactory block = blocks.get(choice);
		return block.setBlock(world, rand, origin, fillAir, replaceSolid);
	}
}
