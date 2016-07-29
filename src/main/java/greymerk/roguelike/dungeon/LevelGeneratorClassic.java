package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class LevelGeneratorClassic implements ILevelGenerator{

	private IWorldEditor editor;
	private Random rand;
	private IDungeonLevel level;

	private List<DungeonNode> nodes;
	private List<DungeonTunnel> tunnels;
	private DungeonNode end;
	
	
	public LevelGeneratorClassic(IWorldEditor editor, Random rand, IDungeonLevel level){
		this.editor = editor;
		this.rand = rand;
		this.level = level;
	
		
		nodes = new ArrayList<DungeonNode>();
		tunnels = new ArrayList<DungeonTunnel>();
	}	
		
	public void generate(Coord start, DungeonNode oldEnd){
		List<Node> gNodes = new ArrayList<Node>();
		Node startNode = new Node(this, level.getSettings(), Cardinal.directions[rand.nextInt(Cardinal.directions.length)], start);
		gNodes.add(startNode);
		
		while(!this.isDone(gNodes)){
			this.update(gNodes);
		}
		
		for(Node n : gNodes){
			n.cull();
		}
		
		DungeonNode startDungeonNode = null;
		
		for(Node n : gNodes){
			DungeonNode nToAdd = n.createNode();
			if(n == startNode){
				startDungeonNode = nToAdd;
			}
			this.nodes.add(nToAdd);
			this.tunnels.addAll(n.createTunnels(editor));
		}
					
		int attempts = 0;
		
		do{
			end = this.nodes.get(rand.nextInt(this.nodes.size()));
			attempts++;
		} while(end == startDungeonNode || end.getPosition().distance(start) > (16 + attempts * 2));
		
		List<DungeonNode> nodes = this.getNodes();
		Collections.shuffle(nodes, rand);
		
		// assign dungeons
		for (DungeonNode node : nodes){
			
			if(node == end || node == startDungeonNode) continue;
			
			// TODO: Find way to check available space when picking room
			IDungeonRoom toGenerate = this.level.getSettings().getRooms().get(rand);
			node.setDungeon(toGenerate);
			toGenerate.generate(editor, rand, this.level.getSettings(), node.getEntrances(), node.getPosition());
		}
		
		if(RogueConfig.getBoolean(RogueConfig.ENCASE)){
			for (DungeonNode node : nodes){
				if(node == end || node == startDungeonNode) continue;
				node.encase(editor, rand, this.level.getSettings().getTheme());
			}
			
			for(DungeonTunnel t : this.getTunnels()){
				t.encase(editor, rand, this.level.getSettings().getTheme());
			}
			
		}
		
		for(DungeonTunnel t : this.getTunnels()){
			t.construct(editor, rand, this.level.getSettings());
		}
		
		for (DungeonNode node : nodes){
			if(node == end || node == startDungeonNode) continue;
			IDungeonRoom toGenerate = node.getRoom();
			toGenerate.generate(editor, rand, this.level.getSettings(), node.getEntrances(), node.getPosition());
		}
		
		
		for(DungeonTunnel tunnel : this.getTunnels()){
			tunnel.genSegments(editor, rand, this.level);
		}
		
		LevelGenerator.generateLevelLink(editor, rand, this.level.getSettings(), start, oldEnd);

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
	
	public DungeonNode getEnd(){
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
			return new DungeonTunnel(editor, new Coord(this.start), new Coord(this.end), this.dir);
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
			
			if(!this.level.level.inRange(pos)){
				return;
			}
			
			for(Cardinal dir : Cardinal.directions){
				
				if (dir.equals(Cardinal.reverse(this.direction))){
					continue;
				}
				
				this.tunnelers.add(new Tunneler(dir, this.settings, this.level, new Coord(this.pos)));
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
			c.add(Cardinal.reverse(this.direction));
			for(Tunneler t : this.tunnelers){
				c.add(t.dir);
			}
			return c.toArray(new Cardinal[c.size()]);
		}
		
		public List<DungeonTunnel> createTunnels(IWorldEditor editor){
			List<DungeonTunnel> tunnels = new ArrayList<DungeonTunnel>();
			for(Tunneler t : this.tunnelers){
				tunnels.add(t.createTunnel());
			}
			return tunnels;
		}
		
		public DungeonNode createNode(){
			return new DungeonNode(this.getEntrances(), this.pos);
		}
		
		public void cull(){
			List<Tunneler> toKeep = new ArrayList<Tunneler>();
			for(Tunneler t : this.tunnelers){
				if(t.done){
					toKeep.add(t);
				}
			}
			this.tunnelers = toKeep;
		}
	}
}
