package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public class LevelGeneratorClassic implements ILevelGenerator{

	private List<DungeonNode> nodes;
	private List<DungeonTunnel> tunnels;
	private IDungeonLevel level;
	private DungeonNode end;
	private Random rand;
	
	public LevelGeneratorClassic(World world, Random rand, IDungeonLevel level, Coord start, DungeonNode oldEnd){
		
		this.rand = rand;
		this.level = level;
		nodes = new ArrayList<DungeonNode>();
		tunnels = new ArrayList<DungeonTunnel>();
		
		List<Node> gNodes = new ArrayList<Node>();
		gNodes.add(new Node(this, level.getSettings(), Cardinal.directions[rand.nextInt(Cardinal.directions.length)], start));
		
		while(!this.isDone(gNodes)){
			this.update(gNodes);
		}
		
		for(Node n : gNodes){
			this.nodes.add(n.createNode());
			this.tunnels.addAll(n.createTunnels());
		}
		
		DungeonNode choice;
			
		int attempts = 0;
		
		do{
			choice = this.nodes.get(rand.nextInt(this.nodes.size()));
			attempts++;
		} while(choice.getPosition() == start || choice.getPosition().distance(start) > (16 + attempts * 2));
		
		this.end = choice;

	}
	
	public void generate(World world, Random rand){


	}
	

	
	public void update(List<Node> nodes){
		
		if(!this.full(nodes)){
			for (int i = 0; i < nodes.size(); i++){
				nodes.get(i).update(nodes);
			}
		}
	}
	
	private boolean isDone(List<Node> nodes){
		boolean allDone = true;
		
		for(Node node : nodes){
			if(!node.isDone()){
				allDone = false;
			}
		}
		
		return allDone || this.full(nodes);
	}
	
	private boolean full(List<Node> nodes){
		return nodes.size() >= this.level.getSettings().getNumRooms();
	}
	
	public void spawnNode(List<Node> nodes, Tunneler tunneler){		
		Node toAdd = new Node(this, this.level.getSettings(), tunneler.getDirection(), tunneler.getPosition());
		
		if(this.level.inRange(toAdd.getPos())){
			toAdd.spawnTunnelers();
		}
		
		nodes.add(toAdd);
	}
	
	public boolean hasNearbyNode(List<Node> nodes, Coord pos, int min){
		for (Node node : nodes){
			int dist = (int) node.getPos().distance(pos);
			if(dist < min){
				return true;
			}
		}
		return false;
	}
	
	public IDungeonLevel getLevel(){
		return this.level;
	}
	
	@Override
	public List<DungeonNode> getNodes() {
		return this.nodes;
	}

	@Override
	public List<DungeonTunnel> getTunnels() {
		return this.tunnels;
	}

	@Override
	public DungeonNode getEnd() {
		return this.end;
	}

	private class Tunneler{
		
		private boolean done;
		private Cardinal dir;
		private LevelSettings settings;
		private LevelGeneratorClassic generator;
		private Coord start;
		private Coord end;
		private int extend;
		
		public Tunneler(Cardinal dir, LevelSettings settings, LevelGeneratorClassic generator, Coord start){
			this.done = false;
			this.dir = dir;
			this.settings = settings;
			this.generator = generator;
			this.start = new Coord(start);
			this.end = new Coord(start);
			this.extend = settings.getScatter() * 2;
		}
		
		public void update(List<Node> nodes){
			if(this.done){
				return;
			}
			
			if(this.generator.hasNearbyNode(nodes, end, settings.getScatter())){
				end.add(dir);
			} else {
				if(rand.nextInt(extend) == 0){
					generator.spawnNode(nodes, this);
					this.done = true;
				} else {
					end.add(dir);
					extend--;
				}
			}
		}

		public boolean isDone(){
			return this.done;
		}
		
		public Cardinal getDirection(){
			return this.dir;
		}
		
		public Coord getPosition(){
			return new Coord(this.end);
		}
		
		public DungeonTunnel createTunnel(){
			return new DungeonTunnel(new Coord(this.start), new Coord(this.end), this.dir);
		}
	}
	
	private class Node{
		
		private List<Tunneler> tunnelers;
		private LevelGeneratorClassic level;
		private LevelSettings settings;
		private Cardinal direction;
		private Coord pos;
		
		public Node(LevelGeneratorClassic level, LevelSettings settings, Cardinal direction, Coord pos){
			this.tunnelers = new ArrayList<Tunneler>();
			this.level = level;
			this.settings = settings;
			this.direction = direction;
			this.pos = pos;
			
			this.spawnTunnelers();
		}
		
		private void spawnTunnelers(){
			for(Cardinal dir : Cardinal.directions){
				
				if (dir.equals(this.direction)){
					continue;
				}
				
				if(tunnelers.isEmpty() || rand.nextBoolean()){
					this.tunnelers.add(new Tunneler(dir, this.settings, this.level, new Coord(this.pos)));
				}
			}
		}
		
		public void update(List<Node> nodes){
			for (Tunneler tunneler : tunnelers){
				tunneler.update(nodes);
			}
		}
		
		public boolean isDone(){
			for (Tunneler tunneler : tunnelers){
				if(!tunneler.isDone()){
					return false;
				}
			}
			return true;
		}
		
		public Coord getPos(){
			return new Coord(this.pos);
		}
		
		public Cardinal[] getEntrances(){
			List<Cardinal> c = new ArrayList<Cardinal>();
			for(Tunneler t : this.tunnelers){
				c.add(t.dir);
			}
			return c.toArray(new Cardinal[c.size()]);
		}
		
		public List<DungeonTunnel> createTunnels(){
			List<DungeonTunnel> tunnels = new ArrayList<DungeonTunnel>();
			for(Tunneler t : this.tunnelers){
				tunnels.add(t.createTunnel());
			}
			return tunnels;
		}
		
		public DungeonNode createNode(){
			return new DungeonNode(level.getLevel(), this.getEntrances(), this.pos);
		}
	}
}
