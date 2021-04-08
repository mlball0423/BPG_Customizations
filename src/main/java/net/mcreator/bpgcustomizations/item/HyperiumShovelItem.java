
package net.mcreator.bpgcustomizations.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.bpgcustomizations.itemgroup.BPGCustomizationsTabItemGroup;
import net.mcreator.bpgcustomizations.BpgCustomizationsModElements;

@BpgCustomizationsModElements.ModElement.Tag
public class HyperiumShovelItem extends BpgCustomizationsModElements.ModElement {
	@ObjectHolder("bpg_customizations:hyperium_shovel")
	public static final Item block = null;
	public HyperiumShovelItem(BpgCustomizationsModElements instance) {
		super(instance, 36);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ShovelItem(new IItemTier() {
			public int getMaxUses() {
				return 0;
			}

			public float getEfficiency() {
				return 12f;
			}

			public float getAttackDamage() {
				return 4f;
			}

			public int getHarvestLevel() {
				return 6;
			}

			public int getEnchantability() {
				return 42;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(HyperiumIngotItem.block, (int) (1)));
			}
		}, 1, -3f, new Item.Properties().group(BPGCustomizationsTabItemGroup.tab).isImmuneToFire()) {
		}.setRegistryName("hyperium_shovel"));
	}
}
