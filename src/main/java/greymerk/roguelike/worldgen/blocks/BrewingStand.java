package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public enum BrewingStand {

	INPUT, LEFT, CENTER, RIGHT;
	
	public static MetaBlock get(){
		return new MetaBlock(Blocks.brewing_stand);
	}	
}
