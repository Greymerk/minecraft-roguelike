package greymerk.roguelike.monster.profiles;

import java.util.Random;

import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.treasure.loot.Slot;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ProfileRleahy implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, Entity mob) {
		ItemStack weapon = ItemNovelty.getItem(ItemNovelty.RLEAHY);
		mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, weapon);
		mob.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, Shield.get(rand));
		
		ItemStack item = ItemArmour.get(rand, Slot.FEET, Quality.WOOD);
		ItemArmour.dyeArmor(item, 32, 32, 32);
		mob.setItemStackToSlot(EntityEquipmentSlot.FEET, item);

		item = ItemArmour.get(rand, Slot.LEGS, Quality.WOOD);
		ItemArmour.dyeArmor(item, 0, 51, 102);
		mob.setItemStackToSlot(EntityEquipmentSlot.LEGS, item);
		
		item = ItemArmour.get(rand, Slot.CHEST, Quality.WOOD);
		ItemArmour.dyeArmor(item, 255, 204, 229);
		mob.setItemStackToSlot(EntityEquipmentSlot.CHEST, item);
	}
}
