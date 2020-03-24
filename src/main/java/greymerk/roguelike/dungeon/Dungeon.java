package greymerk.roguelike.dungeon;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.tasks.DungeonTaskRegistry;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.VanillaStructure;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.sin;
import static java.util.Optional.ofNullable;

public class Dungeon implements IDungeon {
  public static final int VERTICAL_SPACING = 10;
  public static final int TOPLEVEL = 50;

  private static final String SETTINGS_DIRECTORY = RogueConfig.configDirName + "/settings";
  public static SettingsResolver settingsResolver;

  static {
    try {
      RogueConfig.reload(false);
      initResolver();
    } catch (Exception e) {
      // do nothing
    }
  }

  private Coord origin;
  private List<IDungeonLevel> levels;
  private IWorldEditor editor;

  public Dungeon(IWorldEditor editor) {
    this.editor = editor;
    levels = new ArrayList<>();
  }

  public static void initResolver() throws Exception {
    File settingsDir = new File(SETTINGS_DIRECTORY);

    if (settingsDir.exists() && !settingsDir.isDirectory()) {
      throw new Exception("Settings directory is a file");
    }

    if (!settingsDir.exists()) {
      settingsDir.mkdir();
    }

    File[] settingsFiles = settingsDir.listFiles();
    Arrays.sort(settingsFiles);

    SettingsContainer settings = new SettingsContainer();
    settingsResolver = new SettingsResolver(settings);

    Map<String, String> files = new HashMap<>();

    for (File file : settingsFiles) {

      if (!FilenameUtils.getExtension(file.getName()).equals("json")) {
        continue;
      }

      try {
        String content = Files.toString(file, Charsets.UTF_8);
        files.put(file.getName(), content);
      } catch (IOException e) {
        throw new Exception("Error reading file : " + file.getName());
      }
    }

    settings.parseCustomSettings(files);
  }

  public static boolean canSpawnInChunk(int chunkX, int chunkZ, IWorldEditor editor) {

    if (!RogueConfig.getBoolean(RogueConfig.DONATURALSPAWN)) {
      return false;
    }

    int dim = editor.getInfo(new Coord(chunkX * 16, 0, chunkZ * 16)).getDimension();
    List<Integer> whiteList = new ArrayList<>(RogueConfig.getIntList(RogueConfig.DIMENSIONWL));
    List<Integer> blackList = new ArrayList<>(RogueConfig.getIntList(RogueConfig.DIMENSIONBL));
    if (!SpawnCriteria.isValidDimension(dim, whiteList, blackList)) {
      return false;
    }

    if (!isVillageChunk(editor, chunkX, chunkZ)) {
      return false;
    }

    double spawnChance = RogueConfig.getDouble(RogueConfig.SPAWNCHANCE);
    Random rand = new Random(Objects.hash(chunkX, chunkZ, 31));

    return rand.nextFloat() < spawnChance;

  }

  public static boolean isVillageChunk(IWorldEditor editor, int chunkX, int chunkZ) {
    int frequency = RogueConfig.getInt(RogueConfig.SPAWNFREQUENCY);
    int min = 8 * frequency / 10;
    int max = 32 * frequency / 10;

    min = max(min, 2);
    max = max(max, 8);

    int tempX = chunkX < 0 ? chunkX - (max - 1) : chunkX;
    int tempZ = chunkZ < 0 ? chunkZ - (max - 1) : chunkZ;

    int m = tempX / max;
    int n = tempZ / max;

    Random r = editor.getSeededRandom(m, n, 10387312);

    m *= max;
    n *= max;

    m += r.nextInt(max - min);
    n += r.nextInt(max - min);

    return chunkX == m && chunkZ == n;
  }

  public static int getLevel(int y) {

    if (y < 15) {
      return 4;
    }
    if (y < 25) {
      return 3;
    }
    if (y < 35) {
      return 2;
    }
    if (y < 45) {
      return 1;
    }
    return 0;
  }

  public static Coord getNearbyCoord(Random rand, int x, int z, int min, int max) {
    int distance = min + rand.nextInt(max - min);
    double angle = rand.nextDouble() * 2 * PI;
    int xOffset = (int) (cos(angle) * distance);
    int zOffset = (int) (sin(angle) * distance);
    return new Coord(x + xOffset, 0, z + zOffset);
  }

  public static Random getRandom(IWorldEditor editor, Coord pos) {
    return new Random(Objects.hash(editor.getSeed(), pos));
  }

  public void generateNear(Random rand, int x, int z) {
    if (Dungeon.settingsResolver == null) {
      return;
    }
    selectLocation(rand, x, z)
        .ifPresent(coord -> generateDungeon(rand, coord));
  }

  private Optional<Coord> selectLocation(Random rand, int x, int z) {
    return IntStream.range(0, 50)
        .mapToObj(i -> getNearbyCoord(rand, x, z, 40, 100))
        .filter(this::canGenerateDungeonHere)
        .findFirst();
  }

  private void generateDungeon(Random rand, Coord coord) {
    try {
      DungeonSettings dungeonSettings = Dungeon.settingsResolver.chooseDungeonSettingsToGenerate(editor, rand, coord);
      if (dungeonSettings != null) {
        generate(dungeonSettings, coord);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void printDungeonName(DungeonSettings dungeonSettings) {
    Optional<SettingIdentifier> id = ofNullable(dungeonSettings.getId());
    String string = id.isPresent() ? id.toString() : dungeonSettings.toString();
    Minecraft.getMinecraft().player.sendChatMessage(string);
  }

  public void generate(DungeonSettings dungeonSettings, Coord pos) {
    origin = new Coord(pos.getX(), Dungeon.TOPLEVEL, pos.getZ());
    DungeonGenerator.generate(editor, this, dungeonSettings, DungeonTaskRegistry.getTaskRegistry());
  }

  public void spawnInChunk(Random rand, int chunkX, int chunkZ) {
    if (Dungeon.canSpawnInChunk(chunkX, chunkZ, editor)) {
      int x = chunkX * 16 + 4;
      int z = chunkZ * 16 + 4;

      generateNear(rand, x, z);
    }
  }

  public boolean canGenerateDungeonHere(Coord column) {

    Biome biome = editor.getInfo(column).getBiome();

    Type[] invalidBiomes = new Type[]{
        BiomeDictionary.Type.RIVER,
        BiomeDictionary.Type.BEACH,
        BiomeDictionary.Type.MUSHROOM,
        BiomeDictionary.Type.OCEAN
    };

    for (Type type : invalidBiomes) {
      if (BiomeDictionary.hasType(biome, type)) {
        return false;
      }
    }

    Coord stronghold = editor.findNearestStructure(VanillaStructure.STRONGHOLD, column);
    if (stronghold != null) {
      double strongholdDistance = column.distance(stronghold);
      if (strongholdDistance < 300) {
        return false;
      }
    }

    int upperLimit = RogueConfig.getInt(RogueConfig.UPPERLIMIT);
    int lowerLimit = RogueConfig.getInt(RogueConfig.LOWERLIMIT);

    Coord cursor = new Coord(column.getX(), upperLimit, column.getZ());

    if (!editor.isAirBlock(cursor)) {
      return false;
    }

    while (!editor.validGroundBlock(cursor)) {
      cursor.add(Cardinal.DOWN);
      if (cursor.getY() < lowerLimit) {
        return false;
      }
      if (editor.getBlock(cursor).getMaterial() == Material.WATER) {
        return false;
      }
    }

    Coord start;
    Coord end;
    start = new Coord(cursor);
    end = new Coord(cursor);
    start.add(new Coord(-4, 4, -4));
    end.add(new Coord(4, 4, 4));

    for (Coord c : new RectSolid(start, end)) {
      if (editor.validGroundBlock(c)) {
        return false;
      }
    }

    start = new Coord(cursor);
    end = new Coord(cursor);
    start.add(new Coord(-4, -3, -4));
    end.add(new Coord(4, -3, 4));
    int airCount = 0;
    for (Coord c : new RectSolid(start, end)) {
      if (!editor.validGroundBlock(c)) {
        airCount++;
      }
      if (airCount > 8) {
        return false;
      }
    }

    return true;
  }

  @Override
  public List<ITreasureChest> getChests() {
    return editor.getTreasure().getChests();
  }

  @Override
  public Coord getPosition() {
    return new Coord(origin);
  }

  @Override
  public List<IDungeonLevel> getLevels() {
    return levels;
  }
}
