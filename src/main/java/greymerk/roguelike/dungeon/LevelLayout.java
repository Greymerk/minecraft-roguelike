package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelLayout {

	private List<DungeonNode> nodes;
	private List<DungeonTunnel> tunnels;
	private DungeonNode start;
	private DungeonNode end;
	
	public LevelLayout(){
		this.nodes = new ArrayList<DungeonNode>();
		this.tunnels = new ArrayList<DungeonTunnel>();
	}
	
	public void setStart(DungeonNode start){
		this.start = start;
		this.addNode(start);
	}
	
	public void setEnd(DungeonNode end){
		this.end = end;
		this.addNode(end);
	}
	
	public DungeonNode getStart(){
		return this.start;
	}
	
	public DungeonNode getEnd(){
		return this.end;
	}
	
	public void addNode(DungeonNode node){
		this.nodes.add(node);
	}
	
	public void addTunnel(DungeonTunnel tunnel){
		this.tunnels.add(tunnel);
	}
	
	public void addTunnels(List<DungeonTunnel> tunnels){
		this.tunnels.addAll(tunnels);
	}
	
	public List<DungeonNode> getNodes(){
		return this.nodes;
	}
	
	public List<DungeonTunnel> getTunnels(){
		return this.tunnels;
	}
	
	public void setStartEnd(Random rand, DungeonNode start){
		this.start = start;
		
		int attempts = 0;
		do{
			end = this.nodes.get(rand.nextInt(this.nodes.size()));
			attempts++;
		} while(end == this.start || end.getPosition().distance(start.getPosition()) > (16 + attempts * 2));
	}
}
