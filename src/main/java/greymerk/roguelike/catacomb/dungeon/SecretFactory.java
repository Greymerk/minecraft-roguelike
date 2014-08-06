package greymerk.roguelike.catacomb.dungeon;

import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

import java.util.ArrayList;
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
		secrets = new ArrayList<ISecretRoom>();
		count = 0;
	}
	
	public SecretFactory(int count){
		secrets = new ArrayList<ISecretRoom>();
		this.count = count;
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
		for(JsonElement e : data){
			JsonObject room = e.getAsJsonObject();
			String type = room.get("type").getAsString();
			int num = room.get("num").getAsInt();
			secrets.add(new SecretRoom(Dungeon.valueOf(type), num));
		}
	}
	
	public void addRoom(Dungeon type){
		addRoom(type, 1);
	}
	
	public void addRoom(Dungeon type, int count){
		secrets.add(new SecretRoom(type, count));
	}
	
	public void addRoom(ISecretRoom toAdd, int count){
		this.secrets.add(toAdd);
	}
	
	public void addRoom(List<Dungeon> rooms, int count){
		
		SecretFactory toAdd = new SecretFactory(1);
		
		for(Dungeon type : rooms){
			toAdd.addRoom(type);
		}
		
		this.addRoom(toAdd, count);
	}
	
	public boolean genRoom(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord pos){
		for(ISecretRoom room : secrets){
			if(room.isValid(world, rand, dir, pos)){
				room.genRoom(world, rand, settings, dir, pos);
				if(this.count > 0) this.count--;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isValid(World world, Random rand, Cardinal dir, Coord pos) {
		if(count <= 0) return false;
		return true;
	}
}