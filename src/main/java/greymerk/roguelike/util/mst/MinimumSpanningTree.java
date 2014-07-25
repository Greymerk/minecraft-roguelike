package greymerk.roguelike.util.mst;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.world.World;

public class MinimumSpanningTree extends Graph{
	
	Set<Edge> mstEdges;
	
	public MinimumSpanningTree(Random rand, int size, int edgeLength){
		super(rand, size, edgeLength);
		System.out.println("finished Graph");
		
		mstEdges = new HashSet<Edge>();

		Collections.sort(edges);
		System.out.println("finished sorting");
		
		for(Edge e : edges){
			Point start = e.getPoints()[0];
			Point end = e.getPoints()[1];
			
			if(find(start) == find(end)) continue;
			union(start, end);
			mstEdges.add(e);
		}
		
		System.out.println("finished finding tunnels");
	}
	
	
	private void union(Point a, Point b){
		Point root1 = find(a);
		Point root2 = find(b);
		if(root1 == root2) return;
		
		if(root1.getRank() > root2.getRank()){
			root2.setParent(root1);
		} else {
			root1.setParent(root2);
			if(root1.getRank() == root2.getRank()){
				root2.incRank();
			}
		}
	}
	
	private Point find(Point p){
		if(p.getParent() == p) return p;
		p.setParent(find(p.getParent()));
		return p.getParent();
	}
	
	public void generate(World world, Random rand, IBlockFactory blocks, Coord pos){
		
		System.out.println("starting to generate");
		
		for(Edge e : this.mstEdges){
			Coord start = e.getPoints()[0].getPosition(pos);
			Coord end = e.getPoints()[1].getPosition(pos);
			
			System.out.println(start.toString() + ' ' + end.toString());
			
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
		}
		
		System.out.println("finished generating");
	}
	
	public List<Edge> getEdges(){
		List<Edge> toReturn = new ArrayList<Edge>();
		toReturn.addAll(this.mstEdges);
		return toReturn;
	}
}
