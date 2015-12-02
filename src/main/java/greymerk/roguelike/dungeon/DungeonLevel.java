package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonLevel implements IDungeonLevel{

	private Coord origin;
	private LevelSettings settings;
	private ILevelGenerator generator;
	
	public DungeonLevel(WorldEditor editor, Random rand, LevelSettings settings, Coord origin){
		this.settings = settings;
		this.origin = origin;
	}
	
	public void generate(ILevelGenerator generator, Coord start, DungeonNode oldEnd){
		this.generator = generator;
		generator.generate(start, oldEnd);
	}
	
	public int nodeCount(){
		return this.getNodes().size();
	}

	@Override
	public LevelSettings getSettings(){
		return this.settings;
	}

	@Override
	public List<DungeonNode> getNodes() {
		return this.generator.getNodes();
	}

	@Override
	public List<DungeonTunnel> getTunnels() {
		return this.generator.getTunnels();
	}
	
	@Override
	public boolean hasNearbyNode(Coord pos){
		
		for (DungeonNode node : this.getNodes()){
			int dist = (int) node.getPosition().distance(pos);

			if(dist < node.getSize()){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean inRange(Coord pos) {		
		int dist = (int) this.origin.distance(pos);
		return dist < this.settings.getRange();
	}

	@Override
	public Collection<? extends ITreasureChest> getChests() {
		
		List<ITreasureChest> chests = new ArrayList<ITreasureChest>();
		
		for(DungeonNode node : this.generator.getNodes()){
			IDungeonRoom room = node.getRoom();
			if(room == null) continue;
			chests.addAll(room.getChests());
		}
		
		for(DungeonTunnel tunnel : this.generator.getTunnels()){
			chests.addAll(tunnel.getChests());
		}
		
		System.out.println("num chests " + chests.size());
		
		return chests;
	}
}
