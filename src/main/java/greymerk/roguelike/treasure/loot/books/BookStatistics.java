package greymerk.roguelike.treasure.loot.books;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import greymerk.roguelike.treasure.loot.BookBase;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.block.Block;

public class BookStatistics extends BookBase{

	public BookStatistics(IWorldEditor editor){
		super("greymerk", "Statistics");
		
		for(String page : getPages(editor)){
			this.addPage(page);
		}		
	}
	
	private List<String> getPages(IWorldEditor editor){
		List<String> pages = new ArrayList<String>();
		Map<Block, Integer> stats = editor.getStats();
		
		int counter = 0;
		String page = "";
		for(Block type : stats.keySet()){
			int count = stats.get(type);
			String name = StringUtils.abbreviate(type.getLocalizedName(), 16);
			String line = name + " : " + count + "\n";
			page += line;
			++counter;
			if(counter == 12){
				counter = 0;
				pages.add(page);
				page = "";
			}
		}
		pages.add(page);
		return pages;
	}
}
