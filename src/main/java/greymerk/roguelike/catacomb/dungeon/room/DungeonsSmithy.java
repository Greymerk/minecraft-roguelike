package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class DungeonsSmithy extends DungeonBase {

	
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	byte dungeonHeight;
	int dungeonLength;
	int dungeonWidth;
	int woolColor;
	int numChests;
	
	int floorBlock;
	int wallBlock;
	
	public DungeonsSmithy() {
		
		dungeonHeight = 4;
		dungeonLength = 2;
		dungeonWidth = 2;
		numChests = 0;
				
	}

	public boolean generate(World inWorld, Random inRandom, ITheme theme, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		
		
		// clear air space
		WorldGenPrimitive.fillRectSolid(world, inRandom, originX - 2, originY, originZ - 2,
												originX + 2, originY + 4, originZ + 2, new MetaBlock(Blocks.air));
		
		buildWalls();
		buildFloor();
		buildRoof();
		
		MetaBlock anvil = new MetaBlock(Blocks.anvil, RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 0 : 8);
		
		WorldGenPrimitive.setBlock(world, inOriginX, inOriginY, inOriginZ, anvil);
		
		List<Coord> space = new ArrayList<Coord>();
		space.add(new Coord(originX - 2, originY, originZ - 2));
		space.add(new Coord(originX - 2, originY, originZ + 2));
		space.add(new Coord(originX + 2, originY, originZ - 2));
		space.add(new Coord(originX + 2, originY, originZ + 2));
		
		TreasureChest.generate(inWorld, rand, space, TreasureChest.SMITH);
		
		return true;
	}
	
	protected void buildRoof(){
		
		List<Coord> walls = WorldGenPrimitive.getRectHollow(
				originX - dungeonLength - 1, originY + dungeonHeight + 1, originZ - dungeonWidth - 1,
				originX + dungeonLength + 1, originY + dungeonHeight + 1, originZ + dungeonWidth + 1);

		for (Coord block : walls){
		
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			WorldGenPrimitive.setBlock(world, x, y, z, Blocks.stonebrick, rand.nextInt(3), 2, false, true);
		}
	}
    
	protected void buildFloor(){
		
		WorldGenPrimitive.fillRectSolid(world, rand, 
				originX - dungeonLength - 1, originY - 1, originZ - dungeonWidth - 1,
				originX + dungeonLength + 1, originY - 1, originZ + dungeonWidth + 1, new MetaBlock(Blocks.brick_block));
	}

	protected void buildWalls() {
		
		List<Coord> walls = WorldGenPrimitive.getRectHollow(
							originX - dungeonLength - 1, originY - 1, originZ - dungeonWidth - 1,
							originX + dungeonLength + 1, originY + dungeonHeight, originZ + dungeonWidth + 1);
		
		for (Coord block : walls){
		
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			Block blockID;
			
			blockID = Blocks.cobblestone;
			
			if(rand.nextInt(300) == 0){
				blockID = Blocks.iron_block;
			}
			
			if(rand.nextInt(5) == 0){
				blockID = Blocks.iron_bars;
			}

			if(y == (originY + dungeonHeight)){
				blockID = Blocks.stonebrick;
			}
			
			if(y == originY && (z == originZ || x == originX)){
				blockID = Blocks.glowstone;
			}
			
			if(y == originY + 1 && (z == originZ || x == originX) && !world.isAirBlock(x, y, z)){
				blockID = Blocks.furnace;
				if(WorldGenPrimitive.setBlock(world, x, y, z, blockID)){
					TileEntityFurnace furnace = (TileEntityFurnace)world.getTileEntity(x, y, z);
					
					ItemStack coal = new ItemStack(Items.coal, 5 + rand.nextInt(10));
					furnace.setInventorySlotContents(1, coal);
				}
				continue;
			}
			
			WorldGenPrimitive.setBlock(world, x, y, z, blockID, 0, 2, false, true);
			
		}
	}
	
	public int getSize(){
		return 5;
	}
	
}
