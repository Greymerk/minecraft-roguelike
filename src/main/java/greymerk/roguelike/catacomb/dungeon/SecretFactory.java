package greymerk.roguelike.catacomb.dungeon;

import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SecretFactory {

	private ArrayList<SecretRoom> secrets;
	
	public SecretFactory(){
		secrets = new ArrayList<SecretRoom>();
	}
	
	public void addRoom(Dungeon type){
		addRoom(type, 1);
	}
	
	public void addRoom(Dungeon type, int count){
		secrets.add(new SecretRoom(type, count));
	}
	
	public void genRoom(World world, Random rand, ITheme theme, Cardinal dir, Coord pos){
		for(SecretRoom room : secrets){
			if(room.isValid(world, rand, dir, pos)){
				room.genRoom(world, rand, theme, dir, pos);
			}
		}
	}
	
	private class SecretRoom{
		
		private int count;
		private IDungeon prototype;
		
		public SecretRoom(Dungeon type, int count){
			this.count = count;
			this.prototype = Dungeon.getInstance(type);
		}
		
		public boolean isValid(World world, Random rand, Cardinal dir, Coord pos){
			if(count <= 0) return false;
			Coord cursor = new Coord(pos);
			cursor.add(dir, prototype.getSize() + 5);
			
			return prototype.validLocation(world, dir, cursor.getX(), cursor.getY(), cursor.getZ());
		}
		
		public void genRoom(World world, Random rand, ITheme theme, Cardinal dir, Coord pos){
			int size = prototype.getSize();
			
			Coord start = new Coord(pos);
			Coord end = new Coord(pos);
			start.add(Cardinal.getOrthogonal(dir)[0]);
			start.add(Cardinal.DOWN);
			end.add(Cardinal.getOrthogonal(dir)[1]);
			end.add(dir, size + 5);
			end.add(Cardinal.UP, 2);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), false, true);
			
			start = new Coord(pos);
			end = new Coord(pos);
			end.add(dir, size + 5);
			end.add(Cardinal.UP);
			WorldGenPrimitive.fillRectSolid(world, rand, pos, end, new MetaBlock(Blocks.air), true, true);
			
			end.add(Cardinal.DOWN);
			this.prototype.generate(world, rand, theme, new Cardinal[]{dir}, end.getX(), end.getY(), end.getZ());
			count -= 1;
		}
	}
}
