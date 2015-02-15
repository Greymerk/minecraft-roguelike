package greymerk.roguelike.catacomb;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class CatacombTunneler {


	private Random rand;
	private CatacombLevel level;
	private List<Coord> tunnel;
	private Cardinal dir;
	private Coord pos;
	private boolean done;
	private int extend;

	
	public CatacombTunneler(World world, Random rand, CatacombLevel level, Cardinal direction, Coord origin){


		this.rand = rand;
		this.level = level;
		this.dir = direction;
		this.pos = new Coord(origin);
		done = false;
		this.extend = level.getSettings().getScatter() * 2;
		tunnel = new ArrayList<Coord>();
		tunnel.add(new Coord(origin));
	}
	
	public void update(){
		if(done){
			return;
		}
		
		if(level.hasNearbyNode(pos.getX(), pos.getZ(), level.getSettings().getScatter())){
			advance();
		} else {
			if(rand.nextInt(extend) == 0){
				level.spawnNode(this);
				done = true;
			} else {
				advance();
				extend--;
			}
		}
	}
	
	public void advance(){

		Coord toAdd = new Coord(this.pos);
		tunnel.add(new Coord(toAdd));
		toAdd.add(dir, 1);
		this.pos.add(dir);
		
	}
		
	public boolean isDone(){
		return done;
	}
	
	public Cardinal getDirection(){
		return this.dir;
	}
	
	public Coord getPosition(){
		return new Coord(this.pos);
	}
	
	public void construct(World world){
		
		if(tunnel.isEmpty()){
			return;
		}
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		IBlockFactory wallBlocks = this.level.getSettings().getTheme().getPrimaryWall();
		IBlockFactory floor = this.level.getSettings().getTheme().getPrimaryFloor();
		BlockJumble bridgeBlocks = new BlockJumble();
		bridgeBlocks.addBlock(floor);
		bridgeBlocks.addBlock(air);
		
		for (Coord location : tunnel){
			
			int x = location.getX();
			int z = location.getZ();
			
			if(dir == Cardinal.NORTH || dir == Cardinal.SOUTH){
				WorldGenPrimitive.fillRectSolid(world, rand, x - 1, pos.getY(), z, x + 1, pos.getY() + 2, z, air, false, true);
				WorldGenPrimitive.fillRectSolid(world, rand, x - 2, pos.getY() - 1, z, x + 2, pos.getY() + 4, z, wallBlocks, false, true);
				WorldGenPrimitive.fillRectSolid(world, rand, x - 1, pos.getY() - 1, z, x + 1, pos.getY() - 1, z, floor, false, true);
				WorldGenPrimitive.fillRectSolid(world, rand, x - 1, pos.getY() - 1, z, x + 1, pos.getY() - 1, z, bridgeBlocks, true, false);
			} else {
				WorldGenPrimitive.fillRectSolid(world, rand, x, pos.getY(), z - 1, x, pos.getY() + 2, z + 1, air, false, true);
				WorldGenPrimitive.fillRectSolid(world, rand, x, pos.getY() - 1, z - 2, x, pos.getY() + 4, z + 2, wallBlocks, false, true);
				WorldGenPrimitive.fillRectSolid(world, rand, x, pos.getY() - 1, z - 1, x, pos.getY() - 1, z + 1, floor, false, true);
				WorldGenPrimitive.fillRectSolid(world, rand, x, pos.getY() - 1, z - 1, x, pos.getY() - 1, z + 1, bridgeBlocks, true, false);
			}
		}
		
		// end of the tunnel;
		Coord location = tunnel.get(tunnel.size() - 1);
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
	
	public void addSegments(World world){
		for(Coord location : tunnel){
			level.getSettings().getSegments().genSegment(world, rand, level, dir, location);
		}
	}
}
