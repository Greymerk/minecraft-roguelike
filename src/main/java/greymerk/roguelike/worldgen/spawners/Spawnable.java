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
				new MetaBlock(Blocks.MOB_SPAWNER.getDefaultState()),
				true,
				true);
		
        TileEntity tileentity = editor.getTileEntity(pos);

        if (!(tileentity instanceof TileEntityMobSpawner)){
            return;
        }
        	
        TileEntityMobSpawner spawner = (TileEntityMobSpawner)tileentity;
        MobSpawnerBaseLogic spawnerLogic = spawner.getSpawnerBaseLogic();
        
        //spawnerLogic.func_190894_a(new ResourceLocation(this.name));
        
        if(this.meta != null){
            this.meta.setInteger("x", pos.getX());
            this.meta.setInteger("y", pos.getY());
            this.meta.setInteger("z", pos.getZ());
            spawnerLogic.readFromNBT(this.meta);
            spawnerLogic.updateSpawner();
            tileentity.markDirty();
            return;
        }
        
        NBTTagCompound nbt = getRoguelike(level, this.name);
        nbt.setInteger("x", pos.getX());
        nbt.setInteger("y", pos.getY());
        nbt.setInteger("z", pos.getZ());
        
        
        spawnerLogic.readFromNBT(nbt);
        spawnerLogic.updateSpawner();
        tileentity.markDirty();
        
	}
	
	private NBTTagCompound getRoguelike(int level, String type){
		//String strg = "{SpawnData:{id:\"" + type + "\",ActiveEffects:[{Id:4,Duration:10,Amplifier:" + level + ",Ambient:0}]}}";
    	
		NBTTagCompound nbt = new NBTTagCompound();
		
		NBTTagCompound spawnData = new NBTTagCompound();
		nbt.setTag("SpawnData", spawnData);
		
		spawnData.setString("id", type);
		
        if(!(RogueConfig.getBoolean(RogueConfig.ROGUESPAWNERS)
        		&& this.equip)) return nbt;
		
		NBTTagList activeEffects = new NBTTagList();
		spawnData.setTag("ActiveEffects", activeEffects);
		
		NBTTagCompound buff = new NBTTagCompound();
		activeEffects.appendTag(buff);
		
		buff.setByte("Id", (byte) 4);
		buff.setByte("Amplifier", (byte) level);
		buff.setInteger("Duration", 10);
		buff.setByte("Ambient", (byte) 0);

		return nbt;
    }
}
