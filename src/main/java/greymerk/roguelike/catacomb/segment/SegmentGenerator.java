package greymerk.roguelike.catacomb.segment;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SegmentGenerator implements ISegmentGenerator{
	
	protected Segment arch;
	protected WeightedRandomizer<Segment> segments;
	
	public SegmentGenerator(Segment arch){
		this.segments = new WeightedRandomizer<Segment>();
		this.arch = arch;
	}
	
	public SegmentGenerator(SegmentGenerator toCopy){
		this.arch = toCopy.arch;
		this.segments = new WeightedRandomizer<Segment>(toCopy.segments);
	}
	
	public SegmentGenerator(JsonObject json){
		String archType = json.get("arch").getAsString();
		arch = Segment.valueOf(archType);
		
		this.segments = new WeightedRandomizer<Segment>();
		JsonArray segmentList = json.get("segments").getAsJsonArray();
		for(JsonElement e : segmentList){
			JsonObject segData = e.getAsJsonObject();
			String segType = segData.get("type").getAsString();
			int weight = segData.get("weight").getAsInt();
			Segment type = Segment.valueOf(segType);
			this.segments.add(new WeightedChoice<Segment>(type, weight));
		}
	}
	
	public void add(Segment toAdd, int weight){
		this.segments.add(new WeightedChoice<Segment>(toAdd, weight));
	}
	
	@Override
	public void genSegment(World world, Random rand, CatacombLevel level, Cardinal dir, Coord pos) {
		
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
