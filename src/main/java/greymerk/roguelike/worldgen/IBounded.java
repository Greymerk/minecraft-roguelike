package greymerk.roguelike.worldgen;

public interface IBounded {
	
	public BoundingBox getBoundingBox();
	
	public boolean collide(IBounded other);
	
}
