package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.util.mst.Edge;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.util.mst.Point;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class LevelGeneratorMST implements ILevelGenerator{

	IWorldEditor editor;
	Random rand;
	IDungeonLevel level;
	private DungeonNode end;
	
	private List<DungeonNode> nodes;
	private List<DungeonTunnel> tunnels;
	
	public LevelGeneratorMST(IWorldEditor editor, Random rand, IDungeonLevel level){
		this.editor = editor;
		this.rand = rand;
		this.level = level;
		
		nodes = new ArrayList<DungeonNode>();
		tunnels = new ArrayList<DungeonTunnel>();
	}	
	
	@Override
	public void generate(Coord start, DungeonNode oldEnd) {
		MinimumSpanningTree mst = new MinimumSpanningTree(rand, 7, 17, new Coord(start));
		List<Edge> edges = mst.getEdges();
		List<Coord> vertices = mst.getPointPositions();
		List<Edge> used = new ArrayList<Edge>();
		
		for(Coord c : vertices){
			for(Edge e : edges){
				if(used.contains(e)) continue;
				Point[] ends = e.getPoints(); 
				for(Point p : ends){
					if(p.getPosition().equals(c)){
						Cardinal dir = getDirection(ends, p);
						Coord tStart = ends[0].getPosition();
						Coord tEnd = ends[1].getPosition();
						this.tunnels.add(new DungeonTunnel(editor, tStart, tEnd, dir));
						used.add(e);
					}
				}
			}
		}
		
		DungeonNode startDungeonNode = null;
		
		for(Coord c : vertices){
			List<Cardinal> entrances = new ArrayList<Cardinal>();
			for(DungeonTunnel tunnel : this.tunnels){
				Coord[] ends = tunnel.getEnds();
				if(ends[0].equals(c)){
					entrances.add(tunnel.getDirection());
				} else if(ends[1].equals(c)) {
					entrances.add(Cardinal.reverse(tunnel.getDirection()));
				}
			}
			
			Cardinal[] ents = new Cardinal[entrances.size()];
			DungeonNode toAdd = new DungeonNode(entrances.toArray(ents), c);
			this.nodes.add(toAdd);
			
			if(c.equals(start)){
				startDungeonNode = toAdd; 
			}
		}
		
		int attempts = 0;
		
		do{
			end = this.nodes.get(rand.nextInt(this.nodes.size()));
			attempts++;
		} while(end == startDungeonNode || end.getPosition().distance(start) > (16 + attempts * 2));
		
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
	

	
	public DungeonNode getEnd(){
		return this.end;
	}
	
	private Cardinal getDirection(Point[] ends, Point p){
		Coord c1;
		Coord c2;
		
		if(p.getPosition().equals(ends[0].getPosition())){
			c1 = ends[0].getPosition();
			c2 = ends[1].getPosition();
		} else {
			c1 = ends[1].getPosition();
			c2 = ends[0].getPosition();
		}
		
		if(c2.getX() - c1.getX() == 0){
			if(c2.getZ() - c1.getZ() < 0){
				return Cardinal.NORTH;
			} else {
				return Cardinal.SOUTH;
			}
		} else {
			if(c2.getX() - c1.getX() < 0){
				return Cardinal.WEST;
			} else {
				return Cardinal.EAST;
			}
		}
	}
	
	@Override
	public List<DungeonNode> getNodes() {
		return this.nodes;
	}

	@Override
	public List<DungeonTunnel> getTunnels() {
		return this.tunnels;
	}

}
