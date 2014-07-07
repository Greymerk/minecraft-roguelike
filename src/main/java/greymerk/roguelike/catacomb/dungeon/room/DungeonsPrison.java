package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsPrison extends DungeonBase {

	World world;
	Random rand;
	IBlockFactory blocks;
	IBlockFactory pillar;
	
	public DungeonsPrison(){}
	
	@Override
	public boolean generate(World inWorld, Random inRandom, ITheme theme, Cardinal[] entrances, int inOriginX, int inOriginY, int inOriginZ) {
		
		world = inWorld;
		rand = inRandom;
		
		blocks = theme.getPrimaryWall();
		pillar = theme.getPrimaryPillar();
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// clear air
		WorldGenPrimitive.fillRectSolid(inWorld, rand, inOriginX - 7, inOriginY, inOriginZ - 7, inOriginX + 7, inOriginY + 3, inOriginZ + 7, air);
		
		// create outer walls
		WorldGenPrimitive.fillRectHollow(world, rand, inOriginX - 8, inOriginY - 1, inOriginZ - 8, inOriginX + 8, inOriginY + 5, inOriginZ + 8, blocks, false, true);
		
		// fill hallway ceiling beams
		WorldGenPrimitive.fillRectSolid(world, rand, inOriginX - 7, inOriginY + 3, inOriginZ - 2, inOriginX + 7, inOriginY + 3, inOriginZ - 2, blocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inOriginX - 7, inOriginY + 3, inOriginZ + 2, inOriginX + 7, inOriginY + 3, inOriginZ + 2, blocks);
		
		WorldGenPrimitive.fillRectSolid(world, rand, inOriginX - 2, inOriginY + 3, inOriginZ - 7, inOriginX - 2, inOriginY + 3, inOriginZ + 7, blocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inOriginX + 2, inOriginY + 3, inOriginZ - 7, inOriginX + 2, inOriginY + 3, inOriginZ + 7, blocks);
		
		// fill hallway roofs
		WorldGenPrimitive.fillRectSolid(world, rand, inOriginX - 7, inOriginY + 4, inOriginZ - 1, inOriginX - 2, inOriginY + 4, inOriginZ + 1, blocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inOriginX + 2, inOriginY + 4, inOriginZ - 1, inOriginX + 7, inOriginY + 4, inOriginZ + 1, blocks);

		WorldGenPrimitive.fillRectSolid(world, rand, inOriginX - 1, inOriginY + 4, inOriginZ - 7, inOriginX + 1, inOriginY + 4, inOriginZ - 2, blocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inOriginX - 1, inOriginY + 4, inOriginZ + 2, inOriginX + 1, inOriginY + 4, inOriginZ + 7, blocks);
		
		WorldGenPrimitive.fillRectSolid(world, rand, inOriginX - 1, inOriginY + 4, inOriginZ - 1, inOriginX + 1, inOriginY + 4, inOriginZ + 1, blocks);
		

		
		// create cells
		createCell(inOriginX - 5, inOriginY, inOriginZ - 5);
		createCell(inOriginX - 5, inOriginY, inOriginZ + 5);
		createCell(inOriginX + 5, inOriginY, inOriginZ - 5);
		createCell(inOriginX + 5, inOriginY, inOriginZ + 5);
		
		return false;
	}
	
	
	private void createCell(int inX, int inY, int inZ){
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 3, inY - 1, inZ - 3, inX + 3, inY - 1, inZ + 3, blocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY - 1, inZ - 1, inX + 1, inY - 1, inZ + 1, new MetaBlock(Blocks.mossy_cobblestone));
		
		// pillars
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, inY, inZ - 2, inX - 2, inY + 2, inZ - 2, pillar);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, inY, inZ + 2, inX - 2, inY + 2, inZ + 2, pillar);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, inY, inZ - 2, inX + 2, inY + 2, inZ - 2, pillar);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, inY, inZ + 2, inX + 2, inY + 2, inZ + 2, pillar);
		
		// roof
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 3, inY + 3, inZ - 3, inX + 3, inY + 6, inZ + 3, blocks);
		
		
		// torches
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY + 4, inZ - 2, inX + 1, inY + 4, inZ - 2, air);
		WorldGenPrimitive.setBlock(world, inX, inY + 4, inZ - 2, Blocks.redstone_torch);
		
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY + 4, inZ + 2, inX + 1, inY + 4, inZ + 2, air);
		WorldGenPrimitive.setBlock(world, inX, inY + 4, inZ + 2, Blocks.redstone_torch);
		
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, inY + 4, inZ - 1, inX - 2, inY + 4, inZ + 1, air);
		WorldGenPrimitive.setBlock(world, inX - 2, inY + 4, inZ, Blocks.redstone_torch);

		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, inY + 4, inZ - 1, inX + 2, inY + 4, inZ + 1, air);
		WorldGenPrimitive.setBlock(world, inX + 2, inY + 4, inZ, Blocks.redstone_torch);

		// ceiling holes
		WorldGenPrimitive.fillRectSolid(world, rand, inX, inY + 3, inZ, inX, inY + 6, inZ, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY + 3, inZ - 1, inX - 1, inY + 6, inZ - 1, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY + 3, inZ + 1, inX - 1, inY + 6, inZ + 1, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 1, inY + 3, inZ - 1, inX + 1, inY + 6, inZ - 1, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 1, inY + 3, inZ + 1, inX + 1, inY + 6, inZ + 1, air);
		
		MetaBlock bars = new MetaBlock(Blocks.iron_bars);
		
		// bars
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY, inZ - 2, inX + 1, inY + 2, inZ - 2, bars);
		WorldGenPrimitive.fillRectSolid(world, rand, inX, inY, inZ - 2, inX, inY + 1, inZ - 2, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY, inZ + 2, inX + 1, inY + 2, inZ + 2, bars);
		WorldGenPrimitive.fillRectSolid(world, rand, inX, inY, inZ + 2, inX, inY + 1, inZ + 2, air);
		
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, inY, inZ - 1, inX - 2, inY + 2, inZ + 1, bars);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, inY, inZ, inX - 2, inY + 1, inZ, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, inY, inZ - 1, inX + 2, inY + 2, inZ + 1, bars);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, inY, inZ, inX + 2, inY + 1, inZ, air);
		
		if(rand.nextBoolean()){
			switch(rand.nextInt(4)){
			case 0:
				Spawner.generate(world, rand, inX - 2, inY + 4, inZ - 2, Spawner.ZOMBIE);
				break;
			case 1:
				Spawner.generate(world, rand, inX - 2, inY + 4, inZ + 2, Spawner.ZOMBIE);
				break;
			case 2:
				Spawner.generate(world, rand, inX + 2, inY + 4, inZ - 2, Spawner.ZOMBIE);
				break;
			case 3:
				Spawner.generate(world, rand, inX + 2, inY + 4, inZ + 2, Spawner.ZOMBIE);
				break;
			}
		}
		
		if(rand.nextBoolean()){
			TreasureChest.generate(world, rand, inX, inY, inZ);	
		}
		
	}
	
	public int getSize(){
		return 10;
	}
}
