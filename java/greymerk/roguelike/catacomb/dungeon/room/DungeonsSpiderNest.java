package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsSpiderNest implements IDungeon {
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	byte dungeonHeight;
	int dungeonLength;
	int dungeonWidth;
	
	public DungeonsSpiderNest() {
		dungeonHeight = 2;
		dungeonLength = 3;
		dungeonWidth = 3;
	}

	public boolean generate(World inWorld, Random inRandom, ITheme theme, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		BlockWeightedRandom webs = new BlockWeightedRandom();
		webs.addBlock(new MetaBlock(Blocks.web), 3);
		webs.addBlock(new MetaBlock(Blocks.air), 1);
		
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++) {
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
				for (int blockY = originY + dungeonHeight; blockY >= originY - dungeonHeight; blockY--) {
					
					int x = Math.abs(blockX - originX);
					int z = Math.abs(blockZ - originZ);
					
					int clearHeight = x > z ? x : z;
					
					if(blockY == originY) webs.setBlock(inWorld, inRandom, blockX, blockY, blockZ);
					if(clearHeight < 1) clearHeight = 1;
					if(Math.abs(blockY - originY) > clearHeight) continue;
						
					if(rand.nextInt(clearHeight)  == 0){
						webs.setBlock(inWorld, inRandom, blockX, blockY, blockZ);
					} else if(rand.nextInt(5) == 0){
						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, Blocks.gravel);
					}
					
				}
			}
		}
		
		Spawner.generate(world, rand, originX, originY, originZ, Spawner.CAVESPIDER);
		
		TreasureChest.createChests(world, rand, 1 + rand.nextInt(3), WorldGenPrimitive.getRectSolid(
				originX - dungeonLength, originY - 1, originZ - dungeonWidth,
				originX + dungeonLength, originY + 1, originZ + dungeonWidth));

		return true;
	}
	
	public int getSize(){
		return 4;
	}
}
