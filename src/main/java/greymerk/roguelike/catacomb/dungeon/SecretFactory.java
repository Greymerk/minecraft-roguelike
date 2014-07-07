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
			Coord end = new Coord(pos);
			end.add(dir, size + 5);
			end.add(Cardinal.UP);
			WorldGenPrimitive.fillRectSolid(world, rand, pos, end, new MetaBlock(Blocks.air), true, true);
			end.add(Cardinal.DOWN);
			this.prototype.generate(world, rand, theme, end.getX(), end.getY(), end.getZ());
			count -= 1;
		}
	}
}
