package greymerk.roguelike.catacomb.tower;

import greymerk.roguelike.worldgen.Coord;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public enum Tower {

	ROGUE, ENIKO, ETHO, PYRAMID;
	
	public static ITower get(Tower type){
		
		switch(type){
		case ROGUE: return new RogueTower();
		case ENIKO: return new EniTower();
		case ETHO: return new EthoTower();
		case PYRAMID: return new PyramidTower();
		default: return new RogueTower();
		}
	}
	
	public static Coord getBaseCoord(World world, int x, int y, int z){
		
		List<Block> invalidBlocks = new ArrayList<Block>();
		invalidBlocks.add(Blocks.air); // Air
		invalidBlocks.add(Blocks.log);
		invalidBlocks.add(Blocks.log2);
		invalidBlocks.add(Blocks.leaves);
		invalidBlocks.add(Blocks.leaves2);
		invalidBlocks.add(Blocks.cactus);
		invalidBlocks.add(Blocks.reeds);
		invalidBlocks.add(Blocks.vine);
		invalidBlocks.add(Blocks.snow);
		invalidBlocks.add(Blocks.snow_layer);
		invalidBlocks.add(Blocks.cocoa);
		
		int tempY = 128;
		Block block = world.getBlock(x, tempY, z);

		while(tempY > 60){

			if(invalidBlocks.indexOf(block) == -1){
				break;
			}

			tempY = tempY - 1;

			block = world.getBlock(x, tempY, z);

		}

		int yOffset = tempY - y;

		if(yOffset < 14){
			yOffset = 14;
		}
		
		return new Coord(x, y + yOffset, z);

	}
	
}
