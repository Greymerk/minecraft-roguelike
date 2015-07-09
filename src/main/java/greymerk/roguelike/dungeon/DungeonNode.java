package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

public class DungeonNode {


	private Coord pos;
	private IDungeonRoom toGenerate;
	private Cardinal[] entrances;
	
	public DungeonNode (Cardinal[] entrances, Coord origin){
		this.entrances = entrances;
		this.pos = new Coord(origin);
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
		return this.entrances;
	}
	
	public Coord getPosition(){
		return new Coord(this.pos);
	}
}
