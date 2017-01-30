package greymerk.roguelike.dungeon;

import java.util.Random;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

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
	
	public void encase(IWorldEditor editor, Random rand, ITheme theme){
		IDungeonRoom room = this.getRoom();
		int size = room.getSize();
		Coord s = new Coord(this.getPosition());
		Coord e = new Coord(s);
		s.add(Cardinal.NORTH, size);
		s.add(Cardinal.WEST, size);
		s.add(Cardinal.DOWN, 3);
		e.add(Cardinal.SOUTH, size);
		e.add(Cardinal.EAST, size);
		e.add(Cardinal.UP, 8);
		RectSolid.fill(editor, rand, s, e, theme.getPrimary().getWall());
	}
	
	public Cardinal[] getEntrances(){
		return this.entrances;
	}
	
	public Coord getPosition(){
		return new Coord(this.pos);
	}
	
	public IDungeonRoom getRoom(){
		return toGenerate;
	}
}
