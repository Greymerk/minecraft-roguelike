package greymerk.roguelike.treasure.loot.books;

import greymerk.roguelike.Roguelike;
import greymerk.roguelike.treasure.loot.BookBase;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.init.Blocks;

public class BookStarter extends BookBase{

	public BookStarter(IWorldEditor editor){
		super("greymerk", "statistics");
		
		this.addPage("~Architect's Resource Notes~\n\n"
			+ "StoneBrick: " + editor.getStat(Blocks.STONEBRICK) + "\n"
			+ "Cobblestone: " + editor.getStat(Blocks.COBBLESTONE) + "\n"
			+ "Logs: " + (editor.getStat(Blocks.LOG) + editor.getStat(Blocks.LOG2)) + "\n"
			+ "Iron Bars: " + editor.getStat(Blocks.IRON_BARS) + "\n"
			+ "Chests: " + (editor.getStat(Blocks.CHEST) + editor.getStat(Blocks.TRAPPED_CHEST)) + "\n"
			+ "Mob Spawners: " + editor.getStat(Blocks.MOB_SPAWNER) + "\n"
			+ "TNT: " + editor.getStat(Blocks.TNT) + "\n"
			+ "\n-Greymerk");
		this.addPage("Roguelike Dungeons v" + Roguelike.version + "\n"
			+ Roguelike.date + "\n\n"
			+ "Credits\n\n"
			+ "Author: Greymerk\n\n"
			+ "Bits: Drainedsoul\n\n"
			+ "Ideas: Eniko @enichan");
	}
}
