package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import net.minecraft.init.Bootstrap;

import org.junit.Before;
import org.junit.Test;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class ThemeTest {

  @Before
  public void setUp() {
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
    assert (t.getPrimary().getFloor().equals(BlockType.get(BlockType.DIRT)));

  }

  @Test
  public void noSecondary() throws Exception {
    JsonObject json = new JsonObject();
    JsonObject primary = new JsonObject();
    json.add("primary", primary);

    JsonObject floor = new JsonObject();
    primary.add("floor", floor);
    floor.addProperty("name", "minecraft:dirt");

    ITheme t = Theme.create(json);
    assert (t.getPrimary().getFloor().equals(BlockType.get(BlockType.DIRT)));
  }

  @Test
  public void noPrimary() throws Exception {
    JsonObject json = new JsonObject();
    JsonObject secondary = new JsonObject();
    json.add("secondary", secondary);

    JsonObject floor = new JsonObject();
    secondary.add("floor", floor);
    floor.addProperty("name", "minecraft:dirt");

    ITheme t = Theme.create(json);
    assert (t.getSecondary().getFloor().equals(BlockType.get(BlockType.DIRT)));
  }

  @Test
  public void merge() {

    IBlockSet bs = new BlockSet(BlockType.get(BlockType.DIRT), null, null);

    ITheme base = new ThemeBase(bs, null);
    ITheme other = new ThemeBase(null, bs);

    @SuppressWarnings("unused")
    ITheme merge = Theme.create(null, null);
    merge = Theme.create(base, null);
    merge = Theme.create(null, other);
    merge = Theme.create(base, other);
  }
}
