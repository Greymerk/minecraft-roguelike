package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class TreasureChestEmpty extends TreasureChestBase implements ITreasureChest {

	@Override
	public ITreasureChest generate(World inWorld, Random inRand, LootSettings loot, Coord pos, int level, boolean trapped) {
		
		world = inWorld;
		rand = inRand;

		MetaBlock chestType = new MetaBlock(trapped ? Blocks.trapped_chest : Blocks.chest);
		
		
		if(!chestType.setBlock(inWorld, pos)){
			return null;
		}
		
		chest = (TileEntityChest) WorldGenPrimitive.getTileEntity(world, pos);
		
		return this;

	}

	@Override
	protected void fillChest(TileEntityChest chest, LootSettings loot, int level) {
	}
}
