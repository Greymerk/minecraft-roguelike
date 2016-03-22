package greymerk.roguelike;

import greymerk.roguelike.treasure.loot.Loot;

import java.util.Collection;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityJoinWorld {
	
	@SubscribeEvent
	public void OnEntityJoinWorld(EntityJoinWorldEvent event) {
		
		// slimes do not extend EntityMob for some reason
		if(!(event.entity instanceof EntityMob || event.entity instanceof EntitySlime)){
			return;
		}
		
		EntityLiving mob = (EntityLiving) event.entity;
		
		Collection<?> effects = mob.getActivePotionEffects();
		for(Object buff : effects){
			if(Potion.getIdFromPotion(((PotionEffect) buff).getPotion()) == 4){
				int level = ((PotionEffect) buff).getAmplifier();
				Loot.addEquipment(event.world, level, event.entity);
				return;
			}
		}
	}
}
