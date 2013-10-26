package greymerk.roguelike;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Tuple;
import net.minecraft.src.World;

public class CatacombNode {

	private World world;
	private Random rand;
	private CatacombLevel level;
	private List<CatacombTunneler> tunnelers;
	private int x;
	private int y;
	private int z;
	private boolean done;
	
	
	private Cardinal direction;
	
	public CatacombNode (World world, Random rand, CatacombLevel level, int x, int y, int z){
		this.world = world;
		this.rand = rand;
		this.level = level;
		this.x = x;
		this.y = y;
		this.z = z;
		this.done = false;
		this.tunnelers = new ArrayList<CatacombTunneler>();
		
		this.direction = Cardinal.values()[rand.nextInt(Cardinal.values().length)];
		
		if(this.level.inRange(x, z)){
			spawnTunnelers();
		}
	}
	

	public CatacombNode (World world, Random rand, CatacombLevel level, CatacombTunneler tunneler){
		this.world = world;
		this.rand = rand;
		this.level = level;
		this.x = tunneler.getX();
		this.y = tunneler.getY();
		this.z = tunneler.getZ();
		this.done = false;
		this.tunnelers = new ArrayList<CatacombTunneler>();
		
		this.direction = Cardinal.reverse(tunneler.getDirection());
		
		if(this.level.inRange(x, z)){
			spawnTunnelers();
		}
	}
	
	private void spawnTunnelers(){
		
		for(Cardinal dir : Cardinal.values()){
			if (dir.equals(direction)){
				continue;
			}
			
			if(level.nodeCount() == 0){
				this.tunnelers.add(new CatacombTunneler(rand, this.level, dir, x, y, z));
			} else if (rand.nextBoolean()){
				this.tunnelers.add(new CatacombTunneler(rand, this.level, dir, x, y, z));
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
			tunneler.construct(world);
		}
	}
	
	public void segments(){

		for (CatacombTunneler tunneler : tunnelers){
			tunneler.addSegments(world);
		}
	}
	
}
