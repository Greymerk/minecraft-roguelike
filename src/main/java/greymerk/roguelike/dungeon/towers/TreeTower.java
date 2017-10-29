package greymerk.roguelike.dungeon.towers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.Line;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.shapes.Sphere;

public class TreeTower implements ITower{

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord pos) {
		
		Coord start;
		Coord end;
		Coord ground = Tower.getBaseCoord(editor, pos);
		
		start = new Coord(ground);
		start.add(Cardinal.DOWN, 10);
		

		new Branch(rand, start).generate(editor, rand);
		
		start = new Coord(ground);
		start.add(new Coord(-2, 0, -2));
		end = new Coord(ground);
		end.add(new Coord(2, 5, 2));
		BlockType.get(BlockType.AIR).fill(editor, rand, new RectSolid(start, end));
		
		start = new Coord(ground);
		start.add(Cardinal.DOWN);
		for(Coord p : new RectSolid(start, pos)){
			editor.spiralStairStep(rand, p, theme.getPrimary().getStair(), Log.getLog(Wood.OAK));
		}
	}
	
	private class Branch{
		
		Coord start;
		Coord end;
		List<Branch> branches;
		int thickness;
		
		public Branch(Random rand, Coord start){
			this.start = new Coord(start);
			this.branches = new ArrayList<Branch>();
			int counter = 12;
			int thickness = 5;
			this.thickness = thickness;
			int density = 3;
			double noise = 0.3;
			double pitch = 0;
			double yaw = Math.PI / 2;
			this.end = getEnd(start, 4, pitch, yaw);
			
			for(int i = 0; i < density; ++i){
				this.branches.add(
					new Branch(
						rand,
						new Coord(end),
						counter,
						10,
						thickness,
						3,
						noise,
						pitch + ((Math.PI * 2 / density) * i),
						yaw + (rand.nextDouble() - 0.5) * noise
					)
				);
			}
		}
		
		public Branch(Random rand, Coord start, int counter, int length, int thickness, int density, double noise, double pitch, double yaw){
			
			this.start = new Coord(start);
			this.thickness = thickness;
			this.branches = new ArrayList<Branch>();
			this.end = getEnd(start, length, pitch, yaw);
			
			if(counter <= 0 || thickness <= 0 || length <= 0) return;
			
			for(int i = 0; i < density; ++i){
				this.branches.add(
					new Branch(
						rand,
						new Coord(end),
						counter - 1,
						length - 1,
						thickness - 1,
						density + 1,
						noise + 0.3,
						pitch + (rand.nextDouble() - 0.5) * noise,
						yaw + (rand.nextDouble() - 0.5) * noise
					)
				);
			}
		}
		
		public void generate(IWorldEditor editor, Random rand){
			
			MetaBlock oak = Log.get(Wood.OAK, start.dirTo(end));
			
			for(Branch b : this.branches){
				b.generate(editor, rand);
			}
			
			if(thickness == 1){
				new Line(start, end).fill(editor, rand, oak);
			} else if(thickness > 1){
				for(Coord pos : new Line(start, end)){
					Coord s = new Coord(pos);
					Coord e = new Coord(s);
					e.add(new Coord(thickness, thickness, thickness));
					new Sphere(s, e).fill(editor, rand, oak, true, false);
				}
			}
			
			if(this.branches.isEmpty() && rand.nextInt(3) == 0){
				BlockJumble leaves = new BlockJumble();
				leaves.addBlock(Leaves.get(Leaves.OAK, false));
				leaves.addBlock(BlockType.get(BlockType.AIR));
				
				Coord s = new Coord(end);
				Coord e = new Coord(s);
				e.add(new Coord(3, 3, 3));
				new Sphere(s, e).fill(editor, rand, leaves, true, false);
			}
		}
		
		private Coord getEnd(Coord start, int length, double pitch, double yaw){
			Coord end = new Coord(start);
			Coord offset = new Coord(
					(int)(Math.cos(pitch) * Math.cos(yaw) * length),
					(int)(Math.sin(yaw) * length),
					(int)(Math.sin(pitch) * Math.cos(yaw) * length)
					);
			
			end = new Coord(start);
			end.add(offset);
			return end;
		}
	}
}
