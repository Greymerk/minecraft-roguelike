package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public enum Slab {

	STONE, STONEBRICK, COBBLE, BRICK, NETHERBRICK, QUARTZ,
	LEGACY_OAK, SANDSTONE, SANDSTONE_RED,
	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK;
	
	public static MetaBlock get(Slab type, boolean upsideDown, boolean full, boolean seamless){
		
		MetaBlock slab = new MetaBlock(getBaseSlab(type, full));
		
		int typeMeta;
		
		switch(type){
		case STONE: typeMeta = 0; break;
		case SANDSTONE: typeMeta = 1; break;
		case LEGACY_OAK: typeMeta = 2; break;
		case COBBLE: typeMeta = 3; break;
		case BRICK: typeMeta = 4; break;
		case STONEBRICK: typeMeta = 5; break;
		case NETHERBRICK: typeMeta = 6; break;
		case QUARTZ: typeMeta = 7; break;
		case SANDSTONE_RED: typeMeta = 1; break;
		case OAK: typeMeta = 0; break;
		case SPRUCE: typeMeta = 1; break;
		case BIRCH: typeMeta = 2; break;
		case JUNGLE: typeMeta = 3; break;
		case ACACIA: typeMeta = 4; break;
		case DARKOAK: typeMeta = 5; break;
		default: typeMeta = 0;
		}
		
		if(!full && upsideDown){
			slab.setMeta(8 + typeMeta);
		}
		
		if(full && seamless){
			slab.setMeta(8 + typeMeta);
		}

		return slab;
		
	}
	
	public static MetaBlock get(Slab type){
		return get(type, false, false, false);
	}
	
	public static Block getBaseSlab(Slab type, boolean full){
		switch(type){
		case STONE:
		case SANDSTONE:
		case LEGACY_OAK:
		case COBBLE:
		case BRICK:
		case STONEBRICK:
		case NETHERBRICK:
		case QUARTZ:
		case SANDSTONE_RED:
			return full ? Blocks.double_stone_slab : Blocks.stone_slab;
		case OAK:
		case SPRUCE:
		case BIRCH:
		case JUNGLE:
		case ACACIA:
		case DARKOAK:
			return full ? Blocks.double_wooden_slab : Blocks.wooden_slab;
		default: return Blocks.stone_slab;
		}
	}
	
}
