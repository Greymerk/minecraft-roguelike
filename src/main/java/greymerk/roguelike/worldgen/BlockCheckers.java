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
	public boolean setBlock(IWorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		if (x % 2 == 0) {
			if(z % 2 == 0){
				if(y % 2 == 0){
					return fillOne.setBlock(editor, rand, new Coord(origin), fillAir, replaceSolid);
				} else {
					return fillTwo.setBlock(editor, rand, new Coord(origin), fillAir, replaceSolid);
				}
			} else {
				if(y % 2 == 0){
					return fillTwo.setBlock(editor, rand, new Coord(origin), fillAir, replaceSolid);
				} else {
					return fillOne.setBlock(editor, rand, new Coord(origin), fillAir, replaceSolid);
				}
			}
		} else {
			if(z % 2 == 0){
				if(y % 2 == 0){
					return fillTwo.setBlock(editor, rand, new Coord(origin), fillAir, replaceSolid);
				} else {
					return fillOne.setBlock(editor, rand, new Coord(origin), fillAir, replaceSolid);
				}
			} else {
				if(y % 2 == 0){
					return fillOne.setBlock(editor, rand, new Coord(origin), fillAir, replaceSolid);
				} else {
					return fillTwo.setBlock(editor, rand, new Coord(origin), fillAir, replaceSolid);
				}
			}
		}
	}
}
