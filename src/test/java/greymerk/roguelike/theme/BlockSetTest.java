package greymerk.roguelike.theme;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;

public class BlockSetTest {

	@Before
	public void setUp() throws Exception {
		Bootstrap.register();
		RogueConfig.testing = true;
	}

	@Test
	public void jsonNoBase() throws Exception {
		
		JsonObject json = new JsonObject();
		JsonObject floor = new JsonObject();
		json.add("floor", floor);
		
		floor.addProperty("name", "minecraft:dirt");
		
		BlockSet test = new BlockSet(json, null);
		
		assert(test.getFloor().equals(new MetaBlock(Blocks.DIRT)));	
	}

}
