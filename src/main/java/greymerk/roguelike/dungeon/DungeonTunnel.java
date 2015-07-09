package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonTunnel implements Iterable<Coord>{

	private Coord start;
	private Coord end;
	private Cardinal dir;
	
	public DungeonTunnel(Coord start, Coord end, Cardinal dir){
		this.start = start;
		this.end = end;
		this.dir = dir;
	}

	@Override
	public Iterator<Coord> iterator() {
		List<Coord> t = WorldGenPrimitive.getRectSolid(start, end);
		return t.iterator();
	}
	
	public void construct(World world, Random rand, LevelSettings settings){
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		IBlockFactory wallBlocks = settings.getTheme().getPrimaryWall();
		IBlockFactory floor = settings.getTheme().getPrimaryFloor();
		BlockJumble bridgeBlocks = new BlockJumble();
		bridgeBlocks.addBlock(floor);
		bridgeBlocks.addBlock(air);
		
		Coord s = new Coord(this.start);
		s.add(Cardinal.NORTH);
		s.add(Cardinal.EAST);
		Coord e = new Coord(this.end);
		e.add(Cardinal.SOUTH);
		e.add(Cardinal.WEST);
		e.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, s, e, air, true, true);
		
		s.add(Cardinal.NORTH);
		s.add(Cardinal.EAST);
		s.add(Cardinal.DOWN);
		e.add(Cardinal.SOUTH);
		e.add(Cardinal.WEST);
		e.add(Cardinal.UP);
		WorldGenPrimitive.fillRectHollow(world, rand, s, e, wallBlocks, false, true);
		
		s = new Coord(this.start);
		s.add(Cardinal.NORTH);
		s.add(Cardinal.EAST);
		s.add(Cardinal.DOWN);
		e = new Coord(this.end);
		e.add(Cardinal.SOUTH);
		e.add(Cardinal.WEST);
		e.add(Cardinal.DOWN);
		WorldGenPrimitive.fillRectSolid(world, rand, s, e, bridgeBlocks, true, false);
		
		// end of the tunnel;
		Coord location = new Coord(end);
		location.add(dir, 1);
		
		Coord start = new Coord(location);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start.add(orth[0], 2);
		start.add(Cardinal.UP, 2);
		Coord end = new Coord(location);
		end.add(orth[1], 2);
		end.add(Cardinal.DOWN, 2);
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, wallBlocks, false, true);
	}
	
	public Coord[] getEnds(){
		Coord[] toReturn = new Coord[2];
		toReturn[0] = new Coord(start);
		toReturn[1] = new Coord(end);
		return toReturn;
	}
	
	public Cardinal getDirection(){
		return this.dir;
	}
	
}
