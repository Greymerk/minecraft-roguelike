package greymerk.roguelike.catacomb;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.ISegment;
import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockFactoryProvider;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Tuple;
import net.minecraft.src.World;

public class CatacombTunneler {

	private World world;
	private Random rand;
	private CatacombLevel level;
	private List<Tuple> tunnel;
	private Cardinal direction;
	private int originX;
	private int originY;
	private int originZ;
	private boolean done;
	private int extend;
	
	public CatacombTunneler(World world, Random rand, CatacombLevel level, Cardinal direction, int x, int y, int z){

		this.world = world;
		this.rand = rand;
		this.level = level;
		this.direction = direction;
		this.originX = x;
		this.originY = y;
		this.originZ = z;
		done = false;
		this.extend = CatacombLevel.SCATTER * 2;
		tunnel = new ArrayList<Tuple>();
		tunnel.add(new Tuple(x, z));

	}
	
	public void update(){
		if(level.full()){
			return;
		}
		
		if(done){
			return;
		}
		
		if(level.hasNearbyNode(originX, originZ, CatacombLevel.SCATTER)){
			advance();
		} else {
			if(rand.nextInt(extend) == 0){
				level.spawnNode(this);
				done = true;
			} else {
				advance();
				extend--;
			}
		}
	}
	
	public void advance(){

		tunnel.add(new Tuple(originX, originZ));
		
		originX = originX + (Integer)(Cardinal.getTuple(direction).getFirst());
		originZ = originZ + (Integer)(Cardinal.getTuple(direction).getSecond());
		
	}
		
	public boolean isDone(){
		if(level.full()){
			return true;
		}
		
		return done;
	}
	
	public Cardinal getDirection(){
		return this.direction;
	}
	
	public int getX(){
		return originX;
	}
	
	public int getY(){
		return originY;
	}
	
	public int getZ(){
		return originZ;
	}
	
	public void construct(World world){
		
		if(tunnel.isEmpty()){
			return;
		}
		
		IBlockFactory wallBlocks = BlockFactoryProvider.getRandomizer(Catacomb.getLevel(originY), rand);
		IBlockFactory bridgeBlocks = BlockFactoryProvider.getRandomizer(Catacomb.getLevel(originY), rand, true);
		
		for (Tuple location : tunnel){
			
			int x = (Integer)location.getFirst();
			int z = (Integer)location.getSecond();
			
			if(direction == Cardinal.NORTH || direction == Cardinal.SOUTH){
				WorldGenPrimitive.fillRectSolid(world, x - 1, originY, z, x + 1, originY + 2, z, 0, 0, 0, false, true);
				WorldGenPrimitive.fillRectSolid(world, x - 2, originY - 1, z, x + 2, originY + 4, z, wallBlocks, false, true);
				WorldGenPrimitive.fillRectSolid(world, x - 1, originY - 1, z, x + 1, originY - 1, z, bridgeBlocks, true, false);
			} else {
				WorldGenPrimitive.fillRectSolid(world, x, originY, z - 1, x, originY + 2, z + 1, 0, 0, 0, false, true);
				WorldGenPrimitive.fillRectSolid(world, x, originY - 1, z - 2, x, originY + 4, z + 2, wallBlocks, false, true);
				WorldGenPrimitive.fillRectSolid(world, x, originY - 1, z - 1, x, originY - 1, z + 1, bridgeBlocks, true, false);
			}
			
		}
		
		// end of the tunnel;
		Tuple location = tunnel.get(tunnel.size() - 1);
		int x = (Integer)location.getFirst() + (Integer)(Cardinal.getTuple(direction).getFirst());
		int z = (Integer)location.getSecond() + (Integer)(Cardinal.getTuple(direction).getSecond());
		
		if(direction == Cardinal.NORTH || direction == Cardinal.SOUTH){
			WorldGenPrimitive.fillRectSolid(world, x - 2, originY - 1, z, x + 2, originY + 4, z, wallBlocks, false, true);
		} else {
			WorldGenPrimitive.fillRectSolid(world, x, originY - 1, z - 2, x, originY + 4, z + 2, wallBlocks, false, true);
		}
	}
	
	public void addSegments(World world){
		for(Tuple location : tunnel){
			int x = (Integer)location.getFirst();
			int z = (Integer)location.getSecond();
			
			ISegment seg = Segment.pickSegment(rand, direction, x, originY, z);
			
			if(seg == null) continue;
			
			seg.generate(world, rand, level, direction, x, originY, z);
		}
	}
	
	
}
