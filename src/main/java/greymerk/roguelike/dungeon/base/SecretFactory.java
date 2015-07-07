package greymerk.roguelike.dungeon.base;

import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SecretFactory implements ISecretRoom{

	private int count;
	private List<ISecretRoom> secrets;

	
	public SecretFactory(){
		count = 0;
		secrets = new ArrayList<ISecretRoom>();
	}
	
	public SecretFactory(SecretFactory toCopy){
		secrets = new ArrayList<ISecretRoom>();
		this.count = toCopy.count;
		
		for(ISecretRoom room : toCopy.secrets){
			
			ISecretRoom toAdd;
			
			if(room instanceof SecretFactory){
				toAdd = new SecretFactory((SecretFactory)room);
			} else {
				toAdd = new SecretRoom((SecretRoom)room);
			}
			
			this.secrets.add(toAdd);
		}
	}
	
	public SecretFactory(JsonArray data){
		secrets = new ArrayList<ISecretRoom>();
		for(JsonElement e : data){
			JsonObject room = e.getAsJsonObject();
			String type = room.get("type").getAsString();
			int num = room.get("num").getAsInt();
			this.count += num;
			secrets.add(new SecretRoom(DungeonRoom.valueOf(type), num));
		}
	}
	
	public void addRoom(DungeonRoom type){
		addRoom(type, 1);
	}
	
	public void addRoom(DungeonRoom type, int count){
		secrets.add(new SecretRoom(type, count));
		this.count += count;
	}
	
	public void addRoom(List<DungeonRoom> rooms, int count){
		
		SecretFactory toAdd = new SecretFactory();
		
		for(DungeonRoom type : rooms){
			toAdd.addRoom(type);
		}
		
		this.secrets.add(toAdd);
		this.count += count;
	}
	
	public boolean genRoom(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord pos){
		if(count <= 0) return false;
		
		Collections.shuffle(this.secrets, rand);
		
		for(ISecretRoom room : secrets){
			if(room.genRoom(world, rand, settings, dir, pos)){
				this.count--;
				return true;
			}
		}
		
		return false;
	}
}