package greymerk.roguelike.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class BlockCheckers extends BlockBase {

	private IBlockFactory fillOne;
	private IBlockFactory fillTwo;
	
	public BlockCheckers(IBlockFactory fillOne, IBlockFactory fillTwo){
		this.fillOne = fillOne;
		this.fillTwo = fillTwo;
	}
	
	public BlockCheckers(JsonElement json) {
		JsonArray arr = (JsonArray)json;
		List<IBlockFactory> blocks = new ArrayList<IBlockFactory>(); 
		
		for(JsonElement entry : arr){
			blocks.add(BlockProvider.create(entry.getAsJsonObject()));
		}
		
		this.fillOne = blocks.get(0);
		this.fillTwo = blocks.get(1);
	}

	@Override
	public boolean setBlock(WorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		if (x % 2 == 0) {
			if(z % 2 == 0){
				if(y % 2 == 0){
					return editor.setBlock(rand, new Coord(x, y, z), fillOne, fillAir, replaceSolid);
				} else {
					return editor.setBlock(rand, new Coord(x, y, z), fillTwo, fillAir, replaceSolid);
				}
			} else {
				if(y % 2 == 0){
					return editor.setBlock(rand, new Coord(x, y, z), fillTwo, fillAir, replaceSolid);
				} else {
					return editor.setBlock(rand, new Coord(x, y, z), fillOne, fillAir, replaceSolid);
				}
			}
		} else {
			if(z % 2 == 0){
				if(y % 2 == 0){
					return editor.setBlock(rand, new Coord(x, y, z), fillTwo, fillAir, replaceSolid);
				} else {
					return editor.setBlock(rand, new Coord(x, y, z), fillOne, fillAir, replaceSolid);
				}
			} else {
				if(y % 2 == 0){
					return editor.setBlock(rand, new Coord(x, y, z), fillOne, fillAir, replaceSolid);
				} else {
					return editor.setBlock(rand, new Coord(x, y, z), fillTwo, fillAir, replaceSolid);
				}
			}
		}
	}
}
