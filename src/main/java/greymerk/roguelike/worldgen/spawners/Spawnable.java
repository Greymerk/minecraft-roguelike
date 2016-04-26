package greymerk.roguelike.worldgen.spawners;

import java.util.Random;

import com.google.gson.JsonObject;

import greymerk.roguelike.config.RogueConfig;
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
import net.minecraft.util.WeightedSpawnerEntity;

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
		if(data.has("meta")){
			JsonObject metadata = data.get("meta").getAsJsonObject();
			this.meta = JsonNBT.jsonToCompound(metadata);
		}
	}
		
	public void generate(IWorldEditor editor, Random rand, Coord cursor, int level){
		
		Coord pos = new Coord(cursor);
		
		editor.setBlock(
				pos,
				new MetaBlock(Blocks.mob_spawner.getDefaultState()),
				true,
				true);
		
        TileEntity tileentity = editor.getTileEntity(pos);

        if (!(tileentity instanceof TileEntityMobSpawner)){
            System.out.println("Not a mob spawner.");
            return;
        }
        	
        TileEntityMobSpawner spawner = (TileEntityMobSpawner)tileentity;
        MobSpawnerBaseLogic spawnerLogic = spawner.getSpawnerBaseLogic();
        
        spawnerLogic.setEntityName(this.name);
        
        if(this.meta != null){
        	setMeta(spawnerLogic, this.meta);
        	return;
        }
        
        if(!(RogueConfig.getBoolean(RogueConfig.ROGUESPAWNERS))) return;
        
        if(!this.equip) return;
        	
        NBTTagCompound nbt = getRoguelike(level, this.name);

        setMeta(spawnerLogic, nbt);
        
	}
	
	private static void setMeta(MobSpawnerBaseLogic logic, NBTTagCompound nbt){
    	WeightedSpawnerEntity randomEntity = new WeightedSpawnerEntity(1, nbt);
    	logic.func_184993_a(randomEntity);
    	logic.updateSpawner();
	}
	
	private NBTTagCompound getRoguelike(int level, String type){
    	NBTTagCompound nbt = new NBTTagCompound();
    	nbt.setString("id", type);
   	
    	NBTTagList activeEffects = new NBTTagList();
    	nbt.setTag("ActiveEffects", activeEffects);
    	
    	NBTTagCompound buff = new NBTTagCompound();
    	activeEffects.appendTag(buff);
    	
    	buff.setByte("Id", (byte) 4);
    	buff.setByte("Amplifier", (byte) level);
    	buff.setInteger("Duration", 10);
    	buff.setByte("Ambient", (byte) 0);
    	
    	return nbt;
    }
}
