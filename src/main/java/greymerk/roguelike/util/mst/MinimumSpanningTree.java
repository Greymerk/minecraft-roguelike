package greymerk.roguelike.util.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;

public class MinimumSpanningTree extends Graph{
	
	Set<Edge> mstEdges;
	
	public MinimumSpanningTree(Random rand, int size, int edgeLength){
		this(rand, size, edgeLength, new Coord(0, 0, 0));
	}
	
	public MinimumSpanningTree(Random rand, int size, int edgeLength, Coord origin){
		super(rand, size, edgeLength, origin);
		
		mstEdges = new HashSet<Edge>();

		Collections.sort(edges);
		
		for(Edge e : edges){
			Point start = e.getPoints()[0];
			Point end = e.getPoints()[1];
			
			if(find(start) == find(end)) continue;
			union(start, end);
			mstEdges.add(e);
		}
		
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
	
	public void generate(IWorldEditor editor, Random rand, IBlockFactory blocks, Coord pos){
		
		for(Edge e : this.mstEdges){
			
			Coord start = e.getPoints()[0].getPosition();
			start.add(pos);
			Coord end = e.getPoints()[1].getPosition();
			end.add(pos);
			
			RectHollow.fill(editor, rand, start, end, blocks);
		}
	}
	
	public List<Edge> getEdges(){
		List<Edge> toReturn = new ArrayList<Edge>();
		toReturn.addAll(this.mstEdges);
		return toReturn;
	}
}
