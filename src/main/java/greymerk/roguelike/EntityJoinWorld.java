package greymerk.roguelike;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.treasure.loot.Loot;

import java.util.Collection;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityJoinWorld {
	
	@SubscribeEvent
	public void OnEntityJoinWorld(EntityJoinWorldEvent event) {
		
		// slimes do not extend EntityMob for some reason
		if(!(event.entity instanceof EntityMob || event.entity instanceof EntitySlime)){
			return;
		}
		
		EntityLiving mob = (EntityLiving) event.entity;
		
		String name = mob.getCustomNameTag();
		
		if(name.equals("roguelike")){
			Loot.addEquipment(event.world, Catacomb.getLevel((int)event.entity.posY), event.entity);
			mob.setCustomNameTag("");
			return;
		}
		
		Collection<?> effects = mob.getActivePotionEffects();
		for(Object buff : effects){
			if(((PotionEffect) buff).getPotionID() == 4){
				Loot.addEquipment(event.world, Catacomb.getLevel((int)event.entity.posY), event.entity);
				return;
			}
		}
	}
}
