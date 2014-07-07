package greymerk.roguelike.catacomb;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public class CatacombNode {

	private World world;
	private Random rand;
	private CatacombLevel level;
	private List<CatacombTunneler> tunnelers;
	private ITheme theme;
	private int x;
	private int y;
	private int z;
	private IDungeon toGenerate;
	private Cardinal direction;
	
	public CatacombNode (World world, Random rand, CatacombLevel level, ITheme theme, int x, int y, int z){
		this.world = world;
		this.rand = rand;
		this.level = level;
		this.theme = theme;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tunnelers = new ArrayList<CatacombTunneler>();
		
		this.direction = Cardinal.directions[rand.nextInt(Cardinal.directions.length)];
		
		if(this.level.inRange(x, z)){
			spawnTunnelers();
		}
	}
	

	public CatacombNode (World world, Random rand, CatacombLevel level, ITheme theme, CatacombTunneler tunneler){
		this.world = world;

		this.level = level;
		this.theme = theme;
		this.x = tunneler.getX();
		this.y = tunneler.getY();
		this.z = tunneler.getZ();
		
		this.rand = rand;
		
		this.tunnelers = new ArrayList<CatacombTunneler>();
		
		this.direction = Cardinal.reverse(tunneler.getDirection());
		
		if(this.level.inRange(x, z)){
			spawnTunnelers();
		}
	}
	
	private void spawnTunnelers(){
		
		for(Cardinal dir : Cardinal.directions){
			
			if (dir.equals(direction)){
				continue;
			}
			
			if(level.nodeCount() == 0 || tunnelers.isEmpty() || rand.nextBoolean()){
				this.tunnelers.add(new CatacombTunneler(world, rand, this.level, dir, theme, x, y, z));
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
	
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
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
	
	public void setDungeon(IDungeon toGenerate){
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
		for(CatacombTunneler tunneler : tunnelers){
			if(tunneler.isDone()) dirs.add(tunneler.getDirection());
		}
		
		return dirs.toArray(new Cardinal[dirs.size()]);
	}
}
