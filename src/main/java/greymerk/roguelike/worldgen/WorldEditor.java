package greymerk.roguelike.worldgen;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldEditor {
	
	World world;
	private Map<Block, Integer> stats;
	private TreasureManager chests;
	
	public WorldEditor(World world){
		this.world = world;
		stats = new HashMap<Block, Integer>();
		this.chests = new TreasureManager();
	}
	
	public boolean setBlock(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid){
		
		MetaBlock currentBlock = getBlock(pos);
		
		if(currentBlock.getBlock() == Blocks.chest) return false;
		if(currentBlock.getBlock() == Blocks.trapped_chest) return false;
		if(currentBlock.getBlock() == Blocks.mob_spawner) return false;
		
		boolean isAir = world.isAirBlock(pos.getX(), pos.getY(), pos.getZ());
		
		if(!fillAir && isAir) return false;
		if(!replaceSolid && !isAir)	return false;
		
		world.setBlock(pos.getX(), pos.getY(), pos.getZ(), block.getBlock(), block.getMeta(), block.getFlag());
		
		Block type = block.getBlock();
		Integer count = stats.get(type);
		if(count == null){
			stats.put(type, 1);	
		} else {
			stats.put(type, count + 1);
		}
		
		return true;
		
	}
	
	public boolean setBlock(Coord pos, MetaBlock block){
		return block.setBlock(this, pos);
	}
	
	public boolean setBlock(Coord pos, Block block){
		return setBlock(pos, new MetaBlock(block));
	}
	
	public boolean setBlock(Random rand, Coord coord, IBlockFactory blocks, boolean fillAir, boolean replaceSolid) {
		return blocks.setBlock(this, rand, coord, fillAir, replaceSolid);
	}
	
	public boolean isAirBlock(Coord pos){
		return world.isAirBlock(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public boolean isOpaque(Coord pos){
		Block b = getBlock(pos).getBlock();
		Material m = b.getMaterial();
		return m.isOpaque();
	}
	
	public long getSeed(){
		return this.world.getSeed();
	}
	
	public BiomeGenBase getBiome(Coord pos){
		return world.getBiomeGenForCoords(pos.getX(), pos.getZ());
	}
	
	public int getDimension(){
		return world.provider.dimensionId;
	}
	
	public Random setSeed(int a, int b, int c){
		return world.setRandomSeed(a, b, c);
	}
	
	public void fillRectSolid(Random rand, Coord start, Coord end, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		Coord c1 = new Coord(start);
		Coord c2 = new Coord(end);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					this.setBlock(rand, new Coord(x, y, z), blocks, fillAir, replaceSolid);
				}
			}
		}
	}
	
	public void fillRectHollow(Random rand, Coord start, Coord end, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		Coord c1 = new Coord(start);
		Coord c2 = new Coord(end);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					if(x == c1.getX() || x == c2.getX() || y == c1.getY() || y == c2.getY() || z == c1.getZ() || z == c2.getZ()){
						setBlock(rand, new Coord(x, y, z), blocks, fillAir, replaceSolid);
					} else {					
						setBlock(new Coord(x, y, z), new MetaBlock(Blocks.air));
					}	
				}
			}
			
		}
	}
	

	public static List<Coord> getRectSolid(Coord start, Coord end){
		return getRectSolid(start.getX(), start.getY(), start.getZ(), end.getX(), end.getY(), end.getZ());
	}
	
	public static List<Coord> getRectSolid(int x1, int y1, int z1, int x2, int y2, int z2){
		
		Coord c1 = new Coord(x1, y1, z1);
		Coord c2 = new Coord(x2, y2, z2);
		
		Coord.correct(c1, c2);
		
		List<Coord> points = new LinkedList<Coord>();
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					points.add(new Coord(x, y, z));
				}
			}
		}	
		
		return points;
	}
	
	public List<Coord> getRectHollow(Coord start, Coord end){
		return getRectHollow(start.getX(), start.getY(), start.getZ(), end.getX(), end.getY(), end.getZ());
	}
	
	public List<Coord> getRectHollow(int x1, int y1, int z1, int x2, int y2, int z2){

		
		List<Coord> points = new LinkedList<Coord>();
		
		Coord c1 = new Coord(x1, y1, z1);
		Coord c2 = new Coord(x2, y2, z2);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					if(x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2){
						points.add(new Coord(x, y, z));
					}
				}
			}
		}
		
		return points;
	}
	
	public void fillPyramidSolid(Random rand, Coord base, int height, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		
		if(height == 0){
			setBlock(rand, base, blocks, fillAir, replaceSolid);
			return;
		}
		
		Coord start;
		Coord end;
		
		start = new Coord(base);
		end = new Coord(base);
		start.add(Cardinal.NORTH, height);
		start.add(Cardinal.WEST, height);
		end.add(Cardinal.SOUTH, height);
		end.add(Cardinal.EAST, height);
		
		fillRectSolid(rand, start, end, blocks, fillAir, replaceSolid);
		
		base.add(Cardinal.UP);
		
		fillPyramidSolid(rand, base, (height - 1), blocks, fillAir, replaceSolid);
		
	}
	

	public void spiralStairStep(Random rand, Coord origin, IStair stair, IBlockFactory fill){
		
		MetaBlock air = new MetaBlock(Blocks.air);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(new Coord(-1, 0, -1));
		end = new Coord(origin);
		end.add(new Coord(1, 0, 1));
		
		// air
		fillRectSolid(rand, start, end, air, true, true);
		
		// core
		setBlock(rand, origin, fill, true, true);
		
		Cardinal dir = Cardinal.directions[origin.getY() % 4];
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		cursor = new Coord(origin);
		cursor.add(dir);
		stair.setOrientation(orth[0], false).setBlock(this, cursor);
		cursor.add(orth[1]);
		stair.setOrientation(orth[1], true).setBlock(this, cursor);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(this, cursor);
		
		
	}
	

	public void randomVines(Random rand, int x1, int y1, int z1, int x2, int y2, int z2){

		Coord c1 = new Coord(x1, y1, z1);
		Coord c2 = new Coord(x2, y2, z2);
		Coord.correct(c1, c2);

		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
        			for (int dir = 2; dir <= 5; ++dir){
	        			if(world.isAirBlock(x, y, z)){
	        				
	        				if(rand.nextBoolean()){
	        					continue;
	        				}
	        				
	            		    if (Blocks.vine.canPlaceBlockOnSide(world, x, y, z, dir))
	                        {
	            		    	setBlock(new Coord(x, y, z), new MetaBlock(Blocks.vine, 1 << Direction.facingToDirection[Facing.oppositeSide[dir]]), true, true);
	                            break;
	                        }
	        			}
        			}
        		}
        	}
		}
	}
	
	public void fillDown(Random rand, Coord origin, IBlockFactory blocks){

		Coord cursor = new Coord(origin);
		
		while(!isOpaque(cursor) && cursor.getY() > 1){
			blocks.setBlock(this, rand, cursor);
			cursor.add(Cardinal.DOWN);
		}
	}
	
	public MetaBlock getBlock(Coord pos){
		Block b = world.getBlock(pos.getX(), pos.getY(), pos.getZ());
		int meta = world.getBlockMetadata(pos.getX(), pos.getY(), pos.getZ());
		return new MetaBlock(b, meta);
	}
	
	public TileEntity getTileEntity(Coord pos){
		return world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public void setBlock(int x, int y, int z, Block block){
		new MetaBlock(block).setBlock(this, new Coord(x, y, z));
	}
	
	public void setBlock(int x, int y, int z, MetaBlock block){
		block.setBlock(this, new Coord(x, y, z));
	}
	
	public void fillRectSolid(Random rand, int x, int y, int z, int x2, int y2, int z2, IBlockFactory blocks){
		fillRectSolid(rand, new Coord(x, y, z), new Coord(x2, y2, z2), blocks, true, true);
	}
	
	public void fillRectSolid(Random rand, int x, int y, int z, int x2, int y2, int z2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		fillRectSolid(rand, new Coord(x, y, z), new Coord(x2, y2, z2), blocks, fillAir, replaceSolid);
	}
	
	public void setBlock(Random rand, int x, int y, int z, IBlockFactory block, boolean fillAir, boolean replaceSolid){
		setBlock(rand, new Coord(x, y, z), block, true, true);
	}
	
	public void fillRectHollow(Random rand, int x, int y, int z, int x2, int y2, int z2, IBlockFactory blocks, boolean fillAir, boolean replaceSolid){
		fillRectHollow(rand, new Coord(x, y, z), new Coord(x2, y2, z2), blocks, fillAir, replaceSolid);
	}
	
	public boolean validGroundBlock(Coord pos){
		
		if(isAirBlock(pos)) return false;
		
		Block block = getBlock(pos).getBlock();
		
		if(block.getMaterial() == Material.wood) return false;
		if(block.getMaterial() == Material.water) return false;
		if(block.getMaterial() == Material.cactus) return false;
		if(block.getMaterial() == Material.snow) return false;
		if(block.getMaterial() == Material.grass) return false;
		if(block.getMaterial() == Material.gourd) return false;
		if(block.getMaterial() == Material.leaves) return false;
		if(block.getMaterial() == Material.plants) return false;
		
		return true;
	}
	
	@Override
	public String toString(){
		String toReturn = "";

		for(Map.Entry<Block, Integer> pair : stats.entrySet()){
			toReturn += pair.getKey().getLocalizedName() + ": " + pair.getValue() + "\n";
		}
		
		return toReturn;
	}

	public void blockUpdate(Coord pos, Block block, int flag) {
		world.scheduleBlockUpdate(pos.getX(), pos.getY(), pos.getZ(), block, flag);
	}

	public void setBlockMetadata(Coord pos, int meta) {
		world.setBlockMetadataWithNotify(pos.getX(), pos.getY(), pos.getZ(), meta, 2);
	}
	
	public int getStat(Block type){
		if(!this.stats.containsKey(type)) return 0;
		return this.stats.get(type);
	}
	
	public void addChest(ITreasureChest toAdd){
		this.chests.add(toAdd);
	}
	
	public TreasureManager getTreasure(){
		return this.chests;
	}

	public boolean canPlaceOnSide(Block block, Coord c, int dir) {
		return block.canPlaceBlockOnSide(world, c.getX(), c.getY(), c.getZ(), dir);
	}
}