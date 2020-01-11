package greymerk.roguelike.worldgen.spawners;

import com.google.gson.JsonObject;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class SpawnPotential {

  String name;
  int weight;
  boolean equip;
  NBTTagCompound nbt;


  public SpawnPotential(String name) {
    this(name, 1);
  }

  public SpawnPotential(String name, int weight) {
    this(name, true, weight, null);
  }

  public SpawnPotential(String name, boolean equip, int weight) {
    this(name, equip, weight, null);
  }

  public SpawnPotential(String name, boolean equip, int weight, NBTTagCompound nbt) {
    this.name = name;
    this.equip = equip;
    this.weight = weight;
    this.nbt = nbt;
  }

  public SpawnPotential(JsonObject entry) throws Exception {
    this.weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;
    if (!entry.has("name")) {
      throw new Exception("Spawn potential missing name");
    }

    this.name = entry.get("name").getAsString();
    this.equip = entry.has("equip") && entry.get("equip").getAsBoolean();

    if (entry.has("nbt")) {
      String metadata = entry.get("nbt").getAsString();
      this.nbt = JsonToNBT.getTagFromJson(metadata);
    }
  }

  public NBTTagCompound get(int level) {
    NBTTagCompound nbt = this.nbt == null ? new NBTTagCompound() : this.nbt.copy();
    return getPotential(getRoguelike(level, this.name, nbt));
  }

  public NBTTagList get(Random rand, int level) {

    NBTTagList potentials = new NBTTagList();

    if (name.equals(Spawner.getName(Spawner.ZOMBIE))) {
      for (int i = 0; i < 24; ++i) {
        NBTTagCompound mob = new NBTTagCompound();
        mob = getRoguelike(level, this.name, mob);

        Equipment tool;
        switch (rand.nextInt(3)) {
          case 0:
            tool = Equipment.SHOVEL;
            break;
          case 1:
            tool = Equipment.AXE;
            break;
          case 2:
            tool = Equipment.PICK;
            break;
          default:
            tool = Equipment.PICK;
            break;
        }

        mob = equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
        mob = equipArmour(mob, rand, level);

        potentials.appendTag(getPotential(mob));
      }

      return potentials;
    }

    if (name.equals(Spawner.getName(Spawner.SKELETON))) {
      for (int i = 0; i < 12; ++i) {
        NBTTagCompound mob = new NBTTagCompound();
        mob = getRoguelike(level, this.name, mob);
        mob = equipHands(mob, "minecraft:bow", null);
        mob = equipArmour(mob, rand, level);
        potentials.appendTag(getPotential(mob));
      }

      return potentials;
    }

    potentials.appendTag(getPotential(getRoguelike(level, this.name, new NBTTagCompound())));
    return potentials;
  }

  private NBTTagCompound getPotential(NBTTagCompound mob) {
    NBTTagCompound potential = new NBTTagCompound();
    potential.setTag("Entity", mob);
    potential.setInteger("Weight", this.weight);
    return potential;
  }

  private NBTTagCompound equipHands(NBTTagCompound mob, String weapon, String offhand) {
    NBTTagList hands = new NBTTagList();
    hands.appendTag(getItem(weapon));
    hands.appendTag(getItem(offhand));
    mob.setTag("HandItems", hands);

    return mob;
  }

  private NBTTagCompound equipArmour(NBTTagCompound mob, Random rand, int level) {

    NBTTagList armour = new NBTTagList();
    armour.appendTag(getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
    armour.appendTag(getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
    armour.appendTag(getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
    armour.appendTag(getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
    mob.setTag("ArmorItems", armour);

    return mob;
  }

  private NBTTagCompound getItem(String itemName) {
    NBTTagCompound item = new NBTTagCompound();
    if (itemName == null) {
      return item;
    }
    item.setString("id", itemName);
    item.setInteger("Count", 1);
    return item;
  }

  private NBTTagCompound getRoguelike(int level, String type, NBTTagCompound tag) {

    tag.setString("id", type);

    if (!(RogueConfig.getBoolean(RogueConfig.ROGUESPAWNERS)
        && this.equip)) {
      return tag;
    }

    NBTTagList activeEffects = new NBTTagList();
    tag.setTag("ActiveEffects", activeEffects);

    NBTTagCompound buff = new NBTTagCompound();
    activeEffects.appendTag(buff);

    buff.setByte("Id", (byte) 4);
    buff.setByte("Amplifier", (byte) level);
    buff.setInteger("Duration", 10);
    buff.setByte("Ambient", (byte) 0);

    return tag;
  }

}
