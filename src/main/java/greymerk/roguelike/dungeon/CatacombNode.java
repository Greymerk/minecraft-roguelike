package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public class CatacombNode {

	private World world;
	private Random rand;
	private CatacombLevel level;
	private List<CatacombTunneler> tunnelers;
	private Coord pos;
	private IDungeonRoom toGenerate;
	private Cardinal direction;
	
	public CatacombNode (World world, Random rand, CatacombLevel level, ITheme theme, Coord origin){
		this.world = world;
		this.rand = rand;
		this.level = level;
		this.pos = new Coord(origin);
		this.tunnelers = new ArrayList<CatacombTunneler>();
		
		this.direction = Cardinal.directions[rand.nextInt(Cardinal.directions.length)];
		
		if(this.level.inRange(origin.getX(), origin.getZ())){
			spawnTunnelers();
		}
	}
	

	public CatacombNode (World world, Random rand, CatacombLevel level, ITheme theme, CatacombTunneler tunneler){
		this.world = world;

		this.level = level;
		this.pos = tunneler.getPosition();
		
		this.rand = rand;
		
		this.tunnelers = new ArrayList<CatacombTunneler>();
		
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
				this.tunnelers.add(new CatacombTunneler(world, rand, this.level, dir, pos));
			}
		}
	}
	
	public void update(){
		for (CatacombTunneler tunneler : tunnelers){
			tunneler.update();
		}
	}
	
	
	public boolean isDone(){
		for (CatacombTunneler tunneler : tunnelers){
			if(!tunneler.isDone()){
				return false;
			}
		}
	
		return true;
	}
	
	public void construct(World world){

		for (CatacombTunneler tunneler : tunnelers){
			if(tunneler.isDone()) tunneler.construct(world);
		}
	}
	
	public void segments(){

		for (CatacombTunneler tunneler : tunnelers){
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
		
		for(CatacombTunneler tunneler : tunnelers){
			if(tunneler.isDone()) dirs.add(tunneler.getDirection());
		}
		
		return dirs.toArray(new Cardinal[dirs.size()]);
	}
	
	public Coord getPosition(){
		return new Coord(this.pos);
	}
}
