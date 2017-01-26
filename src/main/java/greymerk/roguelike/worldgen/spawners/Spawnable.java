package greymerk.roguelike.worldgen.spawners;

import java.util.Random;

import com.google.gson.JsonObject;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.JsonNBT;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;

public class Spawnable {

	private boolean equip;
	private String name;
	private NBTTagCompound meta;
	
	public Spawnable(Spawner type){
		this(Spawner.getName(type));
	}
	
	public Spawnable(String name){
		this(true, name, null);
	}
	
	public Spawnable(String name, NBTTagCompound meta){
		this(true, name, meta);
	}
	
	public Spawnable(boolean equip, String name, NBTTagCompound meta){
		this.equip = equip;
		this.name = name;
		this.meta = meta;
	}
	
	public Spawnable(JsonObject data){
		name = data.get("name").getAsString();
		equip = data.has("equip") ? data.get("equip").getAsBoolean() : true;
		
		if(!data.has("meta")) return;
		
		JsonObject metadata = data.get("meta").getAsJsonObject();
		this.meta = JsonNBT.jsonToCompound(metadata);
		
		if(!this.meta.hasKey("id")){
			this.meta.setString("id", this.name);
		}
	}
		
	public void generate(IWorldEditor editor, Random rand, Coord cursor, int level){
		
		Coord pos = new Coord(cursor);
		
		editor.setBlock(
				pos,
				new MetaBlock(Blocks.MOB_SPAWNER.getDefaultState()),
				true,
				true);
		
        TileEntity tileentity = editor.getTileEntity(pos);

        if (!(tileentity instanceof TileEntityMobSpawner)){
            return;
        }
        	
        TileEntityMobSpawner spawner = (TileEntityMobSpawner)tileentity;
        MobSpawnerBaseLogic spawnerLogic = spawner.getSpawnerBaseLogic();
        
        NBTTagCompound nbt = new NBTTagCompound();
        
        nbt.setInteger("x", pos.getX());
        nbt.setInteger("y", pos.getY());
        nbt.setInteger("z", pos.getZ());
        
        if(this.meta == null){
        	NBTTagList potentials = getSpawnPotentials(rand, level, this.name);
        	NBTTagCompound spawn = potentials.getCompoundTagAt(0);
        	nbt.setTag("SpawnPotentials", potentials);
        	nbt.setTag("SpawnData", spawn.getTag("Entity"));
        } else {
        	nbt.setTag("SpawnData", this.meta);
        }
        
        spawnerLogic.readFromNBT(nbt);
        spawnerLogic.updateSpawner();
        tileentity.markDirty();
        
	}
	
	private NBTTagList getSpawnPotentials(Random rand, int level, String name){
		
		NBTTagList potentials = new NBTTagList();
		
		if(name.equals(Spawner.getName(Spawner.ZOMBIE))){
			for(int i = 0; i < 24; ++i){
				NBTTagCompound mob = new NBTTagCompound();
				mob = getRoguelike(level, this.name, mob);
				
				Equipment tool;
				switch(rand.nextInt(3)){
				case 0: tool = Equipment.SHOVEL; break;
				case 1: tool = Equipment.AXE; break;
				case 2: tool = Equipment.PICK; break;
				default: tool = Equipment.PICK; break;
				}
				
				mob = equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, level)), "minecraft:shield");
				mob = equipArmour(mob, rand, level);
				
				potentials.appendTag(getPotential(mob));
			}
			
			return potentials;
		}
		
		if(name.equals(Spawner.getName(Spawner.SKELETON))){
			for(int i = 0; i < 12; ++i){
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

	private NBTTagCompound getPotential(NBTTagCompound mob){
		NBTTagCompound potential = new NBTTagCompound();
		potential.setTag("Entity", mob);
		potential.setInteger("Weight", 1);
		return potential;
	}

	private NBTTagCompound equipHands(NBTTagCompound mob, String weapon, String offhand){
		NBTTagList hands = new NBTTagList();
		hands.appendTag(getItem(weapon));
		hands.appendTag(getItem(offhand));
		mob.setTag("HandItems", hands);
		
		return mob;
	}
	
	private NBTTagCompound equipArmour(NBTTagCompound mob, Random rand, int level){
		
		NBTTagList armour = new NBTTagList();
		armour.appendTag(getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.appendTag(getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.appendTag(getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.appendTag(getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		mob.setTag("ArmorItems", armour);

		return mob;
	}
	
	private NBTTagCompound getItem(String name){
		NBTTagCompound item = new NBTTagCompound();
		if(name == null) return item;
		item.setString("id", name);
		item.setInteger("Count", 1);
		return item;
	}
	
	private NBTTagCompound getRoguelike(int level, String type, NBTTagCompound tag){
		//String strg = "{SpawnData:{id:\"" + type + "\",ActiveEffects:[{Id:4,Duration:10,Amplifier:" + level + ",Ambient:0}]}}";
    	
		tag.setString("id", type);
		
        if(!(RogueConfig.getBoolean(RogueConfig.ROGUESPAWNERS)
        		&& this.equip)) return tag;
		
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
