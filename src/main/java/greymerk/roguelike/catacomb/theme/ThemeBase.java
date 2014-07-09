package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.segment.ISegment;
import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;
import net.minecraft.world.World;

public class ThemeBase implements ITheme {
	
	protected IBlockSet walls;
	protected IBlockSet decor;
	protected WeightedRandomizer<Segment> segments;
	protected Segment arch;
	
	public ThemeBase(IBlockSet walls, IBlockSet decor, WeightedRandomizer<Segment> segments, Segment arch){
		this.walls = walls;
		this.decor = decor;
		this.segments = segments;
		this.arch = arch;
	}
	
	public ThemeBase(ThemeBase base, IBlockSet walls, IBlockSet decor, WeightedRandomizer<Segment> segments, Segment arch){
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
	public void genSegment(World world, Random rand, CatacombLevel level, Cardinal dir, Coord pos) {
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			ISegment seg = pickSegment(world, rand, level, dir, pos);
			if(seg == null) return;
			seg.generate(world, rand, level, orth, this, x, y, z);
		}
		
		if(!level.hasNearbyNode(x, z) && rand.nextInt(3) == 0) addSupport(world, rand, this, x, y, z);
		
	}
	
	private ISegment pickSegment(World world, Random rand, CatacombLevel level, Cardinal dir, Coord pos){
		int x = pos.getX();
		int z = pos.getZ();
		
		if((dir == Cardinal.NORTH || dir == Cardinal.SOUTH) && z % 3 == 0){
			if(z % 6 == 0){
				return Segment.getSegment(arch);
			}
			return Segment.getSegment(this.segments.get(rand));
		}
		
		if((dir == Cardinal.WEST || dir == Cardinal.EAST) && x % 3 == 0){
			if(x % 6 == 0){
				return Segment.getSegment(arch);
			}
			return Segment.getSegment(this.segments.get(rand));
		}
		
		return null;
	}
	
	private void addSupport(World world, Random rand, ITheme theme, int x, int y, int z){
		if(!world.isAirBlock(x, y - 2, z)) return;
		
		WorldGenPrimitive.fillDown(world, rand, x, y - 2, z, theme.getPrimaryPillar());
		
		MetaBlock stair = theme.getPrimaryStair();
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.setBlock(world, x - 1, y - 2, z, stair);
		
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.setBlock(world, x + 1, y - 2, z, stair);
		
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.setBlock(world, x, y - 2, z + 1, stair);
		
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.setBlock(world, x, y - 2, z - 1, stair);
		
	}
}
