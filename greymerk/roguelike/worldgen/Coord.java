package greymerk.roguelike.worldgen;

public class Coord{

	private int x;
	private int y;
	private int z;
	
	public Coord(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Coord(Coord toClone){
		this.x = toClone.x;
		this.y = toClone.y;
		this.z = toClone.z;
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
	
	public void add(Cardinal dir, int amount){
		
		switch(dir){
		case EAST: x += amount; return;
		case WEST: x -= amount; return;
		case UP: y += amount; return;
		case DOWN: y -= amount; return;
		case NORTH: z -= amount; return;
		case SOUTH: z += amount; return;

		}		
	}

	public static void correct(Coord one, Coord two){
		
		int temp;
		
		if(two.x < one.x){
			temp = two.x;
			two.x = one.x;
			one.x = temp;
		}

		if(two.y < one.y){
			temp = two.y;
			two.y = one.y;
			one.y = temp;
		}
		
		if(two.z < one.z){
			temp = two.z;
			two.z = one.z;
			one.z = temp;
		}
	}
}
