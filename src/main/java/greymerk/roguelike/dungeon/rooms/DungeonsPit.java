package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.redstone.Piston;
import greymerk.roguelike.worldgen.redstone.Torch;

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

	public boolean generate(World inWorld, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {

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
		

		for (Cardinal dir : Cardinal.directions){
			setTrap(world, rand, settings, dir, origin);
		}
		
		List<Coord> space = new ArrayList<Coord>();
		space.add(new Coord(originX - 2, originY, originZ - 2));
		space.add(new Coord(originX - 2, originY, originZ + 2));
		space.add(new Coord(originX + 2, originY, originZ - 2));
		space.add(new Coord(originX + 2, originY, originZ + 2));
		
		TreasureChest.createChests(inWorld, inRandom, settings, 1, space);
		
		return true;
	}

	MetaBlock air = new MetaBlock(Blocks.air);
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
						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, air);
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
					
					if(world.isAirBlock(new Coord(x, y, z).getBlockPos())){
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
	
	private void setTrap(World world, Random rand, LevelSettings settings, Cardinal dir, Coord origin){
		ITheme theme = settings.getTheme();
		IBlockFactory walls = theme.getPrimaryWall();
		MetaBlock plate = new MetaBlock(Blocks.stone_pressure_plate);
		MetaBlock wire = new MetaBlock(Blocks.redstone_wire);
		Coord start;
		Coord end;
		Coord cursor;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		start.add(dir, 3);
		start.add(Cardinal.DOWN);
		start.add(orth[0]);
		end = new Coord(origin);
		end.add(dir, 6);
		end.add(Cardinal.UP, 3);
		end.add(orth[1]);
		
		
		
		List<Coord> box = WorldGenPrimitive.getRectHollow(start, end);
		
		for(Coord cell : box){
			if(world.isAirBlock(cell.getBlockPos())) return;
			walls.setBlock(world, rand, cell);
		}
		
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		plate.setBlock(world, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.DOWN);
		cursor.add(dir, 3);
		Torch.generate(world, Torch.REDSTONE, dir, cursor);
		cursor.add(dir);
		wire.setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		cursor.add(dir);
		Torch.generate(world, Torch.REDSTONE_UNLIT, Cardinal.UP, cursor);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.UP);
		Piston.generate(world, cursor, Cardinal.reverse(dir), true);
	}
	
	public int getSize(){
		return 4;
	}
}
