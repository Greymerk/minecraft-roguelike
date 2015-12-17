package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.init.Blocks;

public enum TallPlant {

	SUNFLOWER, LILAC, TALLGRASS, FERN, ROSE, PEONY;
	
	public static void generate(WorldEditor editor, TallPlant type, Coord pos){
		Coord cursor;

		//BlockDoublePlant plant = new BlockDoublePlant();
		//MetaBlock upper = new MetaBlock(plant.getStateFromMeta(getMeta(type, true)));
		//MetaBlock lower = new MetaBlock(plant.getStateFromMeta(getMeta(type, false)));
		
		MetaBlock upper = new MetaBlock(Blocks.double_plant.getStateFromMeta(getMeta(type, true)));
		MetaBlock lower = new MetaBlock(Blocks.double_plant.getStateFromMeta(getMeta(type, false)));
		
		cursor = new Coord(pos);
		lower.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		upper.setBlock(editor, cursor);
	}
	
	public static int getMeta(TallPlant type, boolean top){
		if(top) return 8;
		
		switch(type){
		case SUNFLOWER: return 0;
		case LILAC: return 1;
		case TALLGRASS: return 2;
		case FERN: return 3;
		case ROSE: return 4;
		case PEONY: return 5;
		default: return 0;
		}
		
	}
	
	public static BlockDoublePlant.EnumPlantType getType(TallPlant type){
		switch(type){
		case SUNFLOWER: return BlockDoublePlant.EnumPlantType.SUNFLOWER;
		case LILAC: return BlockDoublePlant.EnumPlantType.SYRINGA;
		case TALLGRASS: return BlockDoublePlant.EnumPlantType.GRASS;
		case FERN: return BlockDoublePlant.EnumPlantType.FERN;
		case ROSE: return BlockDoublePlant.EnumPlantType.ROSE;
		case PEONY: return BlockDoublePlant.EnumPlantType.PAEONIA;
		default: return BlockDoublePlant.EnumPlantType.GRASS; 
		}
	}
	
}
