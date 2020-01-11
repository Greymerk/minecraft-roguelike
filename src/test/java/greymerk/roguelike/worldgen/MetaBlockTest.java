package greymerk.roguelike.worldgen;

import com.google.gson.JsonObject;

import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MetaBlockTest {

  @Before
  public void setup() {
    Bootstrap.register();
  }

  @Test
  public void jsonArgs() throws Exception {

    MetaBlock dirt = new MetaBlock(Blocks.DIRT);
    MetaBlock stone = new MetaBlock(Blocks.STONE);

    JsonObject json = new JsonObject();
    json.addProperty("name", "minecraft:dirt");
    MetaBlock test = new MetaBlock(json);
    assert (test.equals(dirt));

    MetaBlock podzol = new MetaBlock(Blocks.DIRT);
    podzol.withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);

    json = new JsonObject();
    json.addProperty("name", "minecraft:dirt");
    json.addProperty("meta", 2);

    test = new MetaBlock(json);

    assertFalse(test.equals(stone));
    assertFalse(test.equals(dirt));
    assertEquals(test, podzol);
  }


  @Test
  public void testEquals() {
    MetaBlock dirt = new MetaBlock(Blocks.DIRT);
    MetaBlock dirt2 = new MetaBlock(Blocks.DIRT);
    assert (dirt.equals(dirt2));
  }
}
