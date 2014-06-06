package greymerk.roguelike.catacomb;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.dungeon.IDungeonFactory;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class CatacombLevel {

	public static int SCATTER = 12;
	
	private World world;
	private Random rand;
	private CatacombNode start;
	private CatacombNode end;
	private List<CatacombNode> nodes;
	private int originX;
	private int originY;
	private int originZ;
	private boolean done;
	private int maxNodes;
	private int range;
	private ITheme theme;
	
	
	public CatacombLevel(World world, Random rand, ITheme theme, int originX, int originY, int originZ){
		this.world = world;
		this.nodes = new ArrayList<CatacombNode>();
				
		this.rand = rand;
		this.theme = theme;
		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;
		
		SCATTER = RogueConfig.getInt(RogueConfig.LEVELSCATTER);
		maxNodes = RogueConfig.getInt(RogueConfig.LEVELMAXROOMS);
		range = RogueConfig.getInt(RogueConfig.LEVELRANGE);
		
		start = new CatacombNode(world, rand, this, theme, originX, originY, originZ);
		nodes.add(start);
	}
	
	public CatacombLevel(World world, Random rand, ITheme theme, int originX, int originY, int originZ, int maxNodes, int range){
		this.world = world;
		this.nodes = new ArrayList<CatacombNode>();
				
		this.rand = rand;
		this.theme = theme;
		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;
		
		SCATTER = RogueConfig.getInt(RogueConfig.LEVELSCATTER);
		this.maxNodes = maxNodes;
		this.range = range;
		
		start = new CatacombNode(world, rand, this, theme, originX, originY, originZ);
		nodes.add(start);
	}
	
	public void generate(){
		
		// node tunnels
		for (CatacombNode node : nodes){
			node.construct(world);
		}

		IDungeonFactory rooms = Catacomb.getFactory(rand, Catacomb.getLevel(originY));
		
		Collections.shuffle(nodes, rand);
		
		// node dungeons
		for (CatacombNode node : nodes){
			
			int x = node.getX();
			int y = node.getY();
			int z = node.getZ();
			
			if(node == this.end){
				continue;
			}
			
			if(node == this.start){
				continue;
			}

			IDungeon toGenerate = rooms.get();
			node.setDungeon(toGenerate);
			toGenerate.generate(world, rand, theme, x, y, z);
		}
		
		// tunnel segment features
		for (CatacombNode node : nodes){
			node.segments();
		}
		
		generateLevelLink(world, rand, start.getX(), start.getY(), start.getZ());
	}
	
	private void generateLevelLink(World world, Random rand, int originX, int originY, int originZ) {
		
		// air in box
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 3, originY, originZ - 3, originX + 3, originY + 15, originZ + 3, 0, 0, 2, true, true);
		
		// shell
		List<Coord> shell = WorldGenPrimitive.getRectHollow(originX - 4, originY - 1, originZ - 4, originX + 4, originY + 16, originZ + 4);

		IBlockFactory blocks = theme.getPrimaryWall();
		
		for (Coord block : shell){
			
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			// floor & ceiling
			if(y == originY - 1 || y == originY + 26){
				blocks.setBlock(world, rand, x, y, z, true, true);
			}
			
			if(world.isAirBlock(x, y, z) && y < originY + 9){
				WorldGenPrimitive.setBlock(world, x, y, z, Block.fenceIron.blockID);
			} else {
				blocks.setBlock(world, rand, x, y, z, false, true);
			}
		
			
		}
		
		
		// middle floor
		WorldGenPrimitive.fillRectHollow(world, rand, originX - 4, originY + 9, originZ - 4, originX + 4, originY + 9, originZ + 4, blocks, true, true);
		
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory fill = theme.getPrimaryWall();
		
		
		for (int y = originY; y <= originY + 9; y++){
			WorldGenPrimitive.spiralStairStep(world, rand, originX, y, originZ, stair, theme.getPrimaryPillar());
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
		CatacombNode toAdd = new CatacombNode(world, rand, this, theme, tunneler);
		this.nodes.add(toAdd);
	}
	
	public boolean inRange(int x, int z){
		
		if(this.nodes.size() == 0){
			return true;
		}
		
		int xrel = Math.abs(this.originX - x);
		int zrel = Math.abs(this.originZ - z);
		
		int dist = (int) Math.sqrt((float)(xrel * xrel + zrel * zrel));
		return dist < this.range;
	}
	
	public int distance(CatacombNode aNode, CatacombNode other){
		
		int xrel = Math.abs(aNode.getX() - other.getX());
		int zrel = Math.abs(aNode.getZ() - other.getZ());
		
		int dist = (int) Math.sqrt((float)(xrel * xrel + zrel * zrel));		
		
		return dist;
	}
	
	public boolean hasNearbyNode(int x, int z, int min){
		for (CatacombNode node : nodes){
			
			int otherX = node.getX();
			int otherZ = node.getZ();
			
			int xrel = Math.abs(otherX - x);
			int zrel = Math.abs(otherZ - z);
			
			int dist = (int) Math.sqrt((float)(xrel * xrel + zrel * zrel));

			if(dist < min){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasNearbyNode(int x, int z){
		
		for (CatacombNode node : nodes){
			
			int otherX = node.getX();
			int otherZ = node.getZ();
			
			int xrel = Math.abs(otherX - x);
			int zrel = Math.abs(otherZ - z);
			
			int dist = (int) Math.sqrt((float)(xrel * xrel + zrel * zrel));
			
			
			if(dist < node.getSize()){
				return true;
			}
		}
		return false;
		
	}
	
	public boolean isDone(){
		return done;
	}
	
	public boolean full(){
		return this.nodes.size() >= this.maxNodes;
	}
	
	public int nodeCount(){
		return this.nodes.size();
	}
}
