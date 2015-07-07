package greymerk.roguelike.dungeon.base;

import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

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
	
	private boolean isValid(World world, Random rand, Cardinal dir, Coord pos){
		if(count <= 0) return false;
		Coord cursor = new Coord(pos);
		cursor.add(dir, prototype.getSize() + 5);
		
		return prototype.validLocation(world, dir, cursor.getX(), cursor.getY(), cursor.getZ());
	}
	
	@Override
	public boolean genRoom(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord pos){
		if(!isValid(world, rand, dir, pos)) return false;
		
		int size = prototype.getSize();
		
		Coord start = new Coord(pos);
		Coord end = new Coord(pos);
		start.add(Cardinal.getOrthogonal(dir)[0]);
		start.add(Cardinal.DOWN);
		start.add(dir, 2);
		end.add(Cardinal.getOrthogonal(dir)[1]);
		end.add(dir, size + 5);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, settings.getTheme().getPrimaryWall(), false, true);
		
		start = new Coord(pos);
		end = new Coord(pos);
		end.add(dir, size + 5);
		end.add(Cardinal.UP);
		WorldGenPrimitive.fillRectSolid(world, rand, pos, end, new MetaBlock(Blocks.air), true, true);
		
		end.add(Cardinal.DOWN);
		this.prototype.generate(world, rand, settings, new Cardinal[]{dir}, end);
		count -= 1;
		
		return true;
	}
}
