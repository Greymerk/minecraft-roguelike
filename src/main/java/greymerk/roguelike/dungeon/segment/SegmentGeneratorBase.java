package greymerk.roguelike.dungeon.segment;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.world.World;

public class SegmentGeneratorBase implements ISegmentGenerator{
	
	protected Segment arch;
	protected WeightedRandomizer<Segment> segments;
	
	public SegmentGeneratorBase(){
		this.segments = new WeightedRandomizer<Segment>();
		this.segments.add(new WeightedChoice<Segment>((Segment.SHELF), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.INSET), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.DOOR), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.FIREPLACE), 1));
		
		this.arch = Segment.ARCH;
	}
	
	public void add(Segment toAdd, int weight){
		this.segments.add(new WeightedChoice<Segment>(toAdd, weight));
	}
	
	@Override
	public void genSegment(World world, Random rand, DungeonLevel level, Cardinal dir, Coord pos) {
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			ISegment seg = pickSegment(world, rand, level, dir, pos);
			if(seg == null) return;
			seg.generate(world, rand, level, orth, level.getSettings().getTheme(), x, y, z);
		}
		
		if(!level.hasNearbyNode(x, z) && rand.nextInt(3) == 0) addSupport(world, rand, level.getSettings().getTheme(), x, y, z);
		
	}
	
	private ISegment pickSegment(World world, Random rand, DungeonLevel level, Cardinal dir, Coord pos){
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
		if(!world.isAirBlock(new Coord(x, y - 2, z).getBlockPos())) return;
		
		WorldGenPrimitive.fillDown(world, rand, new Coord(x, y - 2, z), theme.getPrimaryPillar());
		
		MetaBlock stair = theme.getPrimaryStair();
		WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, true);
		WorldGenPrimitive.setBlock(world, x - 1, y - 2, z, stair);
		
		WorldGenPrimitive.blockOrientation(stair, Cardinal.EAST, true);
		WorldGenPrimitive.setBlock(world, x + 1, y - 2, z, stair);
		
		WorldGenPrimitive.blockOrientation(stair, Cardinal.SOUTH, true);
		WorldGenPrimitive.setBlock(world, x, y - 2, z + 1, stair);
		
		WorldGenPrimitive.blockOrientation(stair, Cardinal.NORTH, true);
		WorldGenPrimitive.setBlock(world, x, y - 2, z - 1, stair);	
	}
}
