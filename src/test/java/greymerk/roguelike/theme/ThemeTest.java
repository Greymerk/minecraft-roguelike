package greymerk.roguelike.theme;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.blocks.BlockType;
import net.minecraft.init.Bootstrap;

public class ThemeTest {

	@Before
	public void setUp() throws Exception {
		Bootstrap.register();
		RogueConfig.testing = true;
	}

	@Test
	public void noBase() throws Exception {
		JsonObject json = new JsonObject();
		JsonObject primary = new JsonObject();
		json.add("primary", primary);
		
		JsonObject secondary = new JsonObject();
		json.add("secondary", secondary);
		
		JsonObject floor = new JsonObject();
		primary.add("floor", floor);
		floor.addProperty("name", "minecraft:dirt");
		
		JsonObject walls = new JsonObject();
		secondary.add("walls", walls);
		walls.addProperty("name", "minecraft:stone");
		
		
		ITheme t = Theme.create(json);
		assert(t.getPrimary().getFloor().equals(BlockType.get(BlockType.DIRT)));
		
	}
	
	@Test
	public void noSecondary() throws Exception{
		JsonObject json = new JsonObject();
		JsonObject primary = new JsonObject();
		json.add("primary", primary);
		
		JsonObject floor = new JsonObject();
		primary.add("floor", floor);
		floor.addProperty("name", "minecraft:dirt");
		
		ITheme t = Theme.create(json);
		assert(t.getPrimary().getFloor().equals(BlockType.get(BlockType.DIRT)));
	}
}
