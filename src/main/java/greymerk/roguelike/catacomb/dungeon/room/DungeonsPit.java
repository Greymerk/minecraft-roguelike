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

	public boolean generate(World inWorld, Random inRandom, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();
		
		world = inWorld;
		rand = inRandom;
		originX = origin.getX();
		originY = origin.getY();
		originZ = origin.getZ();

		blocks = theme.getPrimaryWall();
		
		buildWalls();
		buildFloor();
		buildRoof();
		buildPit();
		

		for(int dir = 0; dir < 4; dir++){
			// @TODO: redo the traps
		}
		
		List<Coord> space = new ArrayList<Coord>();
		space.add(new Coord(originX - 2, originY, originZ - 2));
		space.add(new Coord(originX - 2, originY, originZ + 2));
		space.add(new Coord(originX + 2, originY, originZ - 2));
		space.add(new Coord(originX + 2, originY, originZ + 2));
		
		TreasureChest.createChests(inWorld, inRandom, settings, 1, space);
		
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

						if (blockY >= 0 && !WorldGenPrimitive.getBlock(world, new Coord(blockX, blockY - 1, blockZ)).getBlock().getMaterial().isSolid()) {
							WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, Blocks.air);
							continue;
						}
						
						if (!WorldGenPrimitive.getBlock(world, new Coord(blockX, blockY, blockZ)).getBlock().getMaterial().isSolid()) continue;
						
						blocks.setBlock(world, rand, new Coord(blockX, blockY, blockZ));
						
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
				blocks.setBlock(world, rand, new Coord(blockX, originY - 1, blockZ));				
			}
		}
	}
	
	protected void buildRoof(){
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++){
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){
				blocks.setBlock(world, rand, new Coord(blockX, dungeonHeight + 1, blockZ));
			}
		}
	}

	private void buildPit(){
		
		for(int x = originX - 2; x <= originX + 2; x++){
			for(int z = originZ - 2; z <= originZ + 2; z++){
				for(int y = originY - 1; y > 0; y--){
					
					if(WorldGenPrimitive.isAirBlock(world, new Coord(x, y, z))){
						continue;
					}
					
					if(y < 0 + rand.nextInt(5) && WorldGenPrimitive.getBlock(world, new Coord(x, y, z)).getBlock() == Blocks.bedrock){
						continue;
					}
					
					if(    x == originX - 2
						|| x == originX +2
						|| z == originZ -2
						|| z == originZ +2){
						
						blocks.setBlock(world, rand, new Coord(x, y, z), true, true);
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
	
	public int getSize(){
		return 4;
	}
}
