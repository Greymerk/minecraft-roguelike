package com.greymerk.roguelike.editor.blocks.spawners;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.Equipment;
import com.greymerk.roguelike.treasure.loot.Quality;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;

public class SpawnPotential {
	
	Spawner type;
	int weight;
	boolean equip;
	CompoundTag nbt;
	
	
	public SpawnPotential(Spawner type){
		this(type, 1);
	}
	
	public SpawnPotential(Spawner type, int weight){
		this(type, true, weight, null);
	}
	
	public SpawnPotential(Spawner type, boolean equip, int weight){
		this(type, equip, weight, null);
	}
	
	public SpawnPotential(Spawner type, boolean equip, int weight, CompoundTag nbt){
		this.type = type;
		this.equip = equip;
		this.weight = weight;
		this.nbt = nbt;
	}
	
	public CompoundTag get(Difficulty diff){
		CompoundTag nbt = this.nbt == null ? new CompoundTag() : this.nbt.copy();
		return getPotential(getRoguelike(diff, type, nbt));
	}
	
	public ListTag get(RandomSource rand, Difficulty diff){
		
		ListTag potentials = new ListTag();
		
		if(this.type == Spawner.ZOMBIE){
			for(int i = 0; i < 24; ++i){
				CompoundTag mob = new CompoundTag();
				mob = getRoguelike(diff, this.type, mob);
				
				Equipment tool = switch(rand.nextInt(3)) {
				case 0 -> Equipment.SHOVEL;
				case 1 -> Equipment.AXE;
				case 2 -> Equipment.PICK;
				default -> Equipment.PICK;
				};
				
				mob = equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, diff)), null);
				mob = equipArmour(mob, rand, diff);
				
				potentials.add(getPotential(mob));
			}
			
			return potentials;
		}
		
		if(this.type == Spawner.SKELETON){
			for(int i = 0; i < 12; ++i){
				CompoundTag mob = new CompoundTag();
				mob = getRoguelike(diff, this.type, mob);
				mob = equipHands(mob, "minecraft:bow", null);
				mob = equipArmour(mob, rand, diff);
				potentials.add(getPotential(mob));
			}
			
			return potentials;
		}

		potentials.add(getPotential(getRoguelike(diff, this.type, new CompoundTag())));
		return potentials;
	}
	
	public CompoundTag getSpawnData(RandomSource rand, Difficulty diff) {
		return getSpawnData(getRoguelike(diff, this.type, new CompoundTag()));
	}
	
	private CompoundTag getPotential(CompoundTag mob){
		CompoundTag potential = new CompoundTag();
		potential.put("data", getSpawnData(mob));
		potential.putInt("weight", this.weight);
		return potential;
	}
	
	private CompoundTag getSpawnData(CompoundTag mob) {
		CompoundTag data = new CompoundTag();
		data.put("entity", mob);
		return data;
	}
	
	private CompoundTag equipHands(CompoundTag mob, String weapon, String offhand){
		ListTag hands = new ListTag();
		hands.add(getItem(weapon));
		hands.add(getItem(offhand));
		mob.put("HandItems", hands);
		
		return mob;
	}
	
	private CompoundTag equipArmour(CompoundTag mob, RandomSource rand, Difficulty diff){
		
		ListTag armour = new ListTag();
		armour.add(getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, diff))));
		armour.add(getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, diff))));
		armour.add(getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, diff))));
		armour.add(getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, diff))));
		mob.put("ArmorItems", armour);

		return mob;
	}
	
	private CompoundTag getItem(String itemName){
		CompoundTag item = new CompoundTag();
		if(itemName == null) return item;
		item.putString("id", itemName);
		item.putInt("Count", 1);
		return item;
	}
	
	private CompoundTag getRoguelike(Difficulty diff, Spawner type, CompoundTag tag){
   	
		tag.putString("id", Spawner.getName(type));
		
		ListTag activeEffects = new ListTag();
		tag.put("active_effects", activeEffects);
		
		CompoundTag buff = new CompoundTag();
		activeEffects.add(buff);
		
		buff.putString("id", "minecraft:mining_fatigue");
		buff.putByte("amplifier", (byte) diff.value);
		buff.putInt("duration", 10);
		buff.putByte("ambient", (byte) 0);

		return tag;
    }

}
