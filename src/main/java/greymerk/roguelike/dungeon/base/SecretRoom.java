package greymerk.roguelike.dungeon.base;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class SecretRoom implements ISecretRoom {

	private int count;
	private IDungeonRoom prototype;
	
	public SecretRoom(DungeonRoom type, int count){
		this.count = count;
		this.prototype = DungeonRoom.getInstance(type);
	}
	
	public SecretRoom(SecretRoom toCopy){
		this.count = toCopy.count;
		this.prototype = toCopy.prototype;
	}
	
	private boolean isValid(WorldEditor editor, Random rand, Cardinal dir, Coord pos){
		if(count <= 0) return false;
		Coord cursor = new Coord(pos);
		cursor.add(dir, prototype.getSize() + 5);
		
		return prototype.validLocation(editor, dir, cursor.getX(), cursor.getY(), cursor.getZ());
	}
	
	@Override
	public boolean genRoom(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos){
		if(!isValid(editor, rand, dir, pos)) return false;
		
		int size = prototype.getSize();
		
		Coord start = new Coord(pos);
		Coord end = new Coord(pos);
		start.add(Cardinal.getOrthogonal(dir)[0]);
		start.add(Cardinal.DOWN);
		start.add(dir, 2);
		end.add(Cardinal.getOrthogonal(dir)[1]);
		end.add(dir, size + 5);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, settings.getTheme().getPrimaryWall(), false, true);
		
		start = new Coord(pos);
		end = new Coord(pos);
		end.add(dir, size + 5);
		end.add(Cardinal.UP);
		editor.fillRectSolid(rand, pos, end, BlockType.get(BlockType.AIR), true, true);
		
		end.add(Cardinal.DOWN);
		this.prototype.generate(editor, rand, settings, new Cardinal[]{dir}, end);
		count -= 1;
		
		return true;
	}
}
