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

import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public class DungeonLevel implements IDungeonLevel{

	private World world;
	private Random rand;
	private Coord origin;
	private LevelSettings settings;
	private ILevelGenerator generator;
	
	public DungeonLevel(World world, Random rand, LevelSettings settings, Coord origin){
		this.world = world;
		this.rand = rand;
		this.origin = origin;
		this.settings = settings;		
	}
	
	public void generate(Coord start, DungeonNode oldEnd){
		
		if(oldEnd == null){
			this.generator = new LevelGeneratorClassic(world, rand, this, start, null);	
		} else {
			this.generator = new LevelGeneratorClassic(world, rand, this, start, oldEnd);
		}

		for(DungeonTunnel t : this.getTunnels()){
			t.construct(world, rand, settings);
		}
		
		List<DungeonNode> nodes = this.getNodes();
		Collections.shuffle(nodes, rand);
		
		// node dungeons
		for (DungeonNode node : nodes){
			
			if(node == this.generator.getEnd()){
				continue;
			}
			
			if(node.getPosition().equals(start)){
				generateLevelLink(world, rand, this.settings.getTheme(), node, oldEnd);
			}

			IDungeonRoom toGenerate = settings.getRooms().get(rand);
			node.setDungeon(toGenerate);
			toGenerate.generate(world, rand, settings, node.getEntrances(), node.getPosition());
		}
		
		for(DungeonTunnel tunnel : this.getTunnels()){			
			for(Coord c : tunnel){
				System.out.println(c.toString());
				this.settings.getSegments().genSegment(world, rand, this, tunnel.getDirection(), c);
			}
		}
		
	}
	
	private void generateLevelLink(World world, Random rand, ITheme theme, DungeonNode start, DungeonNode oldEnd) {
		
		IDungeonRoom downstairs = new DungeonLinker();
		downstairs.generate(world, rand, this.settings, start.getEntrances(), start.getPosition());
		
		if(oldEnd == null) return;
		
		IDungeonRoom upstairs = new DungeonCorner();
		upstairs.generate(world, rand, this.settings, oldEnd.getEntrances(), oldEnd.getPosition());
		
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord cursor = new Coord(start.getPosition());
		for (int i = 0; i < oldEnd.getPosition().getY() - start.getPosition().getY(); i++){
			WorldGenPrimitive.spiralStairStep(world, rand, cursor, stair, theme.getPrimaryPillar());
			cursor.add(Cardinal.UP);
		}	
	}
		
	@Override
	public DungeonNode getEnd(){
		return this.generator.getEnd();
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

}
