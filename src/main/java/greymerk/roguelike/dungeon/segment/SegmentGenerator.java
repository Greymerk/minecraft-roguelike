package greymerk.roguelike.dungeon.segment;

import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;

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
	public void genSegment(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, Coord pos) {
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			ISegment seg = pickSegment(editor, rand, level, dir, pos);
			if(seg == null) return;
			seg.generate(editor, rand, level, orth, level.getSettings().getTheme(), x, y, z);
		}
		
		if(!level.hasNearbyNode(pos) && rand.nextInt(3) == 0) addSupport(editor, rand, level.getSettings().getTheme(), x, y, z);
		
	}
	
	private ISegment pickSegment(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, Coord pos){
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
	
	private void addSupport(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		if(!editor.isAirBlock(new Coord(x, y - 2, z))) return;
		
		editor.fillDown(rand, new Coord(x, y - 2, z), theme.getPrimaryPillar());
		
		MetaBlock stair = theme.getPrimaryStair();
		WorldEditor.blockOrientation(stair, Cardinal.WEST, true);
		editor.setBlock(new Coord(x - 1, y - 2, z), stair);
		
		WorldEditor.blockOrientation(stair, Cardinal.EAST, true);
		editor.setBlock(new Coord(x + 1, y - 2, z), stair);
		
		WorldEditor.blockOrientation(stair, Cardinal.SOUTH, true);
		editor.setBlock(new Coord(x, y - 2, z + 1), stair);
		
		WorldEditor.blockOrientation(stair, Cardinal.NORTH, true);
		editor.setBlock(new Coord(x, y - 2, z - 1), stair);	
	}
}
