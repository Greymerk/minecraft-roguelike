package greymerk.roguelike.catacomb;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.ISegment;
import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.catacomb.theme.Themes;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Tuple;
import net.minecraft.src.World;

public class CatacombTunneler {

	private World world;
	private Random rand;
	private CatacombLevel level;
	private List<Coord> tunnel;
	private Cardinal dir;
	private int originX;
	private int originY;
	private int originZ;
	private boolean done;
	private int extend;
	private ITheme theme;
	
	public CatacombTunneler(World world, Random rand, CatacombLevel level, Cardinal direction, ITheme theme, int x, int y, int z){

		this.world = world;
		this.rand = rand;
		this.level = level;
		this.dir = direction;
		this.theme = theme;
		this.originX = x;
		this.originY = y;
		this.originZ = z;
		done = false;
		this.extend = CatacombLevel.SCATTER * 2;
		tunnel = new ArrayList<Coord>();
		tunnel.add(new Coord(x, y, z));
	}
	
	public void update(){
		if(level.full()){
			return;
		}
		
		if(done){
			return;
		}
		
		if(level.hasNearbyNode(originX, originZ, CatacombLevel.SCATTER)){
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

		Coord toAdd = new Coord(originX, originY, originZ);
		tunnel.add(new Coord(toAdd));
		toAdd.add(dir, 1);
		
		originX = toAdd.getX();
		originZ = toAdd.getZ();
		
	}
		
	public boolean isDone(){
		if(level.full()){
			return true;
		}
		
		return done;
	}
	
	public Cardinal getDirection(){
		return this.dir;
	}
	
	public int getX(){
		return originX;
	}
	
	public int getY(){
		return originY;
	}
	
	public int getZ(){
		return originZ;
	}
	
	public void construct(World world){
		
		if(tunnel.isEmpty()){
			return;
		}
		
		IBlockFactory wallBlocks = theme.getPrimaryWall();
		BlockJumble bridgeBlocks = new BlockJumble(wallBlocks);
		bridgeBlocks.addBlock(new MetaBlock(0));
		
		for (Coord location : tunnel){
			
			int x = location.getX();
			int z = location.getZ();
			
			if(dir == Cardinal.NORTH || dir == Cardinal.SOUTH){
				WorldGenPrimitive.fillRectSolid(world, rand, x - 1, originY, z, x + 1, originY + 2, z, 0, 0, 0, false, true);
				WorldGenPrimitive.fillRectSolid(world, rand, x - 2, originY - 1, z, x + 2, originY + 4, z, wallBlocks, false, true);
				WorldGenPrimitive.fillRectSolid(world, rand, x - 1, originY - 1, z, x + 1, originY - 1, z, bridgeBlocks, true, false);
			} else {
				WorldGenPrimitive.fillRectSolid(world, rand, x, originY, z - 1, x, originY + 2, z + 1, 0, 0, 0, false, true);
				WorldGenPrimitive.fillRectSolid(world, rand, x, originY - 1, z - 2, x, originY + 4, z + 2, wallBlocks, false, true);
				WorldGenPrimitive.fillRectSolid(world, rand, x, originY - 1, z - 1, x, originY - 1, z + 1, bridgeBlocks, true, false);
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
			theme.genSegment(world, rand, level, dir, theme, location);
		}
	}
}
