package greymerk.roguelike.dungeon.settings.builtin;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.base.SettingsBase;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.filter.Filter;

import static com.google.common.collect.Lists.newArrayList;
import static greymerk.roguelike.dungeon.towers.Tower.WITCH;
import static greymerk.roguelike.theme.Theme.DARKHALL;
import static greymerk.roguelike.theme.Theme.DARKOAK;
import static greymerk.roguelike.theme.Theme.MOSSY;
import static greymerk.roguelike.theme.Theme.MUDDY;
import static greymerk.roguelike.theme.Theme.NETHER;
import static net.minecraftforge.common.BiomeDictionary.Type.SWAMP;

public class SettingsSwampTheme extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "swamp");

  public SettingsSwampTheme() {
    super(ID);
    getInherit().add(SettingsBase.ID);
    getCriteria().setBiomeTypes(newArrayList(SWAMP));
    setTowerSettings(new TowerSettings(WITCH, DARKOAK));

    Theme[] themes = {DARKHALL, DARKHALL, MUDDY, MOSSY, NETHER};

    WeightedRandomizer<ItemStack> brewing = new WeightedRandomizer<>();
    brewing.add(new WeightedRandomLoot(Items.GLASS_BOTTLE, 0, 1, 3, 3));
    brewing.add(new WeightedRandomLoot(Items.MAGMA_CREAM, 0, 1, 2, 1));
    brewing.add(new WeightedRandomLoot(Items.SPECKLED_MELON, 0, 1, 3, 1));
    brewing.add(new WeightedRandomLoot(Items.BLAZE_POWDER, 0, 1, 3, 1));
    brewing.add(new WeightedRandomLoot(Items.SUGAR, 0, 1, 3, 1));
    setLootRules(new LootRuleManager());
    for (int i = 0; i < 5; ++i) {
      getLootRules().add(null, brewing, i, true, 2);
      getLootRules().add(null, new WeightedRandomLoot(Items.SLIME_BALL, 0, 1, 1 + i, 1), i, false, 4 + i * 3);
    }
    for (int i = 0; i < 5; ++i) {

      LevelSettings level = new LevelSettings();
      level.setTheme(themes[i].getThemeBase());

      if (i == 0) {

        SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
        segments.add(Segment.DOOR, 8);
        segments.add(Segment.LAMP, 2);
        segments.add(Segment.FLOWERS, 1);
        segments.add(Segment.MUSHROOM, 2);
        level.setSegments(segments);

        DungeonFactory factory = new DungeonFactory();
        factory.addSingle(DungeonRoom.CAKE);
        factory.addSingle(DungeonRoom.DARKHALL);
        factory.addRandom(DungeonRoom.BRICK, 10);
        factory.addRandom(DungeonRoom.CORNER, 3);
        level.setRooms(factory);
        level.addFilter(Filter.MUD);
      }

      if (i == 1) {

        SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
        segments.add(Segment.DOOR, 8);
        segments.add(Segment.SHELF, 4);
        segments.add(Segment.INSET, 4);
        segments.add(Segment.MUSHROOM, 3);
        level.setSegments(segments);

        DungeonFactory factory = new DungeonFactory();
        factory.addSingle(DungeonRoom.CAKE);
        factory.addSingle(DungeonRoom.LAB);
        factory.addSingle(DungeonRoom.SPIDER);
        factory.addSingle(DungeonRoom.PIT);
        factory.addSingle(DungeonRoom.PRISON);
        factory.addRandom(DungeonRoom.BRICK, 10);
        factory.addRandom(DungeonRoom.CORNER, 3);
        level.setRooms(factory);
        level.addFilter(Filter.MUD);
      }

      getLevels().put(i, level);
    }

    getLevels().get(2).addFilter(Filter.VINE);
    getLevels().get(3).addFilter(Filter.VINE);
  }
}