package greymerk.roguelike;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class Catacomb {
	
	private World world;
	public static final int DEPTH = 5;
	public static final int VERTICAL_SPACING = 10;
	public static final int TOPLEVEL = 50;
	private Dungeon dungeons;
	private Random rand;
	private CatacombNode previous;
	
	public Catacomb(World world, Random rand){
		 this.world = world;
		 this.rand = rand;
		 this.dungeons = new Dungeon(world, rand);
		 this.previous = null;
	}
	
	public void generate(int inX, int inZ){
				
		int x = inX;
		int y = TOPLEVEL;
		int z = inZ;
		
		// generate levels
		while(y > DEPTH){
			
			CatacombLevel level = new CatacombLevel(world, rand, dungeons, x, y, z);
	
			while(!level.isDone()){
				level.update();
			}			

			level.generate();
			CatacombNode end = level.getEnd();
			x = end.getX();
			y = y - VERTICAL_SPACING;
			z = end.getZ();
			
		} 
		
		CatacombTower tower = new CatacombTower(world, rand, inX, TOPLEVEL, inZ);
		tower.generate();
		
		
	}
	
	
}
