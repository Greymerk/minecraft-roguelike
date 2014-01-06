package greymerk.roguelike.catacomb;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.TreasureChestStarter;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.World;

public class CatacombTower {

	private World world;
	private Random rand;	
	private int originX;
	private int originY;
	private int originZ;
	private int towerY;
	
	private int floor1;
	private int floor2;
	private int roof;
	private BlockRandomizer fillBlocks;
	
	public CatacombTower(World world, Random rand, int x, int y, int z){
		this.world = world;
		this.rand = rand;
		this.originX = x;
		this.originY = y;
		this.originZ = z;
		int tempY = 128;
		
		fillBlocks = new BlockRandomizer(rand, new MetaBlock(Block.stoneBrick.blockID));
		fillBlocks.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 3);
		fillBlocks.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 3);
		fillBlocks.addBlock(new MetaBlock(Block.cobblestone.blockID, 1), 5);
		fillBlocks.addBlock(new MetaBlock(Block.gravel.blockID, 1), 20);
		fillBlocks.addBlock(new MetaBlock(0), 20);
		
		
		List<Integer> invalidBlocks = new ArrayList<Integer>();
		invalidBlocks.add(0); // Air
		invalidBlocks.add(Block.wood.blockID);
		invalidBlocks.add(Block.leaves.blockID);
		invalidBlocks.add(Block.cactus.blockID);
		invalidBlocks.add(Block.reed.blockID);
		invalidBlocks.add(Block.vine.blockID);
		invalidBlocks.add(Block.snow.blockID);
		invalidBlocks.add(Block.cocoaPlant.blockID);
		
		int block = world.getBlockId(x, tempY, z);
		
		while(tempY > 60){

			if(invalidBlocks.indexOf(block) == -1){
				break;
			}
			
			tempY = tempY - 1;
			
			block = world.getBlockId(x, tempY, z);
			
		}
		
		towerY = tempY - originY;
		
		if(towerY < 12){
			towerY = 12;
		}
		
		towerY++;
		
		floor1 = originY + towerY;
		floor2 = originY + towerY + 5;
		roof = originY + towerY + 9;
		
	}
	
	public void generate(){
		
		// fill air space
		//WorldGenPrimitive.fillRectSolid(world, originX - 3, originY, originZ - 3, originX + 3, floor1, originZ + 3, 0);
		WorldGenPrimitive.fillRectSolid(world, originX - 4, floor1, originZ - 4, originX + 4, roof, originZ + 4, 0);
		
		
		//sub pillars
		WorldGenPrimitive.fillRectSolid(world, originX - 4, originY, originZ - 4, originX - 3, floor1, originZ - 3, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 4, originY, originZ + 3, originX - 3, floor1, originZ + 4, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 3, originY, originZ - 4, originX + 4, floor1, originZ - 3, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 3, originY, originZ + 3, originX + 4, floor1, originZ + 4, fillBlocks);
		
		// FLOOR1
		
		// floor1 stone base
		WorldGenPrimitive.fillRectSolid(world, originX - 4, floor1, originZ - 4, originX + 4, floor1, originZ + 4, fillBlocks);

		// floor 1 walls, outer
		WorldGenPrimitive.fillRectSolid(world, originX - 5, floor1, originZ - 1, originX - 5, floor2, originZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 5, floor1, originZ - 1, originX + 5, floor2, originZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 1, floor1, originZ - 5, originX + 1, floor2, originZ - 5, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 1, floor1, originZ + 5, originX + 1, floor2, originZ + 5, fillBlocks);
		
		// floor 1 walls, inner
		WorldGenPrimitive.fillRectSolid(world, originX - 4, floor1, originZ - 3, originX - 4, floor2, originZ - 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 4, floor1, originZ + 1, originX - 4, floor2, originZ + 3, fillBlocks);
		
		WorldGenPrimitive.fillRectSolid(world, originX + 4, floor1, originZ - 3, originX + 4, floor2, originZ - 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 4, floor1, originZ + 1, originX + 4, floor2, originZ + 3, fillBlocks);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 3, floor1, originZ - 4, originX - 1, floor2, originZ - 4, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 1, floor1, originZ - 4, originX + 3, floor2, originZ - 4, fillBlocks);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 3, floor1, originZ + 4, originX - 1, floor2, originZ + 4, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 1, floor1, originZ + 4, originX + 3, floor2, originZ + 4, fillBlocks);
		
		// floor 1 glowstones
		if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
			WorldGenPrimitive.setBlock(world, originX - 3, originY + towerY, originZ - 3, Block.glowStone.blockID);
			WorldGenPrimitive.setBlock(world, originX - 3, originY + towerY, originZ + 3, Block.glowStone.blockID);
			WorldGenPrimitive.setBlock(world, originX + 3, originY + towerY, originZ - 3, Block.glowStone.blockID);
			WorldGenPrimitive.setBlock(world, originX + 3, originY + towerY, originZ + 3, Block.glowStone.blockID);
		}
		
		// floor 1 doors
		for (int y = floor1 + 1; y <= floor1 + 2; y++){
			WorldGenPrimitive.setBlock(world, originX - 5, y, originZ, 0);
			WorldGenPrimitive.setBlock(world, originX + 5, y, originZ, 0);
			WorldGenPrimitive.setBlock(world, originX, y, originZ - 5, 0);
			WorldGenPrimitive.setBlock(world, originX, y, originZ + 5, 0);
		}
		
		// FLOOR 2
		
		// floor 2
		WorldGenPrimitive.fillRectSolid(world, originX - 3, floor2 - 1, originZ - 3, originX + 3, floor2 - 1, originZ + 3, Block.stoneBrick.blockID);
		WorldGenPrimitive.fillRectSolid(world, originX - 3, floor2, originZ - 3, originX + 3, floor2, originZ + 3, Block.planks.blockID, 1, 2, true, true);
		
		// floor2 glowstones
		if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
			WorldGenPrimitive.setBlock(world, originX - 3, floor2, originZ - 3, Block.glowStone.blockID);
			WorldGenPrimitive.setBlock(world, originX - 3, floor2, originZ + 3, Block.glowStone.blockID);
			WorldGenPrimitive.setBlock(world, originX + 3, floor2, originZ - 3, Block.glowStone.blockID);
			WorldGenPrimitive.setBlock(world, originX + 3, floor2, originZ + 3, Block.glowStone.blockID);
		}
		
		// outer walls
		WorldGenPrimitive.fillRectSolid(world, originX - 4, floor2, originZ - 3, originX - 4, roof, originZ + 3, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 4, floor2, originZ - 3, originX + 4, roof, originZ + 3, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 3, floor2, originZ - 4, originX + 3, roof, originZ - 4, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 3, floor2, originZ + 4, originX + 3, roof, originZ + 4, fillBlocks);
		
		// upper wall lip
		WorldGenPrimitive.fillRectSolid(world, originX - 5, floor2 + 3, originZ - 1, originX - 5, roof, originZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 5, floor2 + 3, originZ - 1, originX + 5, roof, originZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 1, floor2 + 3, originZ - 5, originX + 1, roof, originZ - 5, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 1, floor2 + 3, originZ + 5, originX + 1, roof, originZ + 5, fillBlocks);
		
		// floor 2 doors
		for (int y = floor2 + 1; y <= floor2 + 2; y++){
			WorldGenPrimitive.setBlock(world, originX - 4, y, originZ, 0);
			WorldGenPrimitive.setBlock(world, originX + 4, y, originZ, 0);
			WorldGenPrimitive.setBlock(world, originX, y, originZ - 4, 0);
			WorldGenPrimitive.setBlock(world, originX, y, originZ + 4, 0);
		}
		
		// floor 2 windows
		WorldGenPrimitive.setBlock(world, originX - 4, floor2 + 2, originZ - 2, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX - 4, floor2 + 2, originZ + 2, Block.fenceIron.blockID);
		
		WorldGenPrimitive.setBlock(world, originX + 4, floor2 + 2, originZ - 2, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX + 4, floor2 + 2, originZ + 2, Block.fenceIron.blockID);
		
		WorldGenPrimitive.setBlock(world, originX - 2, floor2 + 2, originZ - 4, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX + 2, floor2 + 2, originZ - 4, Block.fenceIron.blockID);
		
		WorldGenPrimitive.setBlock(world, originX - 2, floor2 + 2, originZ + 4, Block.fenceIron.blockID);
		WorldGenPrimitive.setBlock(world, originX + 2, floor2 + 2, originZ + 4, Block.fenceIron.blockID);

		// furniture
		if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
			WorldGenPrimitive.setBlock(world, originX + 1, floor2 + 1, originZ - 3, Block.enderChest.blockID);
			if(WorldGenPrimitive.setBlock(world, originX + 2, floor2 + 1, originZ - 3, Block.furnaceIdle.blockID)){
				TileEntityFurnace furnace = (TileEntityFurnace)world.getBlockTileEntity(originX + 2, floor2 + 1, originZ - 3);
				ItemStack coal = new ItemStack(Item.coal, 5 + rand.nextInt(10));
				furnace.setInventorySlotContents(1, coal);
			}
	
			WorldGenPrimitive.setBlock(world, originX - 1, floor2 + 1, originZ - 3, Block.workbench.blockID);
			WorldGenPrimitive.setBlock(world, originX + 3, floor2 + 1, originZ + 1, Block.bed.blockID, 0, 3, true, true);
			WorldGenPrimitive.setBlock(world, originX + 3, floor2 + 1, originZ + 2, Block.bed.blockID, 0 + 8, 3, true, true);
			
			new TreasureChestStarter().generate(world, rand, originX - 3, floor2 + 1, originZ + 2);
			new TreasureChestStarter().generate(world, rand, originX - 3, floor2 + 1, originZ - 2);
		}
		
		// ROOF
		WorldGenPrimitive.fillRectSolid(world, originX - 4, roof, originZ - 4, originX + 4, roof, originZ + 4, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 5, roof, originZ - 1, originX - 5, roof, originZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 5, roof, originZ - 1, originX + 5, roof, originZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 1, roof, originZ - 5, originX + 1, roof, originZ - 5, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 1, roof, originZ + 5, originX + 1, roof, originZ + 5, fillBlocks);
		
		// RIM
		MetaBlock block;
		
		WorldGenPrimitive.fillRectSolid(world, originX - 6, roof, originZ - 1, originX - 6, roof + 1, originZ + 1, fillBlocks);
		addCrenellation(originX - 6, roof + 2, originZ - 1);
		addCrenellation(originX - 6, roof + 2, originZ + 1);
		
		WorldGenPrimitive.fillRectSolid(world, originX + 6, roof, originZ - 1, originX + 6, roof + 1, originZ + 1, fillBlocks);
		addCrenellation(originX + 6, roof + 2, originZ - 1);
		addCrenellation(originX + 6, roof + 2, originZ + 1);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 1, roof, originZ - 6, originX + 1, roof + 1, originZ - 6, fillBlocks);
		addCrenellation(originX - 1, roof + 2, originZ - 6);
		addCrenellation(originX + 1, roof + 2, originZ - 6);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 1, roof, originZ + 6, originX + 1, roof + 1, originZ + 6, fillBlocks);
		addCrenellation(originX - 1, roof + 2, originZ + 6);
		addCrenellation(originX + 1, roof + 2, originZ + 6);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 5, roof, originZ - 3, originX - 5, roof + 1, originZ - 2, fillBlocks);
		addCrenellation(originX - 5, roof + 2, originZ - 3);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 5, roof, originZ + 2, originX - 5, roof + 1, originZ + 3, fillBlocks);
		addCrenellation(originX - 5, roof + 2, originZ + 3);
		
		WorldGenPrimitive.fillRectSolid(world, originX + 5, roof, originZ - 3, originX + 5, roof + 1, originZ - 2, fillBlocks);
		addCrenellation(originX + 5, roof + 2, originZ - 3);
		
		WorldGenPrimitive.fillRectSolid(world, originX + 5, roof, originZ + 2, originX + 5, roof + 1, originZ + 3, fillBlocks);
		addCrenellation(originX + 5, roof + 2, originZ + 3);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 3, roof, originZ - 5, originX - 2, roof + 1, originZ - 5, fillBlocks);
		addCrenellation(originX - 3, roof + 2, originZ - 5);
		
		WorldGenPrimitive.fillRectSolid(world, originX + 2, roof, originZ - 5, originX + 3, roof + 1, originZ - 5, fillBlocks);
		addCrenellation(originX + 3, roof + 2, originZ - 5);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 3, roof, originZ + 5, originX - 2, roof + 1, originZ + 5, fillBlocks);
		addCrenellation(originX - 3, roof + 2, originZ + 5);
		
		WorldGenPrimitive.fillRectSolid(world, originX + 2, roof, originZ + 5, originX + 3, roof + 1, originZ + 5, fillBlocks);
		addCrenellation(originX + 3, roof + 2, originZ + 5);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 4, roof, originZ - 4, originX - 4, roof + 1, originZ - 4, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 4, roof, originZ + 4, originX - 4, roof + 1, originZ + 4, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 4, roof, originZ - 4, originX + 4, roof + 1, originZ - 4, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 4, roof, originZ + 4, originX + 4, roof + 1, originZ + 4, fillBlocks);		
		
		MetaBlock stair = new MetaBlock(Block.stairsStoneBrick.blockID);;
		MetaBlock fill = new MetaBlock(Block.stoneBrick.blockID);
		
		// stairs
		for (int y = originY; y <= floor2; y++){
			WorldGenPrimitive.spiralStairStep(world, originX, y, originZ, stair, fill);
		}
	}
	
	
	private void addCrenellation(int x, int y, int z){
		MetaBlock block = fillBlocks.getMetaBlock();
		
		if(block.getBlockID() == 0){
			return;
		}
		
		WorldGenPrimitive.setBlock(world, x, y, z, block.getBlockID(), block.getMeta(), block.getFlag(), true, true);
		WorldGenPrimitive.setBlock(world, x, y + 1, z, Block.torchWood.blockID);
	}
}
