package greymerk.roguelike.treasure.loot;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public enum PotionEffect {
	
	SPEED, SLOWNESS, HASTE, FATIGUE, STRENGTH, HEALTH, DAMAGE, JUMP, 
	NAUSIA, REGEN, RESISTANCE, FIRERESIST, WATERBREATH, INVISIBILITY,
	BLINDNESS, NIGHTVISION, HUNGER, WEAKNESS, POISON, WITHER, HEALTHBOOST,
	ABSORPTION, SATURATION;
	
	public static int getEffectID(PotionEffect type){
		
		switch(type){
		case SPEED: return 1;
		case SLOWNESS: return 2;
		case HASTE: return 3;
		case FATIGUE: return 4;
		case STRENGTH: return 5;
		case HEALTH: return 6;
		case DAMAGE: return 7;
		case JUMP: return 8;
		case NAUSIA: return 9;
		case REGEN: return 10;
		case RESISTANCE: return 11; 
		case FIRERESIST: return 12;
		case WATERBREATH: return 13;
		case INVISIBILITY: return 14;
		case BLINDNESS: return 15;
		case NIGHTVISION: return 16;
		case HUNGER: return 17;
		case WEAKNESS: return 18;
		case POISON: return 19;
		case WITHER: return 20;
		case HEALTHBOOST: return 21;
		case ABSORPTION: return 22;
		case SATURATION: return 23;
		default: return 0;
		}		
	}
	
	public static void addCustomEffect(ItemStack potion, PotionEffect type, int amplifier, int duration){
		
		NBTTagCompound tag = potion.getTagCompound();
		if(tag == null){
			tag = new NBTTagCompound();
			potion.setTagCompound(tag);
		}
		
		final String CUSTOM = "CustomPotionEffects";
		NBTTagList effects;
		
		effects = tag.getTagList(CUSTOM, 0);
		if (effects == null){
			effects = new NBTTagList();
			tag.setTag(CUSTOM, effects);
		}
		
		effects = tag.getTagList(CUSTOM, 0);
		
		int effectID = PotionEffect.getEffectID(type);
		NBTTagCompound toAdd = new NBTTagCompound();
		
		toAdd.setByte("Id", (byte)effectID);
		toAdd.setByte("Amplifier", (byte)amplifier);
		toAdd.setInteger("Duration", duration);
		toAdd.setByte("Ambient", (byte)1);
		toAdd.setByte("ShowParticles", (byte)1);
		
		effects.appendTag(toAdd);
		tag.setTag(CUSTOM, effects);
		potion.setTagCompound(tag);

	}
	
}
