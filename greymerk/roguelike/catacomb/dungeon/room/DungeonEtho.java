package greymerk.roguelike.catacomb.dungeon.room;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureChestEmpty;
import greymerk.roguelike.treasure.loot.ItemNovelty;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

public class DungeonEtho implements IDungeon {

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z - 5, x + 5, y + 3, z + 5, 0);
		
		BlockRandomizer leafJumble = new BlockRandomizer(rand, new MetaBlock(0));
		leafJumble.addBlock(new MetaBlock(Block.leaves.blockID, 4), 2);
		leafJumble.addBlock(new MetaBlock(Block.wood.blockID), 20);
		
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 6, z - 4, x + 4, y + 6, z + 4, Block.wood.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 4, z - 4, x + 4, y + 5, z + 4, leafJumble);
		

		// corner pools
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z - 5, x - 2, y - 1, z - 2, Block.planks.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y - 1, z - 4, x - 3, y - 1, z - 3, Block.waterStill.blockID);
		WorldGenPrimitive.setBlock(world, x - 5, y, z - 5, Block.waterMoving.blockID);

		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z + 2, x + 5, y - 1, z + 5, Block.planks.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y - 1, z + 3, x + 4, y - 1, z + 4, Block.waterStill.blockID);
		WorldGenPrimitive.setBlock(world, x + 5, y, z + 5, Block.waterMoving.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z - 5, x + 5, y - 1, z - 2, Block.planks.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y - 1, z - 4, x + 4, y - 1, z - 3, Block.waterStill.blockID);
		WorldGenPrimitive.setBlock(world, x + 5, y, z - 5, Block.waterMoving.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z + 2, x - 2, y - 1, z + 5, Block.planks.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y - 1, z + 3, x - 3, y - 1, z + 4, Block.waterStill.blockID);
		WorldGenPrimitive.setBlock(world, x - 5, y, z + 5, Block.waterMoving.blockID);
		
		// alcove walls with diamonds
		BlockRandomizer ethoStone = new BlockRandomizer(rand, new MetaBlock(Block.stone.blockID));
		ethoStone.addBlock(new MetaBlock(Block.oreDiamond.blockID), 15);
		
		WorldGenPrimitive.fillRectSolid(world, x - 6, y, z - 5, x - 6, y + 4, z - 4, ethoStone);
		WorldGenPrimitive.fillRectSolid(world, x - 6, y, z + 4, x - 6, y + 4, z + 5, ethoStone);
		
		WorldGenPrimitive.fillRectSolid(world, x + 6, y, z - 5, x + 6, y + 4, z - 4, ethoStone);
		WorldGenPrimitive.fillRectSolid(world, x + 6, y, z + 4, x + 6, y + 4, z + 5, ethoStone);
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z - 6, x - 4, y + 4, z - 6, ethoStone);
		WorldGenPrimitive.fillRectSolid(world, x + 4, y, z - 6, x + 5, y + 4, z - 6, ethoStone);
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z + 6, x - 4, y + 4, z + 6, ethoStone);
		WorldGenPrimitive.fillRectSolid(world, x + 4, y, z + 6, x + 5, y + 4, z + 6, ethoStone);
		
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 5, x + 1, y - 1, z + 5, Block.grass.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z - 1, x + 5, y - 1, z + 1, Block.grass.blockID);
		
		
		MetaBlock jungleLeaf = new MetaBlock(Block.leaves.blockID, 3);
		
		// north

		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z - 6, x + 1, y + 2, z - 6, 0);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z - 6, x - 2, y + 2, z - 6, Block.wood.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z - 6, x + 2, y + 2, z - 6, Block.wood.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x - 3, y - 1, z - 5, x - 3, y + 2, z - 5, Block.wood.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y - 1, z - 5, x + 3, y + 2, z - 5, Block.wood.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 3, z - 6, x + 2, y + 3, z - 6, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z - 6, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z - 6, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z - 6, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z - 6, Block.planks.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 4, z - 5, x + 2, y + 4, z - 5, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 2, y + 3, z - 5, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 3, z - 5, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));

		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 5, z - 4, x + 2, y + 5, z - 4, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 2, y + 4, z - 4, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 4, z - 4, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));
		
		WorldGenPrimitive.setBlock(world, x - 3, y + 3, z - 4, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x + 3, y + 3, z - 4, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		
		WorldGenPrimitive.setBlock(world, x - 2, y - 2, z - 5, Block.glowStone.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z - 5, x - 2, y, z - 5, jungleLeaf, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 2, y - 2, z - 5, Block.glowStone.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z - 5, x + 2, y, z - 5, jungleLeaf, true, true);
		
		// south
		
		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z + 6, x + 1, y + 2, z + 6, 0);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z + 6, x - 2, y + 2, z + 6, Block.wood.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z + 6, x + 2, y + 2, z + 6, Block.wood.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x - 3, y - 1, z + 5, x - 3, y + 2, z + 5, Block.wood.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y - 1, z + 5, x + 3, y + 2, z + 5, Block.wood.blockID);

		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 3, z + 6, x + 2, y + 3, z + 6, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z + 6, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z + 6, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z + 6, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z + 6, Block.planks.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 4, z + 5, x + 2, y + 4, z + 5, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 2, y + 3, z + 5, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 3, z + 5, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));

		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 5, z + 4, x + 2, y + 5, z + 4, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 2, y + 4, z + 4, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 4, z + 4, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));

		WorldGenPrimitive.setBlock(world, x - 3, y + 3, z + 4, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));
		WorldGenPrimitive.setBlock(world, x + 3, y + 3, z + 4, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));
		
		WorldGenPrimitive.setBlock(world, x - 2, y - 2, z + 5, Block.glowStone.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z + 5, x - 2, y, z + 5, jungleLeaf, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 2, y - 2, z + 5, Block.glowStone.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z + 5, x + 2, y, z + 5, jungleLeaf, true, true);
		
		
		// west
		
		WorldGenPrimitive.fillRectSolid(world, x - 6, y, z - 1, x - 6, y + 2, z + 1, 0);
		
		WorldGenPrimitive.fillRectSolid(world, x - 6, y - 1, z - 2, x - 6, y + 2, z - 2, Block.wood.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 6, y - 1, z + 2, x - 6, y + 2, z + 2, Block.wood.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z - 3, x - 5, y + 3, z - 3, Block.wood.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z + 3, x - 5, y + 3, z + 3, Block.wood.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x - 6, y + 3, z - 2, x - 6, y + 3, z + 2, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 6, y + 2, z - 2, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 6, y + 2, z - 1, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x - 6, y + 2, z + 1, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));
		WorldGenPrimitive.setBlock(world, x - 6, y + 2, z + 2, Block.planks.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 4, z - 2, x - 5, y + 4, z + 2, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 5, y + 3, z - 2, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x - 5, y + 3, z + 2, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));

		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 5, z - 2, x - 4, y + 5, z + 2, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x - 4, y + 4, z - 2, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x - 4, y + 4, z + 2, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));

		WorldGenPrimitive.setBlock(world, x - 4, y + 3, z - 3, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x - 4, y + 3, z + 3, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));

		WorldGenPrimitive.setBlock(world, x - 5, y - 2, z - 2, Block.glowStone.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z - 2, x - 5, y, z - 2, jungleLeaf, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 6, y - 2, z + 2, Block.glowStone.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z + 2, x - 5, y, z + 2, jungleLeaf, true, true);
		
		
		// east
		
		WorldGenPrimitive.fillRectSolid(world, x + 6, y, z - 1, x + 6, y + 2, z + 1, 0);
		
		WorldGenPrimitive.fillRectSolid(world, x + 6, y - 1, z - 2, x + 6, y + 2, z - 2, Block.wood.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 6, y - 1, z + 2, x + 6, y + 2, z + 2, Block.wood.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x + 5, y - 1, z - 3, x + 5, y + 3, z - 3, Block.wood.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 5, y - 1, z + 3, x + 5, y + 3, z + 3, Block.wood.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x + 6, y + 3, z - 2, x + 6, y + 3, z + 2, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x + 6, y + 2, z - 2, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x + 6, y + 2, z - 1, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x + 6, y + 2, z + 1, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));
		WorldGenPrimitive.setBlock(world, x + 6, y + 2, z + 2, Block.planks.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x + 5, y + 4, z - 2, x + 5, y + 4, z + 2, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x + 5, y + 3, z - 2, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x + 5, y + 3, z + 2, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));

		WorldGenPrimitive.fillRectSolid(world, x + 4, y + 5, z - 2, x + 4, y + 5, z + 2, Block.planks.blockID);
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z - 2, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z + 2, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));

		WorldGenPrimitive.setBlock(world, x + 4, y + 3, z - 3, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));
		WorldGenPrimitive.setBlock(world, x + 4, y + 3, z + 3, new MetaBlock(Block.stairsWoodOak.blockID, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));

		WorldGenPrimitive.fillRectSolid(world, x - 3, y + 4, z - 3, x - 3, y + 5, z - 3, Block.planks.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 3, y + 4, z + 3, x - 3, y + 5, z + 3, Block.planks.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 4, z - 3, x + 3, y + 5, z - 3, Block.planks.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 4, z + 3, x + 3, y + 5, z + 3, Block.planks.blockID);
		
		WorldGenPrimitive.setBlock(world, x + 5, y - 2, z - 2, Block.glowStone.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 5, y - 1, z - 2, x + 5, y, z - 2, jungleLeaf, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 5, y - 2, z + 2, Block.glowStone.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 5, y - 1, z + 2, x + 5, y, z + 2, jungleLeaf, true, true);
		
		ITreasureChest chest = new TreasureChestEmpty();
		chest.generate(world, rand, x - 4, y - 2, z - 4);
		
		ItemStack yourMum = new ItemStack(Item.hoeWood);
		Loot.setItemName(yourMum, "Your Mum", null);
		int middle = chest.getInventorySize() / 2;
		int mumSlot = rand.nextInt(chest.getInventorySize());
		if (mumSlot == middle){
			mumSlot += rand.nextInt(5);
		}
		chest.setInventorySlot(yourMum, mumSlot);
		
		chest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.ETHO), middle);
		
		return true;
	}
	
	public int getSize(){
		return 8;
	}

}
