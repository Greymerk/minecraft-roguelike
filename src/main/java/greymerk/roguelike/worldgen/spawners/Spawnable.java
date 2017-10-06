package greymerk.roguelike.worldgen.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

	private Spawner type;
	private List<SpawnPotential> potentials;
		
	public Spawnable(Spawner type){
		this.potentials = new ArrayList<SpawnPotential>();
		this.type = type;
	}
	
	public Spawnable(JsonObject data){
		this.potentials = new ArrayList<SpawnPotential>();
		
		JsonArray arr = data.get("spawns").getAsJsonArray();
		for(JsonElement e : arr){
			JsonObject spawn = e.getAsJsonObject();
			String name = spawn.get("name").getAsString();
			boolean equip = spawn.has("equip") ? spawn.get("equip").getAsBoolean() : true;
			int weight = spawn.has("weight") ? spawn.get("weight").getAsInt() : 1;
			if(!data.has("meta")){
				SpawnPotential toAdd = new SpawnPotential(name, equip, weight);
				this.potentials.add(toAdd);
			}
			
			JsonObject metadata = data.get("meta").getAsJsonObject();
			NBTTagCompound meta = JsonNBT.jsonToCompound(metadata);
			SpawnPotential toAdd = new SpawnPotential(name, equip, weight, meta);
			this.potentials.add(toAdd);
			
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

        nbt.setTag("SpawnPotentials", getSpawnPotentials(rand, level));
        
        spawnerLogic.readFromNBT(nbt);
        spawnerLogic.updateSpawner();
        tileentity.markDirty();
        
	}
	
	private NBTTagList getSpawnPotentials(Random rand, int level){
		
		if(this.type != null){
			SpawnPotential potential = new SpawnPotential(Spawner.getName(type));
			return potential.get(rand, level);
		}
		
		NBTTagList potentials = new NBTTagList();
		
		for(SpawnPotential potential : this.potentials){
			NBTTagCompound nbt = potential.get(level);
			potentials.appendTag(nbt);
		}
		
		return potentials;
	}

	

	





	

}
