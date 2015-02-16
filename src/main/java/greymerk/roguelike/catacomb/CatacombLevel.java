package greymerk.roguelike.catacomb;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.dungeon.room.DungeonCorner;
import greymerk.roguelike.catacomb.dungeon.room.DungeonLinker;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public class CatacombLevel {

	private World world;
	private Random rand;
	private CatacombNode start;
	private CatacombNode end;
	private List<CatacombNode> nodes;
	private int originX;
	private int originZ;

	private ITheme theme;
	private CatacombLevelSettings settings;
	
	public CatacombLevel(World world, Random rand, CatacombLevelSettings settings, Coord origin){
		this.world = world;
		this.nodes = new ArrayList<CatacombNode>();

		this.rand = rand;
		this.settings = settings;
		this.originX = origin.getX();
		this.originZ = origin.getZ();
		
		start = new CatacombNode(world, rand, this, theme, new Coord(origin));
		nodes.add(start);
	}
	
	public CatacombLevel(World world, Random rand, CatacombLevelSettings settings, Coord origin, int maxNodes, int range){
		this.world = world;
		this.nodes = new ArrayList<CatacombNode>();
		
		this.rand = rand;
		this.settings = settings;
		this.originX = origin.getX();
		this.originZ = origin.getZ();
		
		start = new CatacombNode(world, rand, this, theme, new Coord(origin));
		nodes.add(start);
	}
	
	public void generate(CatacombNode oldEnd){
		
		// node tunnels
		for (CatacombNode node : nodes){
			node.construct(world);
		}

		Collections.shuffle(nodes, rand);
		
		// node dungeons
		for (CatacombNode node : nodes){
			
			int x = node.getPosition().getX();
			int y = node.getPosition().getY();
			int z = node.getPosition().getZ();
			
			if(node == this.end){
				continue;
			}
			
			if(node == this.start){
				continue;
			}

			IDungeon toGenerate = this.settings.getRooms().get(rand);
			node.setDungeon(toGenerate);
			toGenerate.generate(world, rand, this.settings, node.getEntrances(), new Coord(x, y, z));
		}
		
		generateLevelLink(world, rand, settings.getTheme(), this.start, oldEnd);
		
		// tunnel segment features
		for (CatacombNode node : nodes){
			node.segments();
		}
	}
	
	private void generateLevelLink(World world, Random rand, ITheme theme, CatacombNode start, CatacombNode oldEnd) {
		
		IDungeon downstairs = new DungeonLinker();
		downstairs.generate(world, rand, settings, start.getEntrances(), start.getPosition());
		
		if(oldEnd == null) return;
		
		IDungeon upstairs = new DungeonCorner();
		upstairs.generate(world, rand, settings, oldEnd.getEntrances(), oldEnd.getPosition());
		
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord cursor = new Coord(start.getPosition());
		for (int i = 0; i < oldEnd.getPosition().getY() - start.getPosition().getY(); i++){
			WorldGenPrimitive.spiralStairStep(world, rand, cursor, stair, theme.getPrimaryPillar());
			cursor.add(Cardinal.UP);
		}	
	}

	public void update(){
		
		if(!this.full()){
			for (int i = 0; i < nodes.size(); i++){
				nodes.get(i).update();
			}
		}
		
		if (this.isDone() && this.end == null){

			CatacombNode choice;
				
			int attempts = 0;
			
			do{
				choice = this.nodes.get(rand.nextInt(this.nodes.size()));
				attempts++;
			} while(choice == start || distance(choice, start) > (16 + attempts * 2));
			
			this.end = choice;
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
		return dist < settings.getRange();
	}
	
	public int distance(CatacombNode aNode, CatacombNode other){
		
		int xrel = Math.abs(aNode.getPosition().getX() - other.getPosition().getX());
		int zrel = Math.abs(aNode.getPosition().getZ() - other.getPosition().getZ());
		
		int dist = (int) Math.sqrt((float)(xrel * xrel + zrel * zrel));		
		
		return dist;
	}
	
	public boolean hasNearbyNode(int x, int z, int min){
		for (CatacombNode node : nodes){
			
			int otherX = node.getPosition().getX();
			int otherZ = node.getPosition().getZ();
			
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
			
			int otherX = node.getPosition().getX();
			int otherZ = node.getPosition().getZ();
			
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
		
		boolean allDone = true;
		
		for(CatacombNode node : this.nodes){
			if(!node.isDone()){
				allDone = false;
			}
		}
		
		return allDone || this.full();
	}
	
	public boolean full(){
		return this.nodes.size() >= settings.getNumRooms();
	}
	
	public int nodeCount(){
		return this.nodes.size();
	}

	public CatacombLevelSettings getSettings(){
		return this.settings;
	}

}
