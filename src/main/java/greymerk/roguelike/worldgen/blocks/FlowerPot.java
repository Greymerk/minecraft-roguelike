package greymerk.roguelike.worldgen.blocks;

import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;

public enum FlowerPot {

	DANDELION, POPPY, ORCHID, ALLIUM, BLUET, REDTULIP, ORANGETULIP, WHITETULIP, PINKTULIP, DAISY,
	REDMUSHROOM, BROWNMUSHROOM, CACTUS, OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK,
	SHRUB, FERN;

	public static void generate(WorldEditor editor, Coord pos, FlowerPot type){
		MetaBlock pot = new MetaBlock(Blocks.flower_pot);
		if(!pot.setBlock(editor, pos)) return;
		
		TileEntity potEntity = editor.getTileEntity(pos);
		
		if(potEntity == null) return;
		if(!(potEntity instanceof TileEntityFlowerPot)) return;
		
		TileEntityFlowerPot flower = (TileEntityFlowerPot)potEntity;
		
		setData(flower, type);
	}
	
	public static void generate(WorldEditor editor, Random rand, Coord pos){
		FlowerPot choice = FlowerPot.values()[rand.nextInt(FlowerPot.values().length)];
		generate(editor, pos, choice);
	}
	
	public static void setData(TileEntityFlowerPot pot, FlowerPot type){
		switch(type){
		case DANDELION: pot.func_145964_a(Item.getItemFromBlock(Blocks.yellow_flower), 0); return;
		case POPPY: pot.func_145964_a(Item.getItemFromBlock(Blocks.red_flower), 0); return;
		case ORCHID: pot.func_145964_a(Item.getItemFromBlock(Blocks.red_flower), 1); return;
		case ALLIUM: pot.func_145964_a(Item.getItemFromBlock(Blocks.red_flower), 2); return;
		case BLUET: pot.func_145964_a(Item.getItemFromBlock(Blocks.red_flower), 3); return;
		case REDTULIP: pot.func_145964_a(Item.getItemFromBlock(Blocks.red_flower), 4); return;
		case ORANGETULIP: pot.func_145964_a(Item.getItemFromBlock(Blocks.red_flower), 5); return;
		case WHITETULIP: pot.func_145964_a(Item.getItemFromBlock(Blocks.red_flower), 6); return;
		case PINKTULIP: pot.func_145964_a(Item.getItemFromBlock(Blocks.red_flower), 7); return;
		case DAISY: pot.func_145964_a(Item.getItemFromBlock(Blocks.red_flower), 8); return;
		case REDMUSHROOM: pot.func_145964_a(Item.getItemFromBlock(Blocks.red_mushroom), 0); return;
		case BROWNMUSHROOM: pot.func_145964_a(Item.getItemFromBlock(Blocks.brown_mushroom), 0); return;
		case CACTUS: pot.func_145964_a(Item.getItemFromBlock(Blocks.cactus), 0); return;
		case OAK: pot.func_145964_a(Item.getItemFromBlock(Blocks.sapling), 0); return;
		case SPRUCE: pot.func_145964_a(Item.getItemFromBlock(Blocks.sapling), 1); return;
		case BIRCH: pot.func_145964_a(Item.getItemFromBlock(Blocks.sapling), 2); return;
		case JUNGLE: pot.func_145964_a(Item.getItemFromBlock(Blocks.sapling), 3); return;
		case ACACIA: pot.func_145964_a(Item.getItemFromBlock(Blocks.sapling), 4); return;
		case DARKOAK: pot.func_145964_a(Item.getItemFromBlock(Blocks.sapling), 5); return;
		case SHRUB: pot.func_145964_a(Item.getItemFromBlock(Blocks.tallgrass), 0); return;
		case FERN: pot.func_145964_a(Item.getItemFromBlock(Blocks.tallgrass), 2); return;
		default: pot.func_145964_a(Item.getItemFromBlock(Blocks.yellow_flower), 0); return;
		}
	}
	
	public static ItemStack getFlowerItem(FlowerPot type){
		switch(type){
		case DANDELION: return new ItemStack(Blocks.yellow_flower);
		case POPPY: return new ItemStack(Blocks.red_flower, 0);
		case ORCHID: return new ItemStack(Blocks.red_flower, 1);
		case ALLIUM: return new ItemStack(Blocks.red_flower, 2);
		case BLUET: return new ItemStack(Blocks.red_flower, 3);
		case REDTULIP: return new ItemStack(Blocks.red_flower, 4);
		case ORANGETULIP: return new ItemStack(Blocks.red_flower, 5);
		case WHITETULIP: return new ItemStack(Blocks.red_flower, 6);
		case PINKTULIP: return new ItemStack(Blocks.red_flower, 7);
		case DAISY: return new ItemStack(Blocks.red_flower, 8);
		case REDMUSHROOM: return new ItemStack(Blocks.red_mushroom);
		case BROWNMUSHROOM: return new ItemStack(Blocks.brown_mushroom);
		case CACTUS: return new ItemStack(Blocks.cactus);
		case OAK: return new ItemStack(Blocks.sapling, 0);
		case SPRUCE: return new ItemStack(Blocks.sapling, 1);
		case BIRCH: return new ItemStack(Blocks.sapling, 2);
		case JUNGLE: return new ItemStack(Blocks.sapling, 3);
		case ACACIA: return new ItemStack(Blocks.sapling, 4);
		case DARKOAK: return new ItemStack(Blocks.sapling, 5);
		case SHRUB: return new ItemStack(Blocks.tallgrass, 0);
		case FERN: return new ItemStack(Blocks.tallgrass, 2);
		default: return new ItemStack(Blocks.yellow_flower);
		}
	}
	
	public static MetaBlock getFlower(FlowerPot type){
		MetaBlock flower;
		switch(type){
		case DANDELION: flower = new MetaBlock(Blocks.yellow_flower);
		case POPPY: flower = new MetaBlock(Blocks.red_flower);
		case ORCHID: flower = new MetaBlock(Blocks.red_flower);
		flower.withProperty(Blocks.red_flower.func_176494_l(), BlockFlower.EnumFlowerType.BLUE_ORCHID);
		case ALLIUM: flower = new MetaBlock(Blocks.red_flower);
		flower.withProperty(Blocks.red_flower.func_176494_l(), BlockFlower.EnumFlowerType.ALLIUM);
		case BLUET: flower = new MetaBlock(Blocks.red_flower);
		flower.withProperty(Blocks.red_flower.func_176494_l(), BlockFlower.EnumFlowerType.HOUSTONIA);
		case REDTULIP: flower = new MetaBlock(Blocks.red_flower);
		flower.withProperty(Blocks.red_flower.func_176494_l(), BlockFlower.EnumFlowerType.RED_TULIP);
		case ORANGETULIP: flower = new MetaBlock(Blocks.red_flower);
		flower.withProperty(Blocks.red_flower.func_176494_l(), BlockFlower.EnumFlowerType.ORANGE_TULIP);
		case WHITETULIP: flower = new MetaBlock(Blocks.red_flower);
		flower.withProperty(Blocks.red_flower.func_176494_l(), BlockFlower.EnumFlowerType.WHITE_TULIP);
		case PINKTULIP: flower = new MetaBlock(Blocks.red_flower);
		flower.withProperty(Blocks.red_flower.func_176494_l(), BlockFlower.EnumFlowerType.PINK_TULIP);
		case DAISY: flower = new MetaBlock(Blocks.red_flower);
		flower.withProperty(Blocks.red_flower.func_176494_l(), BlockFlower.EnumFlowerType.OXEYE_DAISY);
		case REDMUSHROOM: flower = new MetaBlock(Blocks.red_mushroom);
		case BROWNMUSHROOM: flower = new MetaBlock(Blocks.brown_mushroom);
		case CACTUS: flower = new MetaBlock(Blocks.cactus);
		case OAK: flower = new MetaBlock(Blocks.sapling);
		flower.withProperty(BlockSapling.TYPE_PROP, BlockPlanks.EnumType.OAK);
		case SPRUCE: flower = new MetaBlock(Blocks.sapling);
		flower.withProperty(BlockSapling.TYPE_PROP, BlockPlanks.EnumType.SPRUCE);
		case BIRCH: flower = new MetaBlock(Blocks.sapling);
		flower.withProperty(BlockSapling.TYPE_PROP, BlockPlanks.EnumType.BIRCH);
		case JUNGLE: flower = new MetaBlock(Blocks.sapling);
		flower.withProperty(BlockSapling.TYPE_PROP, BlockPlanks.EnumType.JUNGLE);
		case ACACIA: flower = new MetaBlock(Blocks.sapling);
		flower.withProperty(BlockSapling.TYPE_PROP, BlockPlanks.EnumType.ACACIA);
		case DARKOAK: flower = new MetaBlock(Blocks.sapling);
		flower.withProperty(BlockSapling.TYPE_PROP, BlockPlanks.EnumType.DARK_OAK);
		case SHRUB: flower = new MetaBlock(Blocks.tallgrass);
		flower.withProperty(BlockTallGrass.TYPE_PROP, BlockTallGrass.EnumType.DEAD_BUSH);
		case FERN: flower = new MetaBlock(Blocks.tallgrass);
		flower.withProperty(BlockTallGrass.TYPE_PROP, BlockTallGrass.EnumType.FERN);

		default: flower = new MetaBlock(Blocks.yellow_flower);
		}
		
		return flower;
	}
}
