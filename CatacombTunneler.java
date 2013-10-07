package greymerk.roguelike;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Tuple;
import net.minecraft.src.World;

public class CatacombTunneler {

	
	public static final Tuple NORTH = new Tuple(0, -1); 
	public static final Tuple SOUTH = new Tuple(0, 1);
	public static final Tuple WEST = new Tuple(-1, 0);
	public static final Tuple EAST = new Tuple(1, 0);
	public static final List<Tuple> DIRECTIONS = Arrays.asList(NORTH, SOUTH, WEST, EAST);

	private World world;
	private Random rand;
	private CatacombLevel level;
	private List<Tuple> tunnel;
	private Tuple direction;
	private int x;
	private int y;
	private int z;
	private boolean done;
	private int extend;
	
	public CatacombTunneler(Random rand, CatacombLevel level, Tuple direction, int x, int y, int z){
		this.rand = rand;
		this.level = level;
		this.direction = direction;
		this.x = x;
		this.y = y;
		this.z = z;
		done = false;
		this.extend = CatacombLevel.SCATTER * 2;
		tunnel = new ArrayList<Tuple>();
		tunnel.add(new Tuple(x, z));

	}
	
	public static Tuple reverse(Tuple direction){
		int dx = (Integer)direction.getFirst() * -1;
		int dz = (Integer)direction.getSecond() * -1;
		
		return new Tuple(dx, dz);
	}
	
	public void update(){
		if(level.full()){
			return;
		}
		
		if(done){
			return;
		}
		
		if(level.hasNearbyNode(x, z)){
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

		tunnel.add(new Tuple(x, z));
		
		x = x + (Integer)direction.getFirst();
		z = z + (Integer)direction.getSecond();
		
	}
		
	public boolean isDone(){
		if(level.full()){
			return true;
		}
		
		return done;
	}
	
	public Tuple getDirection(){
		return this.direction;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
	
	public void construct(World world){
		
		if(tunnel.isEmpty()){
			return;
		}
		
		IBlockFactory fillBlocks = BlockFactoryProvider.getRandomizer(Dungeon.getRank(y), rand);
		
		int bridgeBlock = Dungeon.getRank(y) == 3 ? Block.netherrack.blockID : Block.cobblestone.blockID;
		
		for (Tuple location : tunnel){
			
			int x = (Integer)location.getFirst();
			int z = (Integer)location.getSecond();
			
			if(direction == NORTH || direction == SOUTH){
				WorldGenPrimitive.fillRectSolid(world, x - 1, y, z, x + 1, y + 2, z, 0, 0, 0, false, true);
				WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z, x + 2, y + 4, z, fillBlocks, false, true);
				WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z, x + 1, y - 1, z, bridgeBlock, 0, 2, true, false);
			} else {
				WorldGenPrimitive.fillRectSolid(world, x, y, z - 1, x, y + 2, z + 1, 0, 0, 0, false, true);
				WorldGenPrimitive.fillRectSolid(world, x, y - 1, z - 2, x, y + 4, z + 2, fillBlocks, false, true);
				WorldGenPrimitive.fillRectSolid(world, x, y - 1, z - 1, x, y - 1, z + 1, bridgeBlock, 0, 2, true, false);
			}
			
		}
		
		// end of the tunnel;
		Tuple location = tunnel.get(tunnel.size() - 1);
		int x = (Integer)location.getFirst() + (Integer)direction.getFirst();
		int z = (Integer)location.getSecond() + (Integer)direction.getSecond();
		
		if(direction == NORTH || direction == SOUTH){
			WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z, x + 2, y + 4, z, fillBlocks, false, true);
		} else {
			WorldGenPrimitive.fillRectSolid(world, x, y - 1, z - 2, x, y + 4, z + 2, fillBlocks, false, true);
		}
	}	
}
