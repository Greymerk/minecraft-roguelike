package greymerk.roguelike.treasure;

import java.util.Random;

import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestEmpty extends TreasureChest implements ITreasureChest {

	@Override
	public ITreasureChest generate(WorldEditor editor, Random inRand, LootSettings loot, Coord pos, int level, boolean trapped) {
		
		this.rand = inRand;
		
		MetaBlock chestType = new MetaBlock(trapped ? Blocks.trapped_chest : Blocks.chest);
		
		if(!chestType.setBlock(editor, pos)){
			return null;
		}
		
		TileEntityChest chest = (TileEntityChest) editor.getTileEntity(pos);
		this.inventory = new Inventory(rand, chest);
		
		chest = (TileEntityChest) editor.getTileEntity(pos);
		
		fillChest(loot, level);

		return this;

	}

	@Override
	protected void fillChest(LootSettings loot, int level) {
	}
}
