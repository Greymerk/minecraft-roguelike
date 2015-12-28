package greymerk.roguelike.worldgen;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;

public interface IWorldEditor {

	boolean setBlock(Coord pos, IBlockState state, int flag, boolean fillAir, boolean replaceSolid);

	boolean setBlock(Random rand, Coord coord, IBlockFactory fillOne, boolean fillAir, boolean replaceSolid);
	
	boolean setBlock(Coord cursor, MetaBlock metaBlock);

	boolean setBlock(Random rand, int x, int i, int z, IBlockFactory wall, boolean b, boolean c);

	boolean setBlock(int x, int i, int j, MetaBlock metaBlock);
	
	boolean canPlace(MetaBlock vine, Coord c, Cardinal dir);

	void fillDown(Random rand, Coord cursor, IBlockFactory pillar);

	void fillRectHollow(Random rand, Coord start, Coord end, IBlockFactory primaryWall, boolean fillAir, boolean replaceSolid);

	void fillRectHollow(Random rand, int i, int y, int j, int k, int l, int m, IBlockFactory walls, boolean b, boolean c);

	void fillRectSolid(Random rand, Coord start, Coord end, IBlockFactory primaryWall, boolean fillAir, boolean replaceSolid);

	void fillRectSolid(Random rand, int i, int j, int k, int l, int m, int n, IBlockFactory fenceRandom);

	void fillRectSolid(Random rand, int i, int y, int j, int k, int l, int m, IBlockFactory plank, boolean b, boolean c);

	List<Coord> getRectHollow(Coord start, Coord end);

	List<Coord> getRectHollow(int i, int j, int k, int l, int m, int n);

	List<Coord> getRectSolid(Coord start, Coord end);
	
	MetaBlock getBlock(Coord cursor);

	boolean validGroundBlock(Coord cursor);

	BiomeGenBase getBiome(Coord coord);

	List<Coord> getRectSolid(int i, int j, int k, int l, int m, int n);

	boolean isAirBlock(Coord coord);

	int getDimension();

	void spiralStairStep(Random rand, Coord c, IStair stair, IBlockFactory pillar);

	long getSeed();

	int getStat(Block stonebrick);

	TileEntity getTileEntity(Coord pos);

	TreasureManager getTreasure();

	void addChest(ITreasureChest chest);

	Random setSeed(int m, int n, int i);

}
