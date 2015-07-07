package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.rooms.DungeonCorner;
import greymerk.roguelike.dungeon.rooms.DungeonLinker;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public class DungeonLevel implements IDungeonLevel{

	private World world;
	private Random rand;
	private DungeonNode start;
	private DungeonNode end;
	private List<DungeonNode> nodes;
	private int originX;
	private int originZ;

	private ITheme theme;
	private LevelSettings settings;
	
	public DungeonLevel(World world, Random rand, LevelSettings settings, Coord origin){
		this.world = world;
		this.nodes = new ArrayList<DungeonNode>();

		this.rand = rand;
		this.settings = settings;
		this.originX = origin.getX();
		this.originZ = origin.getZ();
		
		start = new DungeonNode(world, rand, this, theme, new Coord(origin));
		nodes.add(start);
		
		while(!this.isDone()){
			this.update();
		}
	}
	
	public void generate(DungeonNode oldEnd){
		
		// node tunnels
		for (DungeonNode node : nodes){
			node.construct(world);
		}

		Collections.shuffle(nodes, rand);
		
		// node dungeons
		for (DungeonNode node : nodes){
			
			int x = node.getPosition().getX();
			int y = node.getPosition().getY();
			int z = node.getPosition().getZ();
			
			if(node == this.end){
				continue;
			}
			
			if(node == this.start){
				continue;
			}

			IDungeonRoom toGenerate = this.settings.getRooms().get(rand);
			node.setDungeon(toGenerate);
			toGenerate.generate(world, rand, this.settings, node.getEntrances(), new Coord(x, y, z));
		}
		
		generateLevelLink(world, rand, settings.getTheme(), this.start, oldEnd);
		
		// tunnel segment features
		for (DungeonNode node : nodes){
			node.segments();
		}
	}
	
	private void generateLevelLink(World world, Random rand, ITheme theme, DungeonNode start, DungeonNode oldEnd) {
		
		IDungeonRoom downstairs = new DungeonLinker();
		downstairs.generate(world, rand, settings, start.getEntrances(), start.getPosition());
		
		if(oldEnd == null) return;
		
		IDungeonRoom upstairs = new DungeonCorner();
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

			DungeonNode choice;
				
			int attempts = 0;
			
			do{
				choice = this.nodes.get(rand.nextInt(this.nodes.size()));
				attempts++;
			} while(choice == start || distance(choice, start) > (16 + attempts * 2));
			
			this.end = choice;
		}
	}
	
	@Override
	public DungeonNode getEnd(){
		return this.end;
	}
	
	public void spawnNode(DungeonTunneler tunneler){		
		DungeonNode toAdd = new DungeonNode(world, rand, this, theme, tunneler);
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
	
	private int distance(DungeonNode aNode, DungeonNode other){
		
		int xrel = Math.abs(aNode.getPosition().getX() - other.getPosition().getX());
		int zrel = Math.abs(aNode.getPosition().getZ() - other.getPosition().getZ());
		
		int dist = (int) Math.sqrt((float)(xrel * xrel + zrel * zrel));		
		
		return dist;
	}
	
	public boolean hasNearbyNode(int x, int z, int min){
		for (DungeonNode node : nodes){
			
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
		
		for (DungeonNode node : nodes){
			
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
	
	private boolean isDone(){
		
		boolean allDone = true;
		
		for(DungeonNode node : this.nodes){
			if(!node.isDone()){
				allDone = false;
			}
		}
		
		return allDone || this.full();
	}
	
	private boolean full(){
		return this.nodes.size() >= settings.getNumRooms();
	}
	
	public int nodeCount(){
		return this.nodes.size();
	}

	@Override
	public LevelSettings getSettings(){
		return this.settings;
	}

}
