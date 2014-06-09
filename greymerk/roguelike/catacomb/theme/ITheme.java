package greymerk.roguelike.catacomb.theme;

import java.util.Random;

import net.minecraft.src.World;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.dungeon.IDungeonFactory;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

public interface ITheme {

	public IBlockFactory getPrimaryWall();
		
	public MetaBlock getPrimaryStair();
	
	public IBlockFactory getPrimaryPillar();
	
	public IBlockFactory getSecondaryWall();
	
	public MetaBlock getSecondaryStair();
	
	public IBlockFactory getSecondaryPillar();
	
	public void genSegment(World world, Random rand, CatacombLevel level, Cardinal dir, Coord pos);
		
}
