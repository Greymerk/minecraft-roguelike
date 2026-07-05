package com.greymerk.roguelike.treasure.loot.items;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatternLayers.Layer;
import net.minecraft.world.level.block.entity.BannerPatterns;
import com.greymerk.roguelike.util.Color;

public class Banner {

	public static ResourceKey<BannerPattern> getRandomPattern(RandomSource rand) {
		final List<ResourceKey<BannerPattern>> PATTERNS = List.of(
				BannerPatterns.BORDER,
				BannerPatterns.BRICKS,
				BannerPatterns.CIRCLE_MIDDLE,
				BannerPatterns.CREEPER,
				BannerPatterns.CROSS,
				BannerPatterns.CURLY_BORDER,
				BannerPatterns.DIAGONAL_LEFT,
				BannerPatterns.DIAGONAL_RIGHT_MIRROR,
				BannerPatterns.DIAGONAL_LEFT_MIRROR,
				BannerPatterns.DIAGONAL_RIGHT,
				BannerPatterns.FLOWER,
				BannerPatterns.GRADIENT,
				BannerPatterns.GRADIENT_UP,
				BannerPatterns.HALF_HORIZONTAL,
				BannerPatterns.HALF_HORIZONTAL_MIRROR,
				BannerPatterns.HALF_VERTICAL,
				BannerPatterns.HALF_VERTICAL_MIRROR,
				BannerPatterns.MOJANG,
				BannerPatterns.PIGLIN,
				BannerPatterns.RHOMBUS_MIDDLE,
				BannerPatterns.SKULL,
				BannerPatterns.STRIPE_SMALL,
				BannerPatterns.SQUARE_BOTTOM_LEFT,
				BannerPatterns.SQUARE_BOTTOM_RIGHT,
				BannerPatterns.SQUARE_TOP_LEFT,
				BannerPatterns.SQUARE_TOP_RIGHT,
				BannerPatterns.STRAIGHT_CROSS,
				BannerPatterns.STRIPE_BOTTOM,
				BannerPatterns.STRIPE_CENTER,
				BannerPatterns.STRIPE_DOWNLEFT,
				BannerPatterns.STRIPE_DOWNRIGHT,
				BannerPatterns.STRIPE_LEFT,
				BannerPatterns.STRIPE_MIDDLE,
				BannerPatterns.STRIPE_RIGHT,
				BannerPatterns.STRIPE_TOP,
				BannerPatterns.TRIANGLE_BOTTOM,
				BannerPatterns.TRIANGLE_TOP,
				BannerPatterns.TRIANGLES_BOTTOM,
				BannerPatterns.TRIANGLES_TOP
		);
		
		return PATTERNS.get(rand.nextInt(PATTERNS.size()));
	}
	
	public static ItemStack get(RegistryAccess reg, RandomSource rand){
		Registry<BannerPattern> patterns = reg.lookupOrThrow(Registries.BANNER_PATTERN);
		
		ItemStack banner = new ItemStack(Items.BANNER.black());
		banner.set(DataComponents.BANNER_PATTERNS, createLayersComponent(patterns, rand, rand.nextInt(3) + 3));
		return banner;
	}
	
	public static BannerPatternLayers createLayersComponent(Registry<BannerPattern> patterns, RandomSource rand, int count) {
		List<Layer> layers = new ArrayList<Layer>();
		BannerPatternLayers component = new BannerPatternLayers(layers);
		layers.add(createLayer(patterns, BannerPatterns.BASE, Color.get(rand)));
		
		for(int i = 0; i < count; ++i) {
			ResourceKey<BannerPattern> key = Banner.getRandomPattern(rand);
			Layer toAdd = createLayer(patterns, key, Color.get(rand));
			layers.add(toAdd);	
		}
		
		return component;
	}
	
	public static Layer createLayer(Registry<BannerPattern> reg, ResourceKey<BannerPattern> key, Color c) {
		Holder<BannerPattern> entry = reg.getOrThrow(key);
		return new Layer(entry, Color.get(c));
	}
		
}