
package net.mcreator.bpgcustomizations.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.HoeItem;

import net.mcreator.bpgcustomizations.itemgroup.BPGCustomizationsTabItemGroup;
import net.mcreator.bpgcustomizations.BpgCustomizationsModElements;

@BpgCustomizationsModElements.ModElement.Tag
public class HyperiumHoeItem extends BpgCustomizationsModElements.ModElement {
	@ObjectHolder("bpg_customizations:hyperium_hoe")
	public static final Item block = null;
	public HyperiumHoeItem(BpgCustomizationsModElements instance) {
		super(instance, 37);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new HoeItem(new IItemTier() {
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
		}, 0, -3f, new Item.Properties().group(BPGCustomizationsTabItemGroup.tab).isImmuneToFire()) {
		}.setRegistryName("hyperium_hoe"));
	}
}
