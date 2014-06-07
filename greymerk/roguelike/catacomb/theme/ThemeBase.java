package greymerk.roguelike.catacomb.theme;

import java.util.List;
import java.util.Random;

import net.minecraft.src.World;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.dungeon.IDungeonFactory;
import greymerk.roguelike.catacomb.segment.ISegment;
import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

public class ThemeBase implements ITheme {
	
	protected IBlockSet walls;
	protected IBlockSet decor;
	protected List<Segment> segments;
	protected Segment arch;
	
	public ThemeBase(IBlockSet walls, IBlockSet decor, List<Segment> segments, Segment arch){
		this.walls = walls;
		this.decor = decor;
		this.segments = segments;
		this.arch = arch;
	}
	
	public ThemeBase(ThemeBase base, IBlockSet walls, IBlockSet decor, List<Segment> segments, Segment arch){
		this.walls = walls == null ? base.walls : walls;
		this.decor = decor == null ? base.decor : decor;
		this.segments = segments == null ? base.segments : segments;
		this.arch = arch == null ? base.arch : arch;
	}
	
	public ThemeBase(){}
	
	@Override
	public IBlockFactory getPrimaryWall() {
		return walls.getFill();
	}
	
	@Override
	public MetaBlock getPrimaryStair() {
		return walls.getStair();
	}

	@Override
	public IBlockFactory getPrimaryPillar() {
		return walls.getPillar();
	}
	
	@Override
	public IBlockFactory getSecondaryPillar() {
		return decor.getPillar();
	}
	
	@Override
	public IBlockFactory getSecondaryWall() {
		return decor.getFill();
	}

	@Override
	public MetaBlock getSecondaryStair() {
		return decor.getStair();
	}

	@Override
	public void genSegment(World world, Random rand, CatacombLevel level, Cardinal dir, ITheme theme, Coord pos) {
		ISegment seg = pickSegment(world, rand, level, dir, pos);
		
		if(seg == null) return;
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		seg.generate(world, rand, level, dir, theme, x, y, z);
	}
	
	private ISegment pickSegment(World world, Random rand, CatacombLevel level, Cardinal dir, Coord pos){
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		ISegment seg = null;
		
		if((dir == Cardinal.NORTH || dir == Cardinal.SOUTH) && z % 3 == 0){
			if(z % 6 == 0){
				return Segment.getSegment(arch);
			}
			return Segment.getSegment(segments.get(rand.nextInt(segments.size())));
		}
		
		if((dir == Cardinal.WEST || dir == Cardinal.EAST) && x % 3 == 0){
			if(x % 6 == 0){
				return Segment.getSegment(arch);
			}
			return Segment.getSegment(segments.get(rand.nextInt(segments.size())));
		}
		
		return null;
	}
}
