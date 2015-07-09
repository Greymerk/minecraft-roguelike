package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.rooms.DungeonCorner;
import greymerk.roguelike.dungeon.rooms.DungeonLinker;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.util.mst.Edge;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.util.mst.Point;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public class LevelGeneratorMST implements ILevelGenerator{

	World world;
	Random rand;
	IDungeonLevel level;
	private DungeonNode end;
	
	private List<DungeonNode> nodes;
	private List<DungeonTunnel> tunnels;
	
	public LevelGeneratorMST(World world, Random rand, IDungeonLevel level){
		this.world = world;
		this.rand = rand;
		this.level = level;
		
		nodes = new ArrayList<DungeonNode>();
		tunnels = new ArrayList<DungeonTunnel>();
	}	
	
	@Override
	public void generate(Coord start, DungeonNode oldEnd) {
		MinimumSpanningTree mst = new MinimumSpanningTree(rand, 7, 17, new Coord(start));
		List<Edge> edges = mst.getEdges();
		List<Coord> points = mst.getPointPositions();
		List<Edge> used = new ArrayList<Edge>();
		
		
		for(Coord c : points){
			for(Edge e : edges){
				if(used.contains(e)) continue;
				Point[] ends = e.getPoints(); 
				for(Point p : ends){
					if(p.getPosition().equals(c)){
						Cardinal dir = getDirection(ends, p);
						Coord tStart = ends[0].getPosition();
						Coord tEnd = ends[1].getPosition();
						this.tunnels.add(new DungeonTunnel(tStart, tEnd, dir));
						used.add(e);
					}
				}
			}
		}
		

		
		DungeonNode startDungeonNode = null;
		
		for(Coord c : points){
			List<Cardinal> entrances = new ArrayList<Cardinal>();
			for(DungeonTunnel t : this.tunnels){
				Coord[] ends = t.getEnds();
				if(ends[0].equals(c)){
					entrances.add(Cardinal.reverse(t.getDirection()));
				} else {
					entrances.add(t.getDirection());
				}
			}
			
			Cardinal[] ents = new Cardinal[entrances.size()];
			DungeonNode toAdd = new DungeonNode(entrances.toArray(ents), c);
			this.nodes.add(toAdd);
			
			if(c.equals(start)){
				startDungeonNode = toAdd; 
			}
		}
		
		for(DungeonNode d : this.nodes){
			System.out.println(d.getPosition().toString());
		}
		
		
		
		int attempts = 0;
		
		do{
			end = this.nodes.get(rand.nextInt(this.nodes.size()));
			attempts++;
		} while(end == startDungeonNode || end.getPosition().distance(start) > (16 + attempts * 2));
		
		for(DungeonTunnel t : this.tunnels){
			t.construct(world, rand, this.level.getSettings());
		}
		
		Collections.shuffle(nodes, rand);
		
		// node dungeons
		for (DungeonNode node : nodes){
			
			if(node == end){
				continue;
			}
			
			if(node == startDungeonNode){
				continue;
			}

			IDungeonRoom toGenerate = this.level.getSettings().getRooms().get(rand);
			node.setDungeon(toGenerate);
			toGenerate.generate(world, rand, this.level.getSettings(), node.getEntrances(), node.getPosition());
		}
		
		for(DungeonTunnel tunnel : this.tunnels){			
			for(Coord c : tunnel){
				this.level.getSettings().getSegments().genSegment(world, rand, this.level, tunnel.getDirection(), c);
			}
		}
		
		generateLevelLink(world, rand, this.level.getSettings().getTheme(), start, oldEnd);
	}
	
	private void generateLevelLink(World world, Random rand, ITheme theme, Coord start, DungeonNode oldEnd) {
		
		IDungeonRoom downstairs = new DungeonLinker();
		downstairs.generate(world, rand, this.level.getSettings(), Cardinal.directions, start);
		
		if(oldEnd == null) return;
		
		IDungeonRoom upstairs = new DungeonCorner();
		upstairs.generate(world, rand, this.level.getSettings(), oldEnd.getEntrances(), oldEnd.getPosition());
		
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord cursor = new Coord(start);
		for (int i = 0; i < oldEnd.getPosition().getY() - start.getY(); i++){
			WorldGenPrimitive.spiralStairStep(world, rand, cursor, stair, theme.getPrimaryPillar());
			cursor.add(Cardinal.UP);
		}	
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
