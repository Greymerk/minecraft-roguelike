package greymerk.roguelike;

import greymerk.roguelike.treasure.loot.Loot;

import java.util.Collection;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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
			if(((PotionEffect) buff).getPotionID() == 4){
				int level = ((PotionEffect) buff).getAmplifier();
				if(level<=0)return;
				Loot.addEquipment(event.world, level, event.entity);
				return;
			}
		}
	}
}
