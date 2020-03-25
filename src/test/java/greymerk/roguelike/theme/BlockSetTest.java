package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.MetaBlock;

public class BlockSetTest {

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;
  }

  @Test
  public void jsonNoBase() throws Exception {

    JsonObject json = new JsonObject();
    JsonObject floor = new JsonObject();
    json.add("floor", floor);

    floor.addProperty("name", "minecraft:dirt");

    BlockSet test = BlockSetParser.parseBlockSet(json, Optional.empty());

    assert (test.getFloor().equals(new MetaBlock(Blocks.DIRT)));
  }

}
