package greymerk.roguelike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class CatacombLevel {

	public static final int MAX_NODES = 60;
	public static final int RANGE = 80;
	public static final int SCATTER = 12;
	
	private World world;
	private Random rand;
	private CatacombNode start;
	private CatacombNode end;
	private List<CatacombNode> nodes;
	private int originX;
	private int originY;
	private int originZ;
	private boolean done;
	
	
	public CatacombLevel(World world, Random rand, int originX, int originY, int originZ){
		this.world = world;
		this.rand = rand;
		this.nodes = new ArrayList<CatacombNode>();
		
		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;
		
		start = new CatacombNode(world, rand, this, originX, originY, originZ);
		nodes.add(start);
	}	
	
	public void generate(){
		
		// node tunnels
		for (CatacombNode node : nodes){
			node.construct(world);
		}

		IDungeonFactory rooms = Dungeon.getFactory(rand, Dungeon.getRank(originY));
		
		Collections.shuffle(nodes);
		
		// node dungeons
		for (CatacombNode node : nodes){
			
			int x = node.getX();
			int y = node.getY();
			int z = node.getZ();
			
			if(node == this.end){
				continue;
			}
			
			if(node == this.start){
				generateLevelLink(world, rand, x, y, z);
				continue;
			}

			rooms.get().generate(world, rand, x, y, z);
					
		}		
	}
	
	private void generateLevelLink(World world, Random rand, int originX, int originY, int originZ) {
		
		// air in box
		WorldGenPrimitive.fillRectSolid(world, originX - 3, originY, originZ - 3, originX + 3, originY + 15, originZ + 3, 0, 0, 2, true, true);
		
		// shell
		List<Coord> shell = WorldGenPrimitive.getRectHollow(originX - 4, originY - 1, originZ - 4, originX + 4, originY + 16, originZ + 4);

		IBlockFactory blocks = BlockFactoryProvider.getRandomizer(Dungeon.getRank(originY), rand);
		
		for (Coord block : shell){
			
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			// floor & ceiling
			if(y == originY - 1 || y == originY + 26){
				blocks.setBlock(world, x, y, z, true, true);
			}
			
			if(world.isAirBlock(x, y, z) && y < originY + 9){
				WorldGenPrimitive.setBlock(world, x, y, z, Block.fenceIron.blockID);
			} else {
				blocks.setBlock(world, x, y, z, false, true);
			}
		
			
		}
		
		
		// middle floor
		WorldGenPrimitive.fillRectHollow(world, originX - 4, originY + 9, originZ - 4, originX + 4, originY + 9, originZ + 4, blocks, true, true);
		
		for (int y = originY; y <= originY + 9; y++){
			WorldGenPrimitive.spiralStairStep(world, originX, y, originZ);
		}
		
	}

	public void update(){
				
		for (int i = 0; i < nodes.size(); i++){
			nodes.get(i).update();
		}
		
		if (this.end == null){
			boolean done = true;
			for (CatacombNode node : nodes){
				if(!node.isDone()){
					done = false;
				}
			}
			
			if(done){
				CatacombNode choice;
				
				int attempts = 0;
				
				do{
					choice = this.nodes.get(rand.nextInt(this.nodes.size()));
					attempts++;
				} while(choice == start || distance(choice, start) > (16 + attempts * 2));
				
				this.end = choice;
				this.done = true;
			}
			
		}
		
		
	}
	

	public CatacombNode getEnd(){
		return this.end;
	}
	
	public void spawnNode(CatacombTunneler tunneler){		
		CatacombNode toAdd = new CatacombNode(world, rand, this, tunneler);
		this.nodes.add(toAdd);
		
	}
	
	public boolean inRange(int x, int z){
		
		if(this.nodes.size() == 0){
			return true;
		}
		
		int xrel = Math.abs(this.originX - x);
		int zrel = Math.abs(this.originZ - z);
		
		int dist = (int) Math.sqrt((float)(xrel * xrel + zrel * zrel));
		return dist < RANGE;
	}
	
	public int distance(CatacombNode aNode, CatacombNode other){
		
		int xrel = Math.abs(aNode.getX() - other.getX());
		int zrel = Math.abs(aNode.getZ() - other.getZ());
		
		int dist = (int) Math.sqrt((float)(xrel * xrel + zrel * zrel));		
		
		return dist;
	}
	
	public boolean hasNearbyNode(int x, int z){
		
		for (CatacombNode node : nodes){
			
			int otherX = node.getX();
			int otherZ = node.getZ();
			
			int xrel = Math.abs(otherX - x);
			int zrel = Math.abs(otherZ - z);
			
			int dist = (int) Math.sqrt((float)(xrel * xrel + zrel * zrel));
			
			
			if(dist < SCATTER){
				return true;
			}
		}
		return false;
		
	}
	
	public boolean isDone(){
		return done;
	}
	
	public boolean full(){
		return this.nodes.size() >= MAX_NODES;
	}
	
	public int nodeCount(){
		return this.nodes.size();
	}
}
