package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsPit extends DungeonBase {
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
	IBlockFactory blocks;
	
	public DungeonsPit() {
		dungeonHeight = 3;
		dungeonLength = 2;
		dungeonWidth = 2;
	}

	public boolean generate(World inWorld, Random inRandom, CatacombLevelSettings settings, Cardinal[] entrances, int inOriginX, int inOriginY, int inOriginZ) {
		
		ITheme theme = settings.getTheme();
		
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		blocks = theme.getPrimaryWall();
		
		buildWalls();
		buildFloor();
		buildRoof();
		buildPit();
		

		for(int dir = 0; dir < 4; dir++){
			setTrap(dir);
		}
		
		List<Coord> space = new ArrayList<Coord>();
		space.add(new Coord(originX - 2, originY, originZ - 2));
		space.add(new Coord(originX - 2, originY, originZ + 2));
		space.add(new Coord(originX + 2, originY, originZ - 2));
		space.add(new Coord(originX + 2, originY, originZ + 2));
		
		TreasureChest.createChests(inWorld, inRandom, settings.getLoot(), 1, space);
		
		return true;
	}


	protected void buildWalls() {
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++) {
			for (int blockY = originY + dungeonHeight; blockY >= originY - 1; blockY--) {
				for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
					if (blockX == originX - dungeonLength - 1
							|| blockZ == originZ - dungeonWidth - 1
							|| blockX == originX + dungeonLength + 1
							|| blockZ == originZ + dungeonWidth + 1){

						if (blockY >= 0 && !world.getBlock(blockX, blockY - 1, blockZ).getMaterial().isSolid()) {
							WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, Blocks.air);
							continue;
						}
						
						if (!world.getBlock(blockX, blockY, blockZ).getMaterial().isSolid()) continue;
						
						blocks.setBlock(world, rand, blockX, blockY, blockZ);
						
					} else {
						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, new MetaBlock(Blocks.air));
					}
				}
			}
		}
	}
	
	protected void buildFloor(){
		
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++){
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){
				blocks.setBlock(world, rand, blockX, originY - 1, blockZ);				
			}
		}
	}
	
	protected void buildRoof(){
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++){
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){
				blocks.setBlock(world, rand, blockX, dungeonHeight + 1, blockZ);
			}
		}
	}

	private void buildPit(){
		
		for(int x = originX - 2; x <= originX + 2; x++){
			for(int z = originZ - 2; z <= originZ + 2; z++){
				for(int y = originY - 1; y > 0; y--){
					
					if(world.getBlock(x, y, z) == Blocks.air){
						continue;
					}
					
					if(y < 0 + rand.nextInt(5) && world.getBlock(x, y, z) == Blocks.bedrock){
						continue;
					}
					
					if(    x == originX - 2
						|| x == originX +2
						|| z == originZ -2
						|| z == originZ +2){
						
						blocks.setBlock(world, rand, x, y, z, true, true);
						continue;
						
					}
					
					if(y < 10){
						WorldGenPrimitive.setBlock(world, x, y, z, Blocks.flowing_water);
						continue;
					}
					
					WorldGenPrimitive.setBlock(world, x, y, z, Blocks.air);
				}
			}
		}
	}
	
	private void setTrap(int dir){
		
		switch(dir){
		
		// South
		case 0: 
			

			for (int x = originX - 1; x <= originX + 1; x++){
				for (int z = originZ + 6; z >= originZ + 3; z--){
					for (int y = originY - 2; y <= originY + 3; y++){
						if(world.getBlock(x, y, z) == Blocks.air){
							return;
						}
					}
				}
			}
			
			WorldGenPrimitive.setBlock(world, originX, originY, originZ + 2, Blocks.stone_pressure_plate);
			WorldGenPrimitive.setBlock(world, originX, originY - 1, originZ + 3, Blocks.redstone_torch, 3, 2, true, true);
			WorldGenPrimitive.setBlock(world, originX, originY - 1, originZ + 4, Blocks.redstone_wire);
			WorldGenPrimitive.setBlock(world, originX, originY, originZ + 5, Blocks.unlit_redstone_torch, 5, 2, true, true);
			WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ + 4, Blocks.sticky_piston, 2, 2, true, true);
			break;
			
			
		// West
		case 1:
			
			for (int x = originX - 6; x <= originX - 3; x++){
				for (int z = originZ - 1; z <= originZ + 1; z++){
					for (int y = originY - 2; y <= originY + 3; y++){
						if(world.getBlock(x, y, z) == Blocks.air){
							return;
						}
					}
				}
			}
			
			WorldGenPrimitive.setBlock(world, originX - 2, originY, originZ, Blocks.stone_pressure_plate);
			WorldGenPrimitive.setBlock(world, originX - 3, originY - 1, originZ, Blocks.redstone_torch, 2, 2, true, true);
			WorldGenPrimitive.setBlock(world, originX - 4, originY - 1, originZ, Blocks.redstone_wire);
			WorldGenPrimitive.setBlock(world, originX - 5, originY, originZ, Blocks.unlit_redstone_torch, 5, 2, true, true);
			WorldGenPrimitive.setBlock(world, originX - 4, originY + 1, originZ, Blocks.sticky_piston, 5, 2, true, true);
			break;
			
		// North
		case 2:

			for (int x = originX - 1; x <= originX + 1; x++){
				for (int z = originZ - 6; z <= originZ - 3; z++){
					for (int y = originY - 2; y <= originY + 3; y++){
						if(world.getBlock(x, y, z) == Blocks.air){
							return;
						}
					}
				}
			}
			
			WorldGenPrimitive.setBlock(world, originX, originY, originZ - 2, Blocks.stone_pressure_plate);
			WorldGenPrimitive.setBlock(world, originX, originY - 1, originZ - 3, Blocks.redstone_torch, 4, 2, true, true);
			WorldGenPrimitive.setBlock(world, originX, originY - 1, originZ - 4, Blocks.redstone_wire);
			WorldGenPrimitive.setBlock(world, originX, originY, originZ - 5, Blocks.unlit_redstone_torch, 5, 2, true, true);
			WorldGenPrimitive.setBlock(world, originX, originY + 1, originZ - 4, Blocks.sticky_piston, 3, 2, true, true);
			break;
			
		// East 
		case 3:

			for (int x = originX + 6; x >= originX + 3; x--){
				for (int z = originZ - 1; z <= originZ + 1; z++){
					for (int y = originY - 2; y <= originY + 3; y++){
						if(world.getBlock(x, y, z) == Blocks.air){
							return;
						}
					}
				}
			}
			
			WorldGenPrimitive.setBlock(world, originX + 2, originY, originZ, Blocks.stone_pressure_plate);
			WorldGenPrimitive.setBlock(world, originX + 3, originY - 1, originZ, Blocks.redstone_torch, 1, 2, true, true);
			WorldGenPrimitive.setBlock(world, originX + 4, originY - 1, originZ, Blocks.redstone_wire);
			WorldGenPrimitive.setBlock(world, originX + 5, originY, originZ, Blocks.unlit_redstone_torch, 5, 2, true, true);
			WorldGenPrimitive.setBlock(world, originX + 4, originY + 1, originZ, Blocks.sticky_piston, 4, 2, true, true);
			break;
		}
	}
	
	public int getSize(){
		return 4;
	}
}
