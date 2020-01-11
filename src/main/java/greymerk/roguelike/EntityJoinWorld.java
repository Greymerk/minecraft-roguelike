package greymerk.roguelike;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.MetaEntity;
import greymerk.roguelike.monster.MonsterProfile;

public class EntityJoinWorld {

  @SubscribeEvent
  public void OnEntityJoinWorld(EntityJoinWorldEvent event) {

    World world = event.getWorld();
    if (world.isRemote) {
      return;
    }

    Entity entity = event.getEntity();

    // slimes do not extend EntityMob for some reason
    if (!(entity instanceof EntityMob || entity instanceof EntitySlime)) {
      return;
    }

    EntityLiving mob = (EntityLiving) entity;

    Collection<?> effects = mob.getActivePotionEffects();
    for (Object buff : effects) {
      if (Potion.getIdFromPotion(((PotionEffect) buff).getPotion()) == 4) {
        int level = ((PotionEffect) buff).getAmplifier();

        IEntity metaEntity = new MetaEntity(mob);
        MonsterProfile.equip(world, world.rand, level, metaEntity);
        if (entity.isDead) {
          event.setCanceled(true);
        }
        return;
      }
    }
  }
}
