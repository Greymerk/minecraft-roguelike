package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public class DungeonNode {

	private World world;
	private Random rand;
	private DungeonLevel level;
	private List<DungeonTunneler> tunnelers;
	private Coord pos;
	private IDungeonRoom toGenerate;
	private Cardinal direction;
	
	public DungeonNode (World world, Random rand, DungeonLevel level, ITheme theme, Coord origin){
		this.world = world;
		this.rand = rand;
		this.level = level;
		this.pos = new Coord(origin);
		this.tunnelers = new ArrayList<DungeonTunneler>();
		
		this.direction = Cardinal.directions[rand.nextInt(Cardinal.directions.length)];
		
		if(this.level.inRange(origin.getX(), origin.getZ())){
			spawnTunnelers();
		}
	}
	

	public DungeonNode (World world, Random rand, DungeonLevel level, ITheme theme, DungeonTunneler tunneler){
		this.world = world;

		this.level = level;
		this.pos = tunneler.getPosition();
		
		this.rand = rand;
		
		this.tunnelers = new ArrayList<DungeonTunneler>();
		
		this.direction = Cardinal.reverse(tunneler.getDirection());
		
		if(this.level.inRange(pos.getX(), pos.getZ())){
			spawnTunnelers();
		}
	}
	
	private void spawnTunnelers(){
		
		for(Cardinal dir : Cardinal.directions){
			
			if (dir.equals(direction)){
				continue;
			}
			
			if(level.nodeCount() == 0 || tunnelers.isEmpty() || rand.nextBoolean()){
				this.tunnelers.add(new DungeonTunneler(world, rand, this.level, dir, pos));
			}
		}
	}
	
	public void update(){
		for (DungeonTunneler tunneler : tunnelers){
			tunneler.update();
		}
	}
	
	
	public boolean isDone(){
		for (DungeonTunneler tunneler : tunnelers){
			if(!tunneler.isDone()){
				return false;
			}
		}
	
		return true;
	}
	
	public void construct(World world){

		for (DungeonTunneler tunneler : tunnelers){
			if(tunneler.isDone()) tunneler.construct(world);
		}
	}
	
	public void segments(){

		for (DungeonTunneler tunneler : tunnelers){
			if(tunneler.isDone()) tunneler.addSegments(world);
		}
	}
	
	public void setDungeon(IDungeonRoom toGenerate){
		this.toGenerate = toGenerate;
	}
	
	public int getSize(){
		
		if(toGenerate == null){
			return 6;
		}
		
		return toGenerate.getSize();
		
	}
	
	public Cardinal[] getEntrances(){
		List<Cardinal> dirs = new ArrayList<Cardinal>();
		
		dirs.add(this.direction);
		
		for(DungeonTunneler tunneler : tunnelers){
			if(tunneler.isDone()) dirs.add(tunneler.getDirection());
		}
		
		return dirs.toArray(new Cardinal[dirs.size()]);
	}
	
	public Coord getPosition(){
		return new Coord(this.pos);
	}
}
